package com.example.admin_java.global;

import com.example.admin_java.result.Result;
import com.example.admin_java.result.ResultUtil;
import org.springframework.validation.BindException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * @Author: Think
 * @Date: 2018/5/27
 * @Time: 13:59
 */
@RestControllerAdvice
public class BindExceptionHandler {

    @ExceptionHandler(BindException.class)
    public Result handlerBindException(BindException ex) {
        FieldError fieldError = ex.getFieldError();
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(fieldError.getField()).append("=[").append(fieldError.getRejectedValue())
                .append("]").append(fieldError.getDefaultMessage());
        Result result = new Result();
        result.setCode(-2);
        result.setMessage(stringBuilder.toString());
        return result;
    }

}
