package com.nuwa.zeus.start.api.config.shiro.exception;


import com.alibaba.cola.exception.BizException;
import com.alibaba.cola.exception.ErrorCodeI;

public class AuthException  extends BizException {

    public AuthException(String errMessage) {
        super(errMessage);
    }

    public AuthException(ErrorCodeI errCode, String errMessage) {
        super(errCode, errMessage);
    }

    public AuthException(String errMessage, Throwable e) {
        super(errMessage, e);
    }
}
