package com.nuwa.infrastructure.zeus.enums;

import lombok.Getter;

/**
 * 素材文件类型  1:文章 2:图片 3:视频
 */
@Getter
public enum MaterialFileTypeEnum {

    /**
     * HTML(文章)
     */
    HTML(1, "HTML"),
    IMAGE(2, "IMAGE"),
    VIDEO(3, "VIDEO"),
    KEY(4, "KEY"),
    ;

    private Integer code;

    private String name;

    MaterialFileTypeEnum(Integer code, String name) {
        this.code = code;
        this.name = name;
    }
}
