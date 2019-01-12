package com.ly.item.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author Lee
 * @date 2018/12/29
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CartDto {

    private Long skuId;  //商品skuId

    private Integer num;  //购买数量

    private String image;
    private String ownSpec;
    private Long price;
    private String title;

}
