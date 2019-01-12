package com.ly.item.pojo;/**
 * Create By IvanLee on 2018/12/16
 */

import lombok.Data;
import tk.mybatis.mapper.annotation.KeySql;

import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;
import java.util.List;

/**
 *@ClassName Category
 *@Description Todo
 *@Author Lee
 *@Date 2018/12/16 15:02
 *@Version 1.0
 **/
@Table(name="tb_category")
@Data
public class Category {
    @Id
    @KeySql(useGeneratedKeys=true)
    private Long id;
    private String name;
    private Long parentId;
    private Boolean isParent;
    private Integer sort;


    @Transient
    private List<Category> categoryList;
}
