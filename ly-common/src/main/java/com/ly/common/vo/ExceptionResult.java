package com.ly.common.vo;/**
 * Create By IvanLee on 2018/12/14
 */

import com.ly.common.enums.ExceptionEnum;
import lombok.Data;

/**
 *@ClassName ExceptionResult
 *@Description 异常返回结果
 *@Author Lee
 *@Date 2018/12/14 23:00
 *@Version 1.0
 **/
//异常返回结果
@Data
public class ExceptionResult {
private int status;
private String message;
private long timestamp;

    public ExceptionResult(ExceptionEnum em){
        this.status=em.code();
        this.message=em.msg();
        this.timestamp=System.currentTimeMillis();
    }
}
