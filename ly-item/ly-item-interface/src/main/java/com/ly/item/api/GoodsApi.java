package com.ly.item.api;

import com.ly.common.vo.PageResult;
import com.ly.item.dto.CartDto;
import com.ly.item.pojo.Sku;
import com.ly.item.pojo.Spu;
import com.ly.item.pojo.SpuDetail;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @program: ly
 * @description: Lyshop
 * @author: Lee
 * @create: 2018-12-26 21:02
 **/

public interface GoodsApi {
    /**
     * 分页查询商品
     *
     * @param page
     * @param rows
     * @param saleable
     * @param key
     * @return
     */
    @GetMapping("/spu/page")
    PageResult<Spu> querySpuByPage(
            @RequestParam(value = "page", defaultValue = "1") Integer page,
            @RequestParam(value = "rows", defaultValue = "5") Integer rows,
            @RequestParam(value = "saleable", defaultValue = "true") Boolean saleable,
            @RequestParam(value = "key", required = false) String key);

    /**
     * 根据spu商品id查询详情
     *
     * @param id
     * @return
     */
    @GetMapping("/spu/detail/{id}")
    SpuDetail querySpuDetailById(@PathVariable("id") Long id);

    /**
     * 根据spu的id查询sku
     *
     * @param id
     * @return
     */
    @GetMapping("sku/list")
    List<Sku> querySkuBySpuId(@RequestParam("id") Long id);

    /**
     * 根据sku ids查询sku
     * @param ids
     * @return
     */
    @GetMapping("sku/list/ids")
    List<Sku> querySkusByIds(@RequestParam("ids") List<Long> ids);


    /**
     * 根据spuId查询spu及skus
     * @param spuId
     * @return
     */
    @GetMapping("spu/{id}")
    Spu querySpuBySpuId(@PathVariable("id") Long spuId);

    /**
     * 减库存
     * @param cartDTOS
     */
    @PostMapping("stock/decrease")
    void decreaseStock(@RequestBody List<CartDto> cartDTOS);
}
