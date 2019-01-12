package com.ly.sms.utils;

import com.ly.common.utils.JsonUtils;
import com.ly.sms.pojo.SmsResult;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * 短信工具类
 * @author Administrator
 *
 */
@Slf4j
@Component
public class SmsUtil {
	public static final String DEF_CHATSET = "UTF-8";
	public static final int DEF_CONN_TIMEOUT = 30000;
	public static final int DEF_READ_TIMEOUT = 30000;
	public static String userAgent =  "Mozilla/5.0 (Windows NT 6.1) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/29.0.1547.66 Safari/537.36";
	private static final String SMS_PREFIX = "sms:phone:";
	private static final long SMS_MIN_INTERVAL_IN_MILLIS = 60000;

    @Autowired
	private StringRedisTemplate redisTemplate;
	@Autowired
	private StringRedisTemplate template;
    
    /**
     * 发生短信
     * @param params 所需资源

     */
	public  void sendSms(Map params){
		String phone=(String) params.get("mobile");
		String key = SMS_PREFIX + phone;

		//限流
		String lastTime = template.opsForValue().get(key);
		log.info(lastTime);
		if (StringUtils.isNotBlank(lastTime)) {
			//Redis中键不为空
			Long last = Long.valueOf(lastTime);
			if (System.currentTimeMillis() - last < SMS_MIN_INTERVAL_IN_MILLIS) {
				//Redis中发送信息的手机号为超过1min,则返回空，不进行短信发送
				log.info("【短信服务】短信发送频率过高，被拦截，手机号：{}", phone);
				return;
			}
		}

		String back =null;
		String url ="http://v.juhe.cn/sms/send";//请求接口地址
		try {
			back = net(url, params, "GET");

					//发送短信成功后，加入redis,指定生存时间为1分钟
					redisTemplate.opsForValue().set(key, String.valueOf(System.currentTimeMillis()),1, TimeUnit.MINUTES);
					log.info("【短信服务】，短信已发送并加入缓存，发送结果为："+back);


		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 *
	 * @param strUrl 请求地址
	 * @param params 请求参数
	 * @param method 请求方法
	 * @return  网络请求字符串
	 * @throws Exception
	 */
	public static String net(String strUrl, Map params,String method) throws Exception {
		HttpURLConnection conn = null;
		BufferedReader reader = null;
		String rs = null;
		try {
			StringBuffer sb = new StringBuffer();
			if(method==null || method.equals("GET")){
				strUrl = strUrl+"?"+urlencode(params);
			}
			URL url = new URL(strUrl);
			conn = (HttpURLConnection) url.openConnection();
			if(method==null || method.equals("GET")){
				conn.setRequestMethod("GET");
			}else{
				conn.setRequestMethod("POST");
				conn.setDoOutput(true);
			}
			conn.setRequestProperty("User-agent", userAgent);
			conn.setUseCaches(false);
			conn.setConnectTimeout(DEF_CONN_TIMEOUT);
			conn.setReadTimeout(DEF_READ_TIMEOUT);
			conn.setInstanceFollowRedirects(false);
			conn.connect();
			if (params!= null && method.equals("POST")) {
				try {
					DataOutputStream out = new DataOutputStream(conn.getOutputStream());
					out.writeBytes(urlencode(params));
				} catch (Exception e) {
					// TODO: handle exception
					e.printStackTrace();
				}
			}
			InputStream is = conn.getInputStream();
			reader = new BufferedReader(new InputStreamReader(is, DEF_CHATSET));
			String strRead = null;
			while ((strRead = reader.readLine()) != null) {
				sb.append(strRead);
			}
			rs = sb.toString();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (reader != null) {
				reader.close();
			}
			if (conn != null) {
				conn.disconnect();
			}
		}
		return rs;
	}

	//将map型转为请求参数型
	public static String urlencode(Map<String,String> data) {
		StringBuilder sb = new StringBuilder();
		for (Map.Entry i : data.entrySet()) {
			try {
				sb.append(i.getKey()).append("=").append(URLEncoder.encode(i.getValue()+"","UTF-8")).append("&");
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}
		}
		return sb.toString();
	}
} 