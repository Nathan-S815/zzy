package com.nuwa.zeus.start.provider.config;

import com.alibaba.cola.dto.Response;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
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
    public Response IllegalArgumentException(IllegalArgumentException e) {
        log.error("[参数异常]", e);
        return Response.buildFailure("903", e.getMessage());
    }

}
