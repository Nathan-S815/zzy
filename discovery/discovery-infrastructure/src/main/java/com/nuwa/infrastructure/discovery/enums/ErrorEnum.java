package com.nuwa.infrastructure.discovery.enums;

import com.alibaba.cola.dto.SingleResponse;
import com.alibaba.cola.exception.ErrorCodeI;

public enum ErrorEnum implements ErrorCodeI {

    DATA_SUCCESS("0000", "操作成功"),

    NOT_LOGIN_FAILED("501", "用户token已失效，请重新登录"),
    BaseElement_CodeIsEXIST("201", "权限编码已存在"),

    ADMIN_DOES_NOT_EXIST("603", "用户不存在"),
    USER_TYPE_ERROR("603", "账号类型错误"),
    PASSWORD_WRONG("604", "密码错误"),
    ADMIN_FORBID("605", "账户已被禁用"),
    MERCHANT_STATUS_ERROR("609", "商户状态异常,请联系客服"),
    APP_USING("610", "删除失败,app正在被使用"),
    APP_PARENT("610", "删除失败,app为父应用"),
    INFO_CHANGED("611", "信息已被改变，请刷新"),

    LOGIN_VALID_CODE_INVALID("607", "无效的验证码"),


    VALID_CODE_TO_BUSY("608", "验证码发送太频繁"),
    VALID_CODE_SEND_FAILED("609", "验证码发送失败"),
    CODE_CHECK_FAILED("609", "验证码验证失败"),
    MOBILE_IS_EXIST("610", "手机号已被注册"),
    MOBILE_OR_USERNAME_IS_EXIST("610", "手机号已被注册"),
    PASSWORD_SAME_ERROR("611", "新密码与旧密码相同"),


    NAME_REPEAT("650", "名称重复"),
    DOMAIN_REPEAT("650", "域名重复"),
    DOMAIN_ERROR("656", "域名错误"),

    DATA_FAIL("889", "操作失败"),
    SKIP_USER_FAILED("890", "用户已存在"),
    LOGIN_CODE_FAILED("891", "景区code不能为空"),
    LOGIN_URL_FAILED("892", "url地址不能为空"),
    PARAM_FAILED("893","未传入正确的参数"),
    FILE_UPLOAD_FAILED("894","文件上传失败"),
    USER_DOES_NOT_EXIST("895","用户不存在");
    ;

    private final String errCode;
    private final String errDesc;

    private ErrorEnum(String errCode, String errDesc) {
        this.errCode = errCode;
        this.errDesc = errDesc;
    }

    public SingleResponse buildFailure() {
        return SingleResponse.buildFailure(this.getErrCode(), this.getErrDesc());
    }

    public SingleResponse buildSuccess() {
        SingleResponse singleResponse = SingleResponse.buildSuccess();
        singleResponse.setErrCode(this.getErrCode());
        singleResponse.setErrMessage(this.getErrDesc());
        return singleResponse;
    }

    @SuppressWarnings("ALL")
    public SingleResponse buildSuccess(Object data) {
        SingleResponse singleResponse = SingleResponse.buildSuccess();
        singleResponse.setErrCode(this.getErrCode());
        singleResponse.setErrMessage(this.getErrDesc());
        singleResponse.setData(data);
        return singleResponse;
    }

    @Override
    public String getErrCode() {
        return errCode;
    }

    @Override
    public String getErrDesc() {
        return errDesc;
    }
}
