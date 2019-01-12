package com.ly.page.client;

import com.ly.item.api.GoodsApi;
import org.springframework.cloud.openfeign.FeignClient;

/**
 * @author Lee
 * @date 2018/12/22
 */
@FeignClient(value = "item-service")
public interface GoodsClient extends GoodsApi {
}
