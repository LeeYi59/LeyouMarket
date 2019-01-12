package com.ly.auth.client;

import api.UserApi;
import org.springframework.cloud.openfeign.FeignClient;

/**
 * @author Lee
 * @date 2018/1/1
 */
@FeignClient(value = "user-service")
public interface UserClient extends UserApi {
}
