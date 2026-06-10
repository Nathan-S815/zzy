package com.nuwa.infrastructure.discovery.common.exception;

import com.alibaba.cola.exception.BizException;
import com.alibaba.cola.exception.ErrorCodeI;

public class UserNotExistException extends BizException {
    public UserNotExistException(String errMessage) {
        super(errMessage);
    }

    public UserNotExistException(ErrorCodeI errCode, String errMessage) {
        super(errCode, errMessage);
    }

    public UserNotExistException(String errMessage, Throwable e) {
        super(errMessage, e);
    }
}
