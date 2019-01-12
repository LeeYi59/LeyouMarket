package com.ly.search.client;

import com.ly.item.api.CategoryApi;
import org.springframework.cloud.openfeign.FeignClient;

/**
 * @program: ly
 * @description: Lyshop
 * @author: Lee
 * @create: 2018-12-26 20:21
 **/
@FeignClient("item-service")
public interface CategoryClient extends CategoryApi {


}
