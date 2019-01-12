package com.ly.item.mapper;/**
 * Create By IvanLee on 2018/12/17
 */

import com.ly.item.pojo.Brand;
import org.apache.ibatis.annotations.*;
import tk.mybatis.mapper.additional.idlist.SelectByIdListMapper;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

/**
 *@ClassName BrandMapper
 *@Description Todo
 *@Author IvanLee
 *@Date 2018/12/17 22:27
 *@Version 1.0
 **/
public interface BrandMapper extends Mapper<Brand> , SelectByIdListMapper<Brand,Long> {
    /**
     * 新增商品分类和品牌中间表数据
     * @param cid 商品分类id
     * @param bid 品牌id
     * @return
     */
    @Insert("INSERT INTO tb_category_brand (category_id, brand_id) VALUES (#{cid},#{bid})")
    int insertCategoryBrand(@Param("cid") Long cid, @Param("bid") Long bid);


    @Update("UPDATE tb_category_brand SET category_id = #{cid} where brand_id = #{bid}" )
    void updateCategoryBrand(@Param("cid") Long categoryId, @Param("bid") Long id);

    @Delete("DELETE from tb_category_brand where brand_id = #{bid}")
    void deleteCategoryBrandByBid(@Param("bid") Long bid);

    @Select("SELECT brand_id from tb_category_brand where category_id = #{cid}")
    List<Long> selectBrandIdsByCategoryId(@Param("cid")Long cid);
}
