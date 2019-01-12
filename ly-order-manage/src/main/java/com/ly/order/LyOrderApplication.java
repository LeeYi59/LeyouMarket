package com.ly.order;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import tk.mybatis.spring.annotation.MapperScan;

@EnableDiscoveryClient
@SpringBootApplication
@EnableFeignClients
@MapperScan(value = "com.ly.order.mapper")
public class LyOrderApplication {
    public static void main(String[] args) {
        SpringApplication.run(LyOrderApplication.class,args);
    }
}
