package com.nuwa.infrastructure.discovery.enums;


import lombok.Getter;

/**
 * 后台返回结果集枚举
 *
 * @author 小懒虫
 * @date 2018/8/14
 */
@Getter
public enum SwitchEnum {

    /**
     * 通用状态
     */
    ON(1, "正常"),
    OFF(0, "关闭");

    private final Integer code;
    private final String message;

    SwitchEnum(Integer code, String message) {
        this.code = code;
        this.message = message;
    }
}
