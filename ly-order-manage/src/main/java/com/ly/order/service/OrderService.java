package com.ly.order.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.ly.auth.entity.UserInfo;
import com.ly.common.enums.ExceptionEnum;
import com.ly.common.exception.LyException;
import com.ly.common.utils.IdWorker;
import com.ly.common.utils.JsonUtils;
import com.ly.common.vo.PageResult;
import com.ly.item.dto.CartDto;
import com.ly.item.pojo.Sku;
import com.ly.order.client.GoodsClient;
import com.ly.order.dto.OrderDto;
import com.ly.order.dto.OrderStatusEnum;
import com.ly.order.filter.LoginInterceptor;
import com.ly.order.mapper.AddressMapper;
import com.ly.order.mapper.OrderDetailMapper;
import com.ly.order.mapper.OrderMapper;
import com.ly.order.mapper.OrderStatusMapper;
import com.ly.order.pojo.Address;
import com.ly.order.pojo.Order;
import com.ly.order.pojo.OrderDetail;
import com.ly.order.pojo.OrderStatus;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import tk.mybatis.mapper.entity.Example;

import java.util.*;
import java.util.stream.Collectors;

@Service
@Slf4j
public class OrderService {
    @Autowired
    private OrderMapper orderMapper;

    @Autowired
    private AddressMapper addressMapper;

    @Autowired
    private OrderDetailMapper orderDetailMapper;

    @Autowired
    private OrderStatusMapper orderStatusMapper;

    @Autowired
    private GoodsClient goodsClient;

    @Autowired
    private IdWorker idWorker;

    @Autowired
    private AmqpTemplate amqpTemplate;



    @Transactional
    public Long createOrder(OrderDto orderDto) {
        //生成订单ID，采用自己的算法生成订单ID
        long orderId = idWorker.nextId();

        //填充order，订单中的用户信息数据从Token中获取，填充到order中
        Order order = new Order();
        order.setCreateTime(new Date());
        order.setOrderId(orderId);
        order.setPaymentType(orderDto.getPaymentType());
        order.setPostFee(0L);  //// TODO 调用物流信息，根据地址计算邮费

        //获取用户信息
        UserInfo user = LoginInterceptor.getLoginUser();
        order.setUserId(user.getId());
        order.setBuyerNick(user.getName());
        order.setBuyerRate(false);  //卖家为留言

        //收货人地址信息，应该从数据库中物流信息中获取，这里使用的是假的数据
        Address address = addressMapper.selectByPrimaryKey(orderDto.getAddressId());
        if (address == null) {
            // 商品不存在，抛出异常
            throw new LyException(ExceptionEnum.RECEIVER_ADDRESS_NOT_FOUND);
        }
        order.setReceiver(address.getName());
        order.setReceiverAddress(address.getAddress());
        order.setReceiverCity(address.getCity());
        order.setReceiverDistrict(address.getDistrict());
        order.setReceiverMobile(address.getPhone());
        order.setReceiverZip(address.getZipCode());
        order.setReceiverState(address.getState());


        //付款金额相关，首先把orderDto转化成map，其中key为skuId,值为购物车中该sku的购买数量
        Map<Long, Integer> skuNumMap = orderDto.getCarts().stream().collect(Collectors.toMap(c -> c.getSkuId(), c -> c.getNum()));

//        //查询商品信息，根据skuIds批量查询sku详情
//        List<Sku> skus = goodsClient.querySkusByIds(new ArrayList<>(skuNumMap.keySet()));
//
//        if (CollectionUtils.isEmpty(skus)) {
//            throw new LyException(ExceptionEnum.GOODS_NOT_FOUND);
//        }

        //填充orderDetail
        ArrayList<OrderDetail> orderDetails = new ArrayList<>();

        //遍历skus，填充orderDetail
        for (CartDto cartDto : orderDto.getCarts()) {


            OrderDetail orderDetail = new OrderDetail();
            orderDetail.setOrderId(orderId);
            orderDetail.setOwnSpec(cartDto.getOwnSpec());
            orderDetail.setSkuId(cartDto.getSkuId());
            orderDetail.setTitle(cartDto.getTitle());
            orderDetail.setNum(cartDto.getNum());
            orderDetail.setPrice(cartDto.getPrice().longValue());
            orderDetail.setImage(cartDto.getImage());
            orderDetails.add(orderDetail);
        }

        order.setActualPay(orderDto.getActualPay());  //todo 还要减去优惠金额
        order.setTotalPay(orderDto.getTotalPay());

        //保存order
        orderMapper.insertSelective(order);

        //保存detail
        orderDetailMapper.insertList(orderDetails);


        //填充orderStatus
        OrderStatus orderStatus = new OrderStatus();
        orderStatus.setOrderId(orderId);
        orderStatus.setStatus(OrderStatusEnum.INIT.value());
        orderStatus.setCreateTime(new Date());

        //保存orderStatus
        orderStatusMapper.insertSelective(orderStatus);

        //减库存
        goodsClient.decreaseStock(orderDto.getCarts());


        //todo 删除购物车中已经下单的商品数据, 采用异步mq的方式通知购物车系统删除已购买的商品，传送商品ID和用户ID
        HashMap<String, Object> map = new HashMap<>();
        try {
            map.put("skuIds", skuNumMap.keySet());
            map.put("userId", user.getId());
            amqpTemplate.convertAndSend("ly.cart.exchange", "cart.delete", JsonUtils.toString(map));
        } catch (Exception e) {
            log.error("删除购物车消息发送异常，商品ID：{}", skuNumMap.keySet(), e);
        }

        log.info("生成订单，订单编号：{}，用户id：{}", orderId, user.getId());
        return orderId;

    }



