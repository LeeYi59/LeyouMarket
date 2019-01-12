package com.ly.gateway;/**
 * Create By IvanLee on 2018/12/14
 */

import org.springframework.boot.SpringApplication;
import org.springframework.cloud.client.SpringCloudApplication;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;

/**
 *@ClassName LyGateway
 *@Description Todo
 *@Author Lee
 *@Date 2018/12/14 14:56
 *@Version 1.0
 **/
@EnableZuulProxy
@SpringCloudApplication
public class LyGateway {
    public static void main(String[] args) {
        SpringApplication.run(LyGateway.class,args);
    }
}
