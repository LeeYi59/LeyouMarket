package com.ly.order.mapper;

import com.ly.order.pojo.Address;
import tk.mybatis.mapper.additional.insert.InsertListMapper;
import tk.mybatis.mapper.common.Mapper;

public interface AddressMapper extends Mapper<Address>, InsertListMapper<Address> {
}
