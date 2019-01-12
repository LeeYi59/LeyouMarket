package com.ly.search.client;

import com.ly.item.api.BrandApi;
import org.springframework.cloud.openfeign.FeignClient;

/**
 * @program: ly
 * @description: Lyshop
 * @author: Lee
 * @create: 2018-12-26 21:27
 **/
@FeignClient("item-service")
public interface BrandClient extends BrandApi {
}
