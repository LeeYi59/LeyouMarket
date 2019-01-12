package com.ly.order.web;

import com.ly.common.vo.PageResult;
import com.ly.order.dto.OrderDto;
import com.ly.order.pojo.Address;
import com.ly.order.pojo.Order;
import com.ly.order.service.AddressService;
import com.ly.order.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
public class OrderController {

    @Autowired
    private OrderService orderService;


    /**
     * 创建订单
     *
     * @param orderDto
     * @return
     */
    @PostMapping
    public ResponseEntity<Long> createOrder(@RequestBody OrderDto orderDto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(orderService.createOrder(orderDto));
    }


    /**
     * 根据订单ID查询订单详情
     *
     * @param orderId
     * @return
     */
    @GetMapping("{id}")
    public ResponseEntity<Order> queryOrderById(@PathVariable("id") String orderId) {
        return ResponseEntity.ok(orderService.queryById(orderId));
    }


    /**
     * 分页查询所有订单
     *
     * @param page
     * @param rows
     * @return
     */
    @GetMapping("list")
    public ResponseEntity<PageResult<Order>> queryOrderByPage(@RequestParam(value = "page",defaultValue = "1") Integer page,
                                                              @RequestParam(value = "rows",defaultValue = "3") Integer rows,
                                                              @RequestParam(value="status",defaultValue = "0")Integer status) {

        return ResponseEntity.ok(orderService.queryOrderByPage(page, rows,status));
    }
}
