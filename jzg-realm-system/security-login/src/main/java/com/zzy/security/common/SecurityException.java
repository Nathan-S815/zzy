package com.zzy.security.common;

import org.springframework.security.core.AuthenticationException;

public class SecurityException extends AuthenticationException {


    public SecurityException(String msg, Throwable t) {
        super(msg, t);
    }

    public SecurityException(String msg) {
        super(msg);
    }
}
