package com.ly.common.vo;/**
 * Create By IvanLee on 2018/12/17
 */

import lombok.Data;

import java.util.List;

/**
 *@ClassName PageResult
 *@Description 页面返回结果
 *@Author Lee
 *@Date 2018/12/17 22:39
 *@Version 1.0
 **/
@Data
public class PageResult<T> {
    private Long total;// 总条数
    private Integer totalPage;// 总页数
    private List<T> items;// 当前页数据

    public PageResult() {
    }

    public PageResult(Long total, List<T> items) {
        this.total = total;
        this.items = items;
    }

    public PageResult(Long total, Integer totalPage, List<T> items) {
        this.total = total;
        this.totalPage = totalPage;
        this.items = items;
    }


}