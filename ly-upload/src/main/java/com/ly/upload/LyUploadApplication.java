package com.ly.upload;/**
 * Create By IvanLee on 2018/12/19
 */

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 *@ClassName LyUploadApplication
 *@Description Todo
 *@Author Lee
 *@Date 2018/12/19 19:07
 *@Version 1.0
 **/
@SpringBootApplication
@EnableDiscoveryClient
public class LyUploadApplication {
    public static void main(String[] args) {
        SpringApplication.run(LyUploadApplication.class,args);
    }
}
