package com.ly.user.utils;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang3.StringUtils;

import java.util.UUID;

/**
 * @author: Lee
 * @create: 2018-12-30 15:56
 **/
public class CodecUtils {
    /**
     * 密码加密
     * @param data
     * @param salt
     * @return
     */


    public static String md5Hex(String data,String salt) {
        if (StringUtils.isBlank(salt)) {
            salt = data.hashCode() + "";
        }
        return DigestUtils.md5Hex(salt + DigestUtils.md5Hex(data));
    }

    public static String shaHex(String data, String salt) {
        if (StringUtils.isBlank(salt)) {
            salt = data.hashCode() + "";
        }
        return DigestUtils.sha512Hex(salt + DigestUtils.sha512Hex(data));
    }

    public static String generateSalt(){
        return StringUtils.replace(UUID.randomUUID().toString(), "-", "");
    }
}
