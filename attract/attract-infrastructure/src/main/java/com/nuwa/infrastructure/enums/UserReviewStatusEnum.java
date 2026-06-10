package com.nuwa.infrastructure.enums;

import lombok.Data;
import lombok.Getter;

/**
 * 用户审核状态枚举
 *
 * @author nanHuang @南皇
 * @version com.nuwa.infrastructure.enums:UserReviewStatusEnum.java,v1.0.0 2022-09-08 09:40:48 nanHuang Exp $
 */
@Getter
public enum UserReviewStatusEnum {
    /**
     * 通用状态
     */
    DRAFT(-1, "草稿"),
    WAIT_AUDIT(0, "等待审核"),
    AUDIT_PASS(1, "审核通过"),
    AUDIT_REJECT(2, "审核拒绝"),
    BAN(3, "禁用");

    private final Integer code;
    private final String  message;

    UserReviewStatusEnum(Integer code, String message) {
        this.code = code;
        this.message = message;
    }
}
