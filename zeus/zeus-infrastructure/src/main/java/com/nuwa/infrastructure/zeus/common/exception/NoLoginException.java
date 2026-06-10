package com.nuwa.infrastructure.zeus.common.exception;

import com.alibaba.cola.exception.BizException;
import com.alibaba.cola.exception.ErrorCodeI;

/**
 * NoLoginException 未登录异常信息
 *
 * @author hy
 * @date 2021/4/30 18:01
 * @since 1.0.0
 */
public class NoLoginException extends BizException {

    public NoLoginException(String errMessage) {
        super(errMessage);
    }

    public NoLoginException(ErrorCodeI errCode, String errMessage) {
        super(errCode, errMessage);
    }

    public NoLoginException(String errMessage, Throwable e) {
        super(errMessage, e);
    }
}
