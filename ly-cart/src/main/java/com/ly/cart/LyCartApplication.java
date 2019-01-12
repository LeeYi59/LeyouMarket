package com.ly.cart;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * @author Lee
 * @date 2019/1/3
 */
@SpringBootApplication
@EnableDiscoveryClient
public class LyCartApplication {

    public static void main(String[] args) {
        SpringApplication.run(LyCartApplication.class, args);
    }
}
