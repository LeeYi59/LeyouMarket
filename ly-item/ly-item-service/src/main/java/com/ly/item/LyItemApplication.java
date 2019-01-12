package com.ly.item;/**
 * Create By IvanLee on 2018/12/14
 */

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.web.bind.annotation.ResponseBody;
import tk.mybatis.spring.annotation.MapperScan;


/**
 *@ClassName LyItemApplication
 *@Description Todo
 *@Author Lee
 *@Date 2018/12/14 16:24
 *@Version 1.0
 **/
@SpringBootApplication
@EnableDiscoveryClient
@ResponseBody
@MapperScan("com.ly.item.mapper")
public class LyItemApplication {
    public static void main(String[] args) {
        SpringApplication.run(LyItemApplication.class,args);
    }
}
