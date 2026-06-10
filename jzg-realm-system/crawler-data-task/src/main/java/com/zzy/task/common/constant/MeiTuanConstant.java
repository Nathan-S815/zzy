package com.zzy.task.common.constant;

public enum MeiTuanConstant {


    getPlaceListByMeiTuan_haiyan("海盐县"),
    getPlaceListByMeiTuan_putuo("普陀区"),
    getPlaceListByMeiTuan_jiuzhaigou("九寨沟县"),

    ;


    private String code;
    MeiTuanConstant(String str){
        this.code = str;
    }


    public String getCode(){
        return this.code;
    }

    public String toDbCode(){
        if(getPlaceListByMeiTuan_haiyan.equals(this)){
            return "haiyan";
        }else if(getPlaceListByMeiTuan_putuo.equals(this)){
            return "putuo";
        }else if(getPlaceListByMeiTuan_jiuzhaigou.equals(this)){
            return "jiuzhaigou";
        }
        return null;
    }

}
