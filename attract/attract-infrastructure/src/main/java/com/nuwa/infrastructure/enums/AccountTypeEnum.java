package com.nuwa.infrastructure.enums;

import io.swagger.models.auth.In;
import lombok.Getter;

/**
 * @Author: WangXh
 * @DateTime: 2022/10/28
 * @Description: TODO
 */
@Getter
public enum AccountTypeEnum {
    ADMIN(0, "文理局"),
    SCENERY(1, " 景区"),
    HOTEL(2, "酒店"),
    TRAVEL(3, "旅行社"),
    INDEPENDENT_TRAVEL(6, "自由行");

    private Integer code;

    private String name;

    AccountTypeEnum(Integer code, String name) {
        this.code = code;
        this.name = name;
    }
}
