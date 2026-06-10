package com.nuwa.infrastructure.ticket.enums;


import cn.hutool.core.map.MapUtil;
import cn.hutool.core.util.EnumUtil;
import cn.hutool.core.util.ReflectUtil;
import lombok.Getter;

import java.util.Map;

/**
 * 适合人群
 *
 * @author hy
 * @date 2021/10/21
 */
@Getter
public enum SuitedPeopleEnum {
    /**
     * 适合人群；0->所有人 1->成年人 2->儿童 3->学生 4->本地市民 5->教师  6->记者 7->导游 8->医护人员 9->残疾人 10->外籍人士 11->现役军人  12->离休干部 13->其他
     */
    ALL(0, "所有人"),
    TYPE_1(1, "成年人"),
    TYPE_2(2, "儿童"),
    TYPE_3(3, "学生"),
    TYPE_4(4, "本地市民"),
    TYPE_5(5, "教师"),
    TYPE_6(6, "记者"),
    TYPE_7(7, "导游"),
    TYPE_8(8, "医护人员"),
    TYPE_9(9, "残疾人"),
    TYPE_10(10, "外籍人士"),
    TYPE_11(11, "现役军人"),
    TYPE_12(12, "离休干部"),
    TYPE_13(13, "其他"),
    ;

    private final Integer code;
    private final String message;

    SuitedPeopleEnum(Integer code, String message) {
        this.code = code;
        this.message = message;
    }

    public static Map<Integer, String> toMap() {
        final Map<Integer, String> map = MapUtil.newHashMap(SuitedPeopleEnum.values().length);
        for (SuitedPeopleEnum enumEle : SuitedPeopleEnum.values()) {
            map.put(enumEle.getCode(), enumEle.getMessage());
        }
        return map;
    }
}
