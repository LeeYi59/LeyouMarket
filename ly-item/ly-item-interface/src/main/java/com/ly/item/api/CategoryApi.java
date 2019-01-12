package com.ly.item.api;

import com.ly.item.pojo.Category;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

/**
 * @program: ly
 * @description: Lyshop
 * @author: Lee
 * @create: 2018-12-26 20:56
 **/
@RequestMapping("category")
public interface CategoryApi {

    @GetMapping("/list/ids")
    List<Category> queryCategoryByIds(@RequestParam("ids") List<Long> ids);
}
