package com.ly.page.client;

import com.ly.item.api.CategoryApi;
import org.springframework.cloud.openfeign.FeignClient;

/**
 * @author Lee
 * @date 2018/12/22
 */
@FeignClient("item-service")
public interface CategoryClient extends CategoryApi {
}
