package com.nuwa.infrastructure.discovery.enums;

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
    ;

    private Integer code;

    private String name;

    MaterialTargetEnum(Integer code, String name) {
        this.code = code;
        this.name = name;
    }
}
