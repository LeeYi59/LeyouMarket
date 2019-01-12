package com.ly.search.repository;

import com.ly.common.vo.PageResult;
import com.ly.item.pojo.Spu;
import com.ly.search.client.GoodsClient;
import com.ly.search.pojo.Goods;
import com.ly.search.service.SearchService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class GoodsRepositoryTest {
    @Autowired
    private GoodsRepository repository;

    @Autowired
    private ElasticsearchTemplate template;

    @Autowired
    private GoodsClient goodsClient;

    @Autowired
    private SearchService searchService;
    //添加索引
    @Test
    public  void testCreateIndex(){
        template.createIndex(Goods.class);
        template.putMapping(Goods.class);

    }
    @Test
    public void loadData() {
        int page = 1;
        //size不足100则是最后一页
        int size = 0;
        int rows = 100;
        do {
            //查询所有上架商品
            PageResult<Spu> result = goodsClient.querySpuByPage(page, rows, true, null);
            ArrayList<Goods> goodList = new ArrayList<>();
            List<Spu> spus = result.getItems();
            size = spus.size();
            for (Spu spu : spus) {
                try {
                    Goods g = searchService.buildGoods(spu);
                    goodList.add(g);

                } catch (Exception e) {
                    break;
                }
            }
            this.repository.saveAll(goodList);
            page++;
        } while (size == 100);
    }

}