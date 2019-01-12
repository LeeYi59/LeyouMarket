package com.ly.auth.service;

import com.ly.auth.client.UserClient;
import com.ly.auth.entity.UserInfo;
import com.ly.auth.properties.JwtProperties;
import com.ly.auth.utils.JwtUtils;
import com.ly.common.enums.ExceptionEnum;
import com.ly.common.exception.LyException;
import com.ly.user.pojo.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.stereotype.Service;

/**
 * @author Lee
 * @date 2018/1/1
 */
@Slf4j
@Service
@EnableConfigurationProperties(JwtProperties.class)
public class AuthService {

    @Autowired
    private UserClient userClient;

    @Autowired
    private JwtProperties props;


    public String login(String username, String password) {
        try {
            User user = userClient.queryUser(username, password);
            if (user == null) {
                return null;
            }
            UserInfo userInfo = new UserInfo(user.getId(), user.getUsername());
            //生成Token
            String token = JwtUtils.generateToken(userInfo, props.getPrivateKey(), props.getExpire());
            return token;
        } catch (Exception e) {
            log.error("【授权中心】用户名和密码错误，用户名：{}", username,e);
            throw new LyException(ExceptionEnum.USERNAME_OR_PASSWORD_ERROR);
        }
    }
}
