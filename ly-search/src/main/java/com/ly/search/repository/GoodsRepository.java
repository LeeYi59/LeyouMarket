package com.ly.search.repository;

import com.ly.search.pojo.Goods;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * @author Lee
 * @date 2018/12/22
 */
public interface GoodsRepository extends ElasticsearchRepository<Goods, Long> {
}
