package com.ly.common.advice;/**
 * Create By IvanLee on 2018/12/14
 */

import com.ly.common.exception.LyException;
import com.ly.common.vo.ExceptionResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 *@ClassName CommonExceptionHandler
 *@Description 自定义异常通知
 *@Author Lee
 *@Date 2018/12/14 21:49
 *@Version 1.0
 **/
@Slf4j
@ControllerAdvice
public class CommonExceptionHandler {
    @ExceptionHandler({LyException.class})
    public ResponseEntity<ExceptionResult> handlerException(LyException e){
        return ResponseEntity.status(e.getExceptionEnum().code())
                .body(new ExceptionResult(e.getExceptionEnum())) ;
    }
}
