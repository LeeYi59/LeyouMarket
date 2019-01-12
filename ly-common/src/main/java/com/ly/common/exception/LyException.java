package com.ly.common.exception;/**
 * Create By IvanLee on 2018/12/14
 */

import com.ly.common.enums.ExceptionEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 *@ClassName LyException
 *@Description 通用异常
 *@Author Lee
 *@Date 2018/12/14 22:28
 *@Version 1.0
 **/
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class LyException  extends RuntimeException{
    private ExceptionEnum exceptionEnum;
}
