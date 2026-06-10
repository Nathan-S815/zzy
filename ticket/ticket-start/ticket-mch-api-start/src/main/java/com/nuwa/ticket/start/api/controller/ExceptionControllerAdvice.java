package com.nuwa.ticket.start.api.controller;

import com.alibaba.cola.dto.Response;
import com.alibaba.cola.exception.BizException;
import com.nuwa.infrastructure.ticket.common.exception.NoLoginException;
import com.nuwa.infrastructure.ticket.enums.ErrorEnum;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authz.AuthorizationException;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Objects;

/**
 * @author hy
 */
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
        log.error("参数格式不合法,message={}", e.getMessage());
        return Response.buildFailure("901", "请输入正确内容");
    }

    /**
     * NoLoginException
     */
    @ExceptionHandler(NoLoginException.class)
    @ResponseBody
    public Response noLoginException(NoLoginException e) {
        ErrorEnum errCode = (ErrorEnum) e.getErrCode();
        log.error(errCode.getErrDesc(), e);
        return errCode.buildFailure();
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
    public Response illegalArgumentException(IllegalArgumentException exception) {
        log.error("[缺少必填参数]", exception);
        log.error("[缺少必填参数]：" + exception.getMessage());
        return Response.buildFailure("904", exception.getMessage() + "-缺少必填参数");
    }

    /**
     * NumberFormatException
     */
    @ExceptionHandler(NumberFormatException.class)
    @ResponseBody
    public Response numberFormatException(NumberFormatException exception) {
        log.error("[类型转换错误]", exception);
        log.error("[类型转换错误]：" + exception.getMessage());
        return Response.buildFailure("904", "请输入正确内容");
    }

    /**
     * NullPointerException
     */
    @ExceptionHandler(NullPointerException.class)
    @ResponseBody
    public Response nullPointerException(NullPointerException exception) {
        log.error("[空指针异常]", exception);
        log.error("[空指针异常]：" + exception.getMessage());
        return Response.buildFailure("904", exception.getMessage() + "-空指针异常");
    }

    /**
     * MissingServletRequestParameterException
     */
    @ExceptionHandler(MissingServletRequestParameterException.class)
    @ResponseBody
    public Response missingServletRequestParameterException(MissingServletRequestParameterException exception) {
        log.error("[参数格式不符]", exception);
        log.error("[参数格式不符]：" + exception.getMessage());
        return Response.buildFailure("904", exception.getMessage() + "-参数格式不符");
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
