package com.ly.common.mapper;

import tk.mybatis.mapper.additional.idlist.IdListMapper;
import tk.mybatis.mapper.additional.insert.InsertListMapper;
import tk.mybatis.mapper.annotation.RegisterMapper;
import tk.mybatis.mapper.common.Mapper;

/**
 * @program: ly
 * @description: Lyshop
 * @author: Lee
 * @create: 2018-12-25 13:52
 **/
@RegisterMapper
public interface BaseMapper<T, PK> extends Mapper<T>, IdListMapper<T, PK>, InsertListMapper<T> {
}