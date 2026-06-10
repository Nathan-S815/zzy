package com.nuwa.infrastructure.discovery.common.exception;

import com.alibaba.cola.exception.BizException;
import com.alibaba.cola.exception.ErrorCodeI;

public class FileUploadException extends BizException {
    public FileUploadException(String errMessage) {
        super(errMessage);
    }

    public FileUploadException(ErrorCodeI errCode, String errMessage) {
        super(errCode, errMessage);
    }

    public FileUploadException(String errMessage, Throwable e) {
        super(errMessage, e);
    }
}
