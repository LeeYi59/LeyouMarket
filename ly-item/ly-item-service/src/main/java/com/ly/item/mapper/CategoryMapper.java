package com.ly.item.mapper;/**
 * Create By IvanLee on 2018/12/16
 */

import com.ly.item.pojo.Category;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import tk.mybatis.mapper.additional.idlist.SelectByIdListMapper;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

/**
 *@ClassName CategoryMapper
 *@Description Todo
 *@Author Lee
 *@Date 2018/12/16 19:10
 *@Version 1.0
 **/
public interface CategoryMapper extends Mapper<Category>,SelectByIdListMapper<Category,Long> {
    @Select("SELECT * from tb_category WHERE id IN (select category_id FROM tb_category_brand where brand_id = #{bid})")
    List<Category> queryCategoryByBid(@Param("bid") Long bid);
}
