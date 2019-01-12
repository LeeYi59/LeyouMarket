package com.ly.sms.listener;

import com.ly.common.utils.JsonUtils;
import com.ly.sms.pojo.SmsResult;
import com.ly.sms.properties.SmsProperties;
import com.ly.sms.utils.SmsUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.amqp.core.ExchangeTypes;
import org.springframework.amqp.rabbit.annotation.Exchange;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.QueueBinding;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

/**
 * @author bystander
 * @date 2018/9/29
 */
@Slf4j
@Component
public class SmsListener {



    @Autowired
    private SmsUtil smsUtil;

    /**
     * 发送验证码
     * @param msg
     */
    @RabbitListener(bindings = @QueueBinding(
            value = @Queue(name = "ly.sms.verify.code.queue"),
            exchange = @Exchange(name = "ly.sms.exchange", type = ExchangeTypes.TOPIC),
            key = "sms.verify.code"
    ))
    public void listenVerifyCode(Map<String, Object> msg) {
        if (msg == null) {
            return;
        }
        String phone = (String) msg.remove("phone");
        String code=(String) msg.remove("code");
        if (StringUtils.isBlank(phone) || StringUtils.isBlank(code)) {
            return;
        }
        System.out.println("手机号："+phone);
        System.out.println("验证码："+code);

        SmsProperties props=new SmsProperties();
        Map params = new HashMap();//请求参数
        params.put("mobile",phone);//接受短信的用户手机号码
        params.put("tpl_id",props.getTpl_id());//短信模板ID
        params.put("tpl_value","#code#="+code);//验证码值
        params.put("key",props.getKey());//应用APPKEY

        smsUtil.sendSms(params);


    }


}
