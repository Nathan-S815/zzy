package com.nuwa.infrastructure.zeus.enums;


import lombok.Getter;

/**
 * 后台返回结果集枚举
 *
 * @author 小懒虫
 * @date 2018/8/14
 */
@Getter
public enum DeleteFlagEnum {

    /**
     * 通用状态
     */
    NORMAL(0, "正常"),
    DELETE(1, "删除")
    ;

    private Integer code;

    private String message;

    DeleteFlagEnum(Integer code, String message) {
        this.code = code;
        this.message = message;
    }
}
