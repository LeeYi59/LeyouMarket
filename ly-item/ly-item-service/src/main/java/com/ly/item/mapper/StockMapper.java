package com.ly.item.mapper;

import com.ly.common.mapper.BaseMapper;
import com.ly.item.pojo.Stock;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Update;

/**
 * @program: ly
 * @description: Lyshop
 * @author: Lee
 * @create: 2018-12-25 13:24
 **/
public interface StockMapper extends BaseMapper<Stock,Long> {
    @Update("update tb_stock set stock = stock - #{num} where sku_id = #{skuId} and stock >= #{num}")
    int decreaseStock(@Param("skuId") Long skuId, @Param("num") Integer num);
}
