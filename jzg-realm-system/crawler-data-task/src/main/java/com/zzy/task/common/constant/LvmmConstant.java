package com.zzy.task.common.constant;

public enum LvmmConstant {

    HOTEL_UCODE_HAIYAN("U678"),
    HOTEL_UCODE_PUTUO("U77K330100"),
    HOTEL_UCODE_JZG("H9U276K310000"),
    getLvmmScenicList_SCENIC_COUNTY_HAIYAN("COUNTY200677"),
    getLvmmScenicList_SCENIC_COUNTY_PUTUO("K330100PRO96"),
    getLvmmScenicList_SCENIC_COUNTY_JZG("COUNTY10677250H9K330100"),
    getLvmmScenicList_KEYWORD_HAIYAN("海盐"),
    getLvmmScenicList_KEYWORD_PUTUO("普陀区"),
    getLvmmScenicList_KEYWORD_JZG("九寨沟县"),

    ;

    private String code;
    LvmmConstant(String str){
        this.code = str;
    }


    public String getCode(){
        return this.code;
    }



    public String toDbCode(){
        if(getLvmmScenicList_KEYWORD_HAIYAN.equals(this)
                || HOTEL_UCODE_HAIYAN.equals(this)){
            return "haiyan";
        }
        if(getLvmmScenicList_KEYWORD_PUTUO.equals(this)
                || getLvmmScenicList_SCENIC_COUNTY_PUTUO.equals(this)
        || HOTEL_UCODE_PUTUO.equals(this)){
            return "putuo";
        }
        if(getLvmmScenicList_KEYWORD_JZG.equals(this) || getLvmmScenicList_SCENIC_COUNTY_JZG.equals(this)
        || HOTEL_UCODE_JZG.equals(this)){
            return "jiuzhaigou";
        }
        return null;
    }

}
