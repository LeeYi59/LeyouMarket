package com.ly.search.client;

import com.ly.item.api.SpecApi;
import org.springframework.cloud.openfeign.FeignClient;

/**
 * @program: ly
 * @description: Lyshop
 * @author: Lee
 * @create: 2018-12-26 21:30
 **/
@FeignClient("item-service")
public interface SpecClient extends SpecApi {
}
