package com.ly.sms.pojo;

import lombok.Data;

@Data
public class SmsResult {
    private String reason;
    private ReasultDetail result;
    private Long error_code;
}
