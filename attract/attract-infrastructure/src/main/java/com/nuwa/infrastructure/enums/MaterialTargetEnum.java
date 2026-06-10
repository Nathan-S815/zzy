package com.nuwa.infrastructure.enums;

import lombok.Getter;

/**
 * 素材用途
 */
@Getter
public enum MaterialTargetEnum {

    /**
     * 其他
     */
    OTHER(1, "其他"),
    REWARD(2, "奖励政策"),
    TRAVEL(3,"申报文件"),
    ;

    private Integer code;

    private String name;

    MaterialTargetEnum(Integer code, String name) {
        this.code = code;
        this.name = name;
    }
}
