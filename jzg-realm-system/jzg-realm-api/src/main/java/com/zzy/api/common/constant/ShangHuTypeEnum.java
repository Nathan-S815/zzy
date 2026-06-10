package com.zzy.api.common.constant;

public enum ShangHuTypeEnum {

    entertainment("entertainment"),
    hotel("hotel"),
    restaurant("restaurant"),
    scenic("scenic"),
    shopping("shopping"),
    traffic("traffic"),
    travel("travel"),

    ;

    private String fields;

    ShangHuTypeEnum(String p){
        this.fields = p;
    }

    public String getFields() {
        return fields;
    }

}
