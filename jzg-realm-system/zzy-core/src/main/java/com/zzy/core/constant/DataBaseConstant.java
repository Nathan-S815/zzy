package com.zzy.core.constant;

public enum DataBaseConstant {

    SOURCE_PY_MEITUAN("MEITUAN"),
    SOURCE_PY_XIECHENG("XIECHENG"),
    SOURCE_PY_QUNAR("QUNAR"),
    SOURCE_PY_TUNIU("TUNIU"),
    SOURCE_PY_LVMAMA("LVMAMA"),
    SOURCE_PY_TONGCHENGLVYOU("TONGCHENGLVYOU"),
    PLACE_Type_SCENIC("SCENIC"),
    PLACE_Type_RESTAURANT("RESTAURANT"),
    PLACE_Type_HOTEL("HOTEL"),
    DB_AREA_CODE_HAIYAN("haiyan"),
    DB_AREA_CODE_PUTUO("putuo"),

    ;


    private String code;
    DataBaseConstant(String py){
        this.code = py;
    }

    public String getCode(){
        return this.code;
    }


    @Override
    public String toString() {
        return this.code;
    }
}
