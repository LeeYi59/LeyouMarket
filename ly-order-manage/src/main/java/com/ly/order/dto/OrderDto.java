package com.ly.order.dto;

import com.ly.item.dto.CartDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderDto {
    @NotNull
    private Long addressId; // 收获人地址id
    @NotNull
    private Integer paymentType;// 付款类型
    @NotNull
    private List<CartDto> carts;// 订单详情
    @NotNull
    private Long totalPay;//总价
    @NotNull
    private Long actualPay;//应付

//    private Long postFee;
//    private String receiver;
//    private String receiverAddress;
//    private String receiverCity;
//    private String receiverDistrict;
//    private String receiverMobile;
//    private String receiverState;
//    private String receiverZip;
//
//    private String totalPay;


}