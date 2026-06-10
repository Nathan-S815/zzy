package com.nuwa.attract.start.dispatch.controller;

import com.alibaba.cola.dto.Response;
import com.alibaba.cola.exception.BizException;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authz.AuthorizationException;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Objects;

@ControllerAdvice
@Slf4j
public class ExceptionControllerAdvice {

    /**
     * 拦截表单验证异常
     */
    @ExceptionHandler(BindException.class)
    @ResponseBody
    public Response bindException(BindException e) {
        BindingResult bindingResult = e.getBindingResult();
        String defaultMessage = Objects.requireNonNull(bindingResult.getFieldError()).getDefaultMessage();
        log.error("表单验证失败,message={}", e.getMessage());
        return Response.buildFailure("901", defaultMessage);
    }

    /**
     * AuthorizationException
     */
    @ExceptionHandler(AuthorizationException.class)
    @ResponseBody
    public Response authorizationException(AuthorizationException e) {
        log.error("权限认证异常", e);
        return Response.buildFailure("6031", "权限认证异常");
    }

    /**
     * BizException
     */
    @ExceptionHandler(BizException.class)
    @ResponseBody
    public Response httpRequestMethodNotSupportedException(BizException e) {
        log.error("BizException error", e);
        return Response.buildFailure(e.getErrCode().getErrCode(), e.getMessage());
    }


    /**
     * 拦截表单验证异常
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseBody
    public Response methodArgumentNotValidException(MethodArgumentNotValidException e) {
        BindingResult bindingResult = e.getBindingResult();
        String defaultMessage = Objects.requireNonNull(bindingResult.getFieldError()).getDefaultMessage();
        return Response.buildFailure("902", defaultMessage);
    }

    /**
     * 拦截未知的运行时异常
     */
    @ExceptionHandler(RuntimeException.class)
    @ResponseBody
    public Response runtimeException(RuntimeException e) {
        log.error("[系统异常]", e);
        return Response.buildFailure("500", "系统异常");
    }

    /**
     * 拦截未知的运行时异常
     */
    @ExceptionHandler(Throwable.class)
    @ResponseBody
    public Response runtimeException(Throwable e) {
        log.error("[系统异常]", e);
        return Response.buildFailure("500", "系统异常");
    }

    /**
     * IllegalArgumentException
     */
    @ExceptionHandler(IllegalArgumentException.class)
    @ResponseBody
    public Response illegalArgumentException(IllegalArgumentException e) {
        log.error("[参数异常]", e);
        return Response.buildFailure("903", e.getMessage());
    }

    /**
     * HttpRequestMethodNotSupportedException
     */
    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    @ResponseBody
    public Response httpRequestMethodNotSupportedException(HttpRequestMethodNotSupportedException e) {
        String method = e.getMethod();
        log.error("method:{} not supported", method);
        log.error("[系统异常]", e);
        return Response.buildFailure("904", e.getMessage());
    }

}
