package com.nuwa.infrastructure.zeus.enums;


import lombok.Getter;

/**
 * 后台返回结果集枚举
 *
 * @author 小懒虫
 * @date 2018/8/14
 */
@Getter
public enum AuditStatusEnum {

    /**
     * 通用状态
     */
    WAIT(0, "待审核"),
    PASS(1, "审核通过"),
    NOT_PASS(2, "审核失败")
    ;

    private Integer code;

    private String message;

    AuditStatusEnum(Integer code, String message) {
        this.code = code;
        this.message = message;
    }
}
