package com.nuwa.infrastructure.discovery.enums;


import lombok.Getter;

/**
 * 审核状态
 *
 * @author hy
 * @date 2021/10/21
 */
@Getter
public enum AuditStatusEnum {
    /**
     * 审核状态 1：待审核 2：审核成功 3：审核失败
     */
    WAIT_AUDIT(1, "等待审核"),
    AUDIT_PASS(2, "审核通过"),
    AUDIT_Reject(3, "审核拒绝");

    private final Integer code;
    private final String message;

    AuditStatusEnum(Integer code, String message) {
        this.code = code;
        this.message = message;
    }
}
