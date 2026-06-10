package com.zzy.security.common;


import com.zzy.core.dto.R;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;


@Slf4j
@RestControllerAdvice("advice")
public class SecurityConfigura {

    @ExceptionHandler(Exception.class)
    public R handler(Exception e) {
        if (e instanceof AccessDeniedException) {
            log.error("异常:{}",e.getMessage());
            return  R.error("访问无权限");
        } else {//其他未捕获异常
            log.error("异常",e);
            return  R.error("内部错误,请联系管理员!");
        }
    }


}
