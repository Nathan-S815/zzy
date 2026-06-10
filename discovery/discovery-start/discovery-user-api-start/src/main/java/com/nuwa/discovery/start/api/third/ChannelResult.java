package com.nuwa.discovery.start.api.third;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import java.io.Serializable;

@Data
public class ChannelResult<T> implements Serializable {

    private String code;

    private String msg;

    private T data;

    @JsonIgnore
    private String requestStr;

    @JsonIgnore
    private String responseStr;

    @JsonIgnore
    private Long costTime;

    @JsonIgnore
    private String url;

    @JsonIgnore
    private String method;

    @JsonIgnore
    public Boolean isSuccessful() {
        return ChannelResultCodeEnum.SUCCESS.getCode().equalsIgnoreCase(this.getCode());
    }

    /**
     * 请求成功
     *
     * @param t T
     * @return ChannelResult<T>
     */
    public ChannelResult<T> success(T t, String requestStr, String responseStr) {
        this.setData(t);
        this.setCode(ChannelResultCodeEnum.SUCCESS.getCode());
        this.setMsg(ChannelResultCodeEnum.SUCCESS.getMessage());
        this.setRequestStr(requestStr);
        this.setResponseStr(responseStr);
        return this;
    }

    /**
     * 支付渠道异常
     *
     * @param code    错误码
     * @param message 错误参数
     * @return ChannelResult
     */
    public ChannelResult<T> channelException(String code, String message, String requestStr, String responseStr, Long costTime) {
        this.setCode(code);
        this.setMsg(message);
        this.setRequestStr(requestStr);
        this.setResponseStr(responseStr);
        this.setCostTime(costTime);
        return this;
    }


    /**
     * 请求超时报文异常
     *
     * @return ChannelResult
     */
    public ChannelResult<T> timeoutException(String requestStr, String responseStr) {
        this.setCode(ChannelResultCodeEnum.TIMEOUT_ERROR.getCode());
        this.setMsg(ChannelResultCodeEnum.TIMEOUT_ERROR.getMessage());
        this.setRequestStr(requestStr);
        this.setResponseStr(responseStr);
        return this;
    }


    /**
     * 解析响应报文异常
     *
     * @return ChannelResult
     */
    public ChannelResult<T> parseRespException(String requestStr, String responseStr) {
        this.setCode(ChannelResultCodeEnum.PARSE_RESPONSE_ERROR.getCode());
        this.setMsg(ChannelResultCodeEnum.PARSE_RESPONSE_ERROR.getMessage());
        this.setRequestStr(requestStr);
        this.setResponseStr(responseStr);
        return this;
    }

    /**
     * 获取请求参数异常
     *
     * @return ChannelResult
     */
    public ChannelResult<T> buildParamsException() {
        this.setCode(ChannelResultCodeEnum.BUILD_REQUEST_PARAMS_ERROR.getCode());
        this.setMsg(ChannelResultCodeEnum.BUILD_REQUEST_PARAMS_ERROR.getMessage());
        return this;
    }

    /**
     * 系统异常
     *
     * @return ChannelResult
     */
    public ChannelResult<T> systemException(String errMsg) {
        this.setCode(ChannelResultCodeEnum.UNKNOWN_ERROR.getCode());
        this.setMsg(errMsg);
        return this;
    }

    @Override
    public String toString() {
        return "ChannelResult{" +
                "code='" + code + '\'' +
                ", msg='" + msg + '\'' +
                ", data=" + data +
                '}';
    }
}