    public Order queryById(String orderIdString) {
        Long orderId=Long.valueOf(orderIdString).longValue();
        Order order = orderMapper.selectByPrimaryKey(orderId);
        order.setOrderIdString(orderIdString);
        if (orderId == null) {
            throw new LyException(ExceptionEnum.ORDER_NOT_FOUND);
        }
        OrderDetail orderDetail = new OrderDetail();
        orderDetail.setOrderId(orderId);
        List<OrderDetail> orderDetails = orderDetailMapper.select(orderDetail);
        order.setOrderDetails(orderDetails);
        OrderStatus orderStatus = orderStatusMapper.selectByPrimaryKey(orderId);
        order.setOrderStatus(orderStatus);
        return order;
    }



    public PageResult<Order> queryOrderByPage(Integer page, Integer rows,Integer status) {
        Order order1 = new Order();
        //开启分页
        PageHelper.startPage(page, rows);


            //获取用户信息
            UserInfo user = LoginInterceptor.getLoginUser();
            order1.setUserId(user.getId());

            //查询订单
            List<Order> orders = orderMapper.select(order1);



            //查询订单详情
            for (Order order : orders) {
                order.setOrderIdString(String.valueOf(order.getOrderId()));
                OrderDetail orderDetail = new OrderDetail();
                orderDetail.setOrderId(order.getOrderId());
                List<OrderDetail> orderDetailList = orderDetailMapper.select(orderDetail);

                order.setOrderDetails(orderDetailList);
                //查询订单状态
                OrderStatus orderStatus = orderStatusMapper.selectByPrimaryKey(order.getOrderId());
                order.setOrderStatus(orderStatus);
            }



        //如果有状态
       if (status!= 0){
           List<Order> neworderlist=new ArrayList<>();
           for (Order order : orders){
                if (order.getOrderStatus().getStatus()==status){
                    neworderlist.add(order);
                }
           }
           PageInfo<Order> pageInfo = new PageInfo<>(neworderlist);
           return new PageResult<>(pageInfo.getTotal(), pageInfo.getPages(), pageInfo.getList());
       }

        PageInfo<Order> pageInfo = new PageInfo<>(orders);

        return new PageResult<>(pageInfo.getTotal(), pageInfo.getPages(), pageInfo.getList());
    }

}
