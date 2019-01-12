package com.ly.order.service;

import com.ly.auth.entity.UserInfo;
import com.ly.common.enums.ExceptionEnum;
import com.ly.common.exception.LyException;
import com.ly.order.filter.LoginInterceptor;
import com.ly.order.mapper.AddressMapper;
import com.ly.order.pojo.Address;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.util.List;

@Service
public class AddressService {

    @Autowired
    private AddressMapper addressMapper;


    public List<Address> queryAddressList() {

        Address address=new Address();
        address.setUserId(getUser());
        List<Address> addresses=addressMapper.select(address);

        return addresses;
    }


    public void addAddress(Address address) {
        address.setUserId(getUser());
        if (!StringUtils.isEmpty(address)) {
            int count=addressMapper.insert(address);
            if (count != 1){
              throw   new LyException(ExceptionEnum.ADDRESS_CREATE_FAILED);
            }
        }
    }


    private String getUser(){
        //获取用户信息
        UserInfo user = LoginInterceptor.getLoginUser();
        String userId=String.valueOf(user.getId());
        return userId;
    }

    public Address queryAddressById(Long id) {
        Address address=addressMapper.selectByPrimaryKey(id);
        return address;
    }

    @Transactional
    public void deleteAddressById(Long id) {
        int count=addressMapper.deleteByPrimaryKey(id);
        if (count != 1){
            throw   new LyException(ExceptionEnum.ADDRESS_DELETE_FAILED);
        }
    }
    @Transactional
    public void updateAddressById(Address address) {
        address.setUserId(getUser());
        addressMapper.updateByPrimaryKey(address);
    }
}
