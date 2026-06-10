package com.zzy.api.common.constant;

import cn.hutool.core.util.EnumUtil;

public enum MemberParaEnum {
    memberId("dm.id"),
    departId("dm.department_id"),
    departName("di.`name`"),
    memberName("dm.`name`"),
    title("dm.title"),
    gender("dm.gender"),
    phoneNumber("dm.phone_number"),
    birth("birth"),
    email("dm.email"),

    ;

    private String fields;

    MemberParaEnum(String p){
        this.fields = p;
    }

    public String getFields() {
        return fields;
    }



}
