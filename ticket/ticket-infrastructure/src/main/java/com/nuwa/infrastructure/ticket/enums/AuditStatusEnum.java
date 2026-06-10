package com.nuwa.infrastructure.ticket.enums;


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
     * 通用状态
     */
    DRAFT(-1, "草稿"),
    WAIT_AUDIT(0, "等待审核"),
    AUDIT_PASS(1, "审核通过"),
    AUDIT_REJECT(2, "审核拒绝");

    private final Integer code;
    private final String message;

    AuditStatusEnum(Integer code, String message) {
        this.code = code;
        this.message = message;
    }
}
