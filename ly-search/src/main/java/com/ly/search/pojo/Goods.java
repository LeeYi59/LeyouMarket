package com.ly.search.pojo;

import lombok.Data;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

import javax.persistence.Id;
import java.util.Date;
import java.util.Map;
import java.util.Set;

/**
 * @program: ly
 * @description: Lyshop
 * @author: Lee
 * @create: 2018-12-26 17:13
 **/
@Data
@Document(indexName = "goods", type = "docs", shards = 1, replicas = 0)
public class Goods {

    @Id
    private Long id;  //SpuId

    @Field(type = FieldType.text, analyzer = "ik_max_word")
    private String all;  //所有需要被搜索的信息，包括品牌，分类，标题

    @Field(type = FieldType.keyword, index = false)
    private String subtitle;  //父标题

    private Long brandId;
    private Long cid1;
    private Long cid2;
    private Long cid3;

    private Date createTime;
    private Set<Double> price;  //是所有sku的价格集合。方便根据价格进行筛选过滤

    @Field(type = FieldType.keyword, index = false)
    private String skus;  //sku信息的json结构数据
    private Map<String, Object> specs;  //可搜索的规格参数，key是参数名，值是参数值
}