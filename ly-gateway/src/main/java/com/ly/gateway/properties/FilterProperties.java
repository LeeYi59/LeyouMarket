package com.ly.gateway.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.List;

/**
 * @author Lee
 * @date 2018/1/2
 */
@Data
@ConfigurationProperties(prefix = "ly.filter")
public class FilterProperties {

    private List<String> allowPaths;


}
