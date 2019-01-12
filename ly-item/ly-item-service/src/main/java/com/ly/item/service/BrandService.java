package com.ly.item.service;/**
 * Create By IvanLee on 2018/12/17
 */

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.ly.common.enums.ExceptionEnum;
import com.ly.common.exception.LyException;
import com.ly.common.vo.PageResult;
import com.ly.item.mapper.BrandMapper;
import com.ly.item.pojo.Brand;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import tk.mybatis.mapper.entity.Example;

import java.util.List;

/**
 *@ClassName BrandService
 *@Description Todo
 *@Author Lee
 *@Date 2018/12/17 22:27
 *@Version 1.0
 **/
@Service
public class BrandService {
    @Autowired
    private BrandMapper brandMapper;

    /**
     * 根据多个条件查询品牌信息
     * @param page
     * @param rows
     * @param sortBy
     * @param desc
     * @param key
     * @return
     */
    public PageResult<Brand> queryBrandByPageAndSort(Integer page, Integer rows, String sortBy, Boolean desc, String key) {
        //分页
        PageHelper.startPage(page,rows);

        /*
        where 'name' like "%x%" or letter == 'x'
        order by id desc
         */

        //过滤
        Example example = new Example(Brand.class);
        if (StringUtils.isNotBlank(key)){
            //过滤条件
            example.createCriteria().orLike("name","%"+key+"%").orEqualTo("letter",key.toUpperCase());
        }
        //排序
        if (StringUtils.isNotBlank(sortBy)){
            String orderByClause=sortBy+(desc ? " DESC" : " ASC");
            example.setOrderByClause(orderByClause);
        }

        //查询
        List<Brand> list =brandMapper.selectByExample(example);
        if (CollectionUtils.isEmpty(list)){
            throw new LyException(ExceptionEnum.BRAND_NOT_FOUND);
        }
        //解析分页结果
        PageInfo<Brand> info = new PageInfo<>(list);
        return new PageResult<>(info.getTotal(),list);
    }
    /**
     * 品牌的新增
     *
     * @param cids
     * @param brand
     */
    @Transactional
    public void saveBrand( List<Long> cids,Brand brand) {
        //新增品牌
        brand.setId(null);
        int count= brandMapper.insert(brand);
        if(count !=1){
            throw new LyException(ExceptionEnum.BRAND_SAVE_ERROR);
        }
        //新增中间表
        for (Long cid:cids){
          count=  brandMapper.insertCategoryBrand(cid,brand.getId());
        }
        if (count !=1){
            throw new LyException(ExceptionEnum.CATEGORY_BRAND_SAVE_ERROR);
        }
    }



    /**
     * 品牌的修改
     *
     * @param categories
     * @param brand
     */
    @Transactional
    public void updateBrand(List<Long> categories, Brand brand) {
        //修改品牌
        brandMapper.updateByPrimaryKeySelective(brand);
        //维护中间表
        for (Long categoryId : categories) {
            brandMapper.updateCategoryBrand(categoryId, brand.getId());
        }

    }

    /**
     * 品牌的删除后
     * @param bid
     */
    public void deleteBrand(Long bid) {
        //删除品牌表
        brandMapper.deleteByPrimaryKey(bid);
        //维护中间表
        brandMapper.deleteCategoryBrandByBid(bid);
    }

    /**
     * 根据品牌brandid查询品牌名称
     * @param brandId
     * @return
     */
    public String queryBrandNameByBid(Long brandId) {
        Brand brand = this.brandMapper.selectByPrimaryKey(brandId);
        return brand.getName();
    }

    /**
     * 根据cid查到所有的品牌
     * @param cid
     * @return
     */
    public List<Brand> queryBrandsByCategoryId(Long cid) {
        //先根据cid查到所有的品牌id
        List<Long> bids = brandMapper.selectBrandIdsByCategoryId(cid);
        //根据品牌id查到所有的信息
        return this.queryBrandsByBids(bids);
    }


    /**
     * 根据品牌bid查询品牌信息
     * @param id
     * @return
     */
    public Brand queryBrandByBid(Long id) {
        Brand brand = new Brand();
        brand.setId(id);
        Brand b1 = brandMapper.selectByPrimaryKey(brand);
        if (b1 == null) {
            throw new LyException(ExceptionEnum.BRAND_NOT_FOUND);
        }
        return b1;
    }

    /**
     * 根据bid的集合查询品牌信息
     */
    public List<Brand> queryBrandsByBids(List<Long> bids){
        return this.brandMapper.selectByIdList(bids);
    }
}
