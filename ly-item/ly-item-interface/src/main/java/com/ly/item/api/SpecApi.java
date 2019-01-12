package com.ly.item.api;

import com.ly.item.pojo.SpecGroup;
import com.ly.item.pojo.SpecParam;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

/**
 * @program: ly
 * @description: Lyshop
 * @author: Lee
 * @create: 2018-12-26 21:15
 **/
@RequestMapping("spec")
public interface SpecApi {
    // 查询规格参数组，及组内参数
    @GetMapping("{cid}")
    List<SpecGroup> querySpecsByCid(@PathVariable("cid") Long cid);

    @GetMapping("params")
    List<SpecParam> querySpecParams(
            @RequestParam(value = "gid", required = false) Long gid,
            @RequestParam(value = "cid", required = false) Long cid,
            @RequestParam(value = "searching", required = false) Boolean searching,
            @RequestParam(value = "generic", required = false) Boolean generic
    );

    @GetMapping("groups/{cid}")
    List<SpecGroup> queryGroupByid(@PathVariable("cid") Long cid);
}
