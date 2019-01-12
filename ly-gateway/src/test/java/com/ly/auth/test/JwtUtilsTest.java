package com.ly.auth.test;


import com.ly.auth.entity.UserInfo;
import com.ly.auth.utils.JwtUtils;
import com.ly.auth.utils.RsaUtils;
import org.junit.Before;
import org.junit.Test;

import java.security.PrivateKey;
import java.security.PublicKey;

/**
 * @author bystander
 * @date 2018/10/1
 */
public class JwtUtilsTest {

    private static final String publicKeyPath = "C:\\tmp\\rsa\\rsa.pub";
    private static final String privateKeyPath = "C:\\tmp\\rsa\\rsa.pri";

    private PrivateKey privateKey;
    private PublicKey publicKey;


    @Test
    public void testRsa() throws Exception {
        RsaUtils.generateKey(publicKeyPath, privateKeyPath, "234");
    }

    @Before
    public void testGetRsa() throws Exception {
        privateKey = RsaUtils.getPrivateKey(privateKeyPath);
        publicKey = RsaUtils.getPublicKey(publicKeyPath);
    }

    @Test
    public void generateToken() {
        //生成Token
        String s = JwtUtils.generateToken(new UserInfo(20L, "Jack"), privateKey, 5);
        System.out.println("s = " + s);
    }


    @Test
    public void parseToken() {
        String token = "eyJhbGciOiJSUzI1NiJ9.eyJpZCI6MjAsInVzZXJuYW1lIjoiSmFjayIsImV4cCI6MTU0NjM1OTI0NH0.kYJXXgoL43M--4liwzKX9yO3FmdL5jsO3tb5zEimsl3Gawt4tIBNFCGS8wXyBEZ1Tsk3ONpuWa3Pfxe3bIlqthq7EXIUoQfHG9qQmLx8Htk4koQR3dbetQKnIcM_mrtW940MN8aVTPp-VP9Ppd-oP-DigakgIo5uQ3FnQCqmWBE";
        UserInfo userInfo = JwtUtils.getUserInfo(publicKey, token);
        System.out.println("id:" + userInfo.getId());
        System.out.println("name:" + userInfo.getName());
    }

    @Test
    public void parseToken1() {
    }

    @Test
    public void getUserInfo() {
    }

    @Test
    public void getUserInfo1() {
    }
}