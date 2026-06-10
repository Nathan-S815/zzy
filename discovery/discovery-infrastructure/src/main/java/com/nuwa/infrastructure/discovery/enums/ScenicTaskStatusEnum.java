package com.nuwa.infrastructure.discovery.enums;


import lombok.Getter;

/**
 * 任务状态
 *
 * @author hy
 * @date 2021/10/21
 */
@Getter
public enum ScenicTaskStatusEnum {
    /**
     * 1:未开始 2:进行中 3:已结束 4:暂停
     */
    WAIT(1, "未开始"),
    DOING(2, "进行中"),
    END(3, "已结束"),
    STOP(4, "暂停");

    private final Integer code;
    private final String message;

    ScenicTaskStatusEnum(Integer code, String message) {
        this.code = code;
        this.message = message;
    }
}
