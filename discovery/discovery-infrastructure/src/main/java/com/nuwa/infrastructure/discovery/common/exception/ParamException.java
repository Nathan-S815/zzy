package com.nuwa.infrastructure.discovery.common.exception;

import com.alibaba.cola.exception.BizException;
import com.alibaba.cola.exception.ErrorCodeI;

public class ParamException extends BizException {
    public ParamException(String errMessage) {
        super(errMessage);
    }

    public ParamException(ErrorCodeI errCode, String errMessage) {
        super(errCode, errMessage);
    }

    public ParamException(String errMessage, Throwable e) {
        super(errMessage, e);
    }
}
