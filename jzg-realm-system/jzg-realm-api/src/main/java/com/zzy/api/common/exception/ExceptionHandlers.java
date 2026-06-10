package com.zzy.api.common.exception;


import com.alibaba.fastjson.JSONException;
import com.zzy.core.dto.R;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;


@RestControllerAdvice
public class ExceptionHandlers {

    private static final Logger LOGGER = LoggerFactory.getLogger(ExceptionHandlers.class);



    @ExceptionHandler(Exception.class)
    public R handler(Exception e) {
        LOGGER.error("异常:{}",e);
        if (e instanceof HttpRequestMethodNotSupportedException) {
            return  R.error("方法类型不匹配");
        } else if (e instanceof IllegalArgumentException) {
            return  R.error(e.getMessage());
        } else if (e instanceof MissingServletRequestParameterException) {
            return  R.error(e.getMessage());
        } else if(e instanceof JSONException){
            return R.error(e.getMessage());
        }else {//其他未捕获异常
            return  R.error("内部错误,请联系管理员!");
        }
    }

}
