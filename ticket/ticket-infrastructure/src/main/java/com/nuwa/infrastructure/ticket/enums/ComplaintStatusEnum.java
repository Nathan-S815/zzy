package com.nuwa.infrastructure.ticket.enums;

import lombok.Getter;

/**
 * AuditStatusEnum 审核状态 [100审核中,101审核成功,102审核失败]
 *
 * @author hy
 * @date 2020/11/3 13:10
 * @since 1.0.0
 */
@Getter
public enum ComplaintStatusEnum {
    PROCESSED(102, "已处理"),
    UNTREATED(101, "未处理");

    private Integer code;

    private String name;

    ComplaintStatusEnum(Integer code, String name) {
        this.code = code;
        this.name = name;
    }
}
