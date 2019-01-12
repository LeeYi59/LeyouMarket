package com.ly.registry;/**
 * Create By IvanLee on 2018/12/14
 */

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

/**
 *@ClassName LyRegistry
 *@Description Todo
 *@Author Lee
 *@Date 2018/12/14 14:49
 *@Version 1.0
 **/
@EnableEurekaServer
@SpringBootApplication
public class LyRegistry {
    public static void main(String[] args) {
        SpringApplication.run(LyRegistry.class,args);
    }
}
