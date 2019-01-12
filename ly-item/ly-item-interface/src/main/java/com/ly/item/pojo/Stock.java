package com.ly.item.pojo;

import lombok.Data;

import javax.persistence.Id;
import javax.persistence.Table;

/**
 * @program: ly
 * @description: Lyshop
 * @author: Lee
 * @create: 2018-12-25 13:18
 **/
@Table(name = "tb_stock")
@Data
public class Stock {

    @Id
    private Long skuId;

    private Integer seckillStock;// 秒杀可用库存

    private Integer seckillTotal;// 已秒杀数量

    private Integer stock;// 正常库存
}