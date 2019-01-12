package com.ly.sms.properties;



/**
 * @author Lee
 * @date 2019/1/1
 */

public class SmsProperties {
    //默认值
    private static final String REGIST_MSG_Model = "126926";
    private static final String USER_KEY = "b292ee7d2211379c6d56c87b3f86a9da";
    //短信模板ID
    private String tpl_id;
    //用户Key
    private String key;

    public String getTpl_id() {
        return REGIST_MSG_Model;
    }

    public void setTpl_id(String tpl_id) {
        this.tpl_id = tpl_id;
    }

    public String getKey() {
        return USER_KEY;
    }

    public void setKey(String key) {
        this.key = key;
    }
}
