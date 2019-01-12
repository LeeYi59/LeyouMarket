package com.ly.item.api;

import com.ly.item.pojo.Brand;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

/**
 * @program: ly
 * @description: Lyshop
 * @author: Lee
 * @create: 2018-12-26 20:57
 **/
@RequestMapping("brand")
public interface BrandApi {
    @GetMapping("/{id}")
    Brand queryById(@PathVariable("id") Long id);

    @GetMapping("/list")
    List<Brand> queryBrandsByBids(@RequestParam("ids") List<Long> ids);
}
