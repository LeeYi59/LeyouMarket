package com.ly.order.pojo;

import lombok.Data;
import tk.mybatis.mapper.annotation.KeySql;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;

@Data
@Table(name = "tb_address")
public class Address {
    @Id
    @KeySql(useGeneratedKeys = true)
    @Column(name = "address_id")
    private Long id;
    @Column(name = "receiver_name")
    private String name;
    @Column(name = "receiver_state")
    private String state;
    @Column(name = "receiver_city")
    private String city;
    @Column(name = "receiver_district")
    private String district;
    @Column(name = "receiver_address")
    private String address;
    @Column(name = "receiver_mobile")
    private String phone;
    @Column(name = "receiver_zip")
    private String zipCode;
    @Column(name = "user_id")
    private String userId;
    @Column(name = "‘isDefault’")
    private Boolean defaultAddress;



}
