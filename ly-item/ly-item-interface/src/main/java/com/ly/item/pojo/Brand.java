package com.ly.item.pojo;/**
 * Create By IvanLee on 2018/12/17
 */

import lombok.Data;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 *@ClassName Brand
 *@Description Todo
 *@Author Lee
 *@Date 2018/12/17 22:25
 *@Version 1.0
 **/
@Table(name = "tb_brand")
@Data
public class Brand {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;// 品牌名称
    private String image;// 品牌图片
    private Character letter;
}