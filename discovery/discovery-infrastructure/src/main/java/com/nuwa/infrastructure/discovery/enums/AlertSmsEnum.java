package com.nuwa.infrastructure.discovery.enums;


import lombok.Getter;

import java.util.Arrays;
import java.util.Optional;

/**
 * 消息通知枚举
 *
 * @author hy
 * @date 2021/10/21
 */
@Getter
public enum AlertSmsEnum {
    /**
     * 审核状态 task_apply_success：任务报名成功 new_task：任务上新 member_apply_prize：达人权益认领申请  new_task：任务上新
     */
    TASK_APPLY_SUCCESS("task_apply_success", "任务报名成功通知", "dd", "任务id:${taskId};任务标题:${taskName};任务联系人:${linkName};任务联系人电话:${linkMobile};任务数量:${taskCnt};达人昵称:${userNick};达人手机号码:${userMobile}"),
    MEMBER_PRIZE_APPLY("member_apply_prize", "达人权益认领申请通知", "dd", "任务id:${taskId};任务标题:${taskName};申领权益类型:${prizeTypeName};申领权益标题:${prizeName};达人昵称:${userNick};达人手机号码:${userMobile}"),
    PRIZE_APPLY_PROCESS("prize_apply_success", "达人权益状态变更通知", "sms", "任务id:${taskId};任务标题:${taskName};申领权益类型:${prizeTypeName};申领权益标题:${prizeName};申领时间:${applyTime};申领状态:${applyStatus}"),
    NEW_TASK("new_task", "任务上新通知", "sms", "任务id:${taskId};任务标题:${taskName};任务联系人:${linkName};任务联系人电话:${linkMobile};任务数量:${taskCnt}");

    private final String bizCode;
    private final String message;
    private final String sendType;
    private final String paramName;

    AlertSmsEnum(String bizCode, String message, String sendType, String paramName) {
        this.bizCode = bizCode;
        this.message = message;
        this.sendType = sendType;
        this.paramName = paramName;
    }

    public static AlertSmsEnum getByBizCode(String bizCode) {
        Optional<AlertSmsEnum> enumOptional = Arrays.stream(AlertSmsEnum.values()).filter(x -> x.getBizCode().equals(bizCode)).findFirst();
        return enumOptional.orElse(null);
    }
}
