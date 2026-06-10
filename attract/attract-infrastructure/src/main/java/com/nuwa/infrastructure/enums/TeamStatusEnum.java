package com.nuwa.infrastructure.enums;

import lombok.Getter;

/**
 * @author nanHuang @南皇
 * @version com.nuwa.infrastructure.enums:TeamStatusEnum.java,v1.0.0 2022-09-15 10:13:20 nanHuang Exp $
 */

@Getter
public enum TeamStatusEnum {
    /**
     * 团队审核状态
     */
    WAIT_AUDIT(0, "未提交"),
    MCH_AUDIT(1, "景区/酒店审核中"),
    MCH_AUDIT_REJECT(2, "景区/酒店审核拒绝"),
    WAIT_OFFICIAL(3, "文旅局审核中"),
    OFFICIAL_SUCCESS(4, "文旅局审核成功"),
    OFFICIAL_REJECT(5, "文旅局审核失败"),
    INDEPENDENT_TRAVEL(6, "自由行"),
    MCH_AUDIT_PASS(7, "景区/酒店审核成功"),
    UPLOAD_INVOICE(8, "上传单据");
    private final Integer code;
    private final String  message;

    TeamStatusEnum(Integer code, String message) {
        this.code = code;
        this.message = message;
    }
}
