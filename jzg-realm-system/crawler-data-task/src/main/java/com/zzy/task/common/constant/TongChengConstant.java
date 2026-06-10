package com.zzy.task.common.constant;

public enum TongChengConstant {

    start_city_id_haiyan("385"), //嘉兴city
    start_city_id_zhoushan("392"),
    start_city_id_jzg("322"),


    section_id_haiyan("564"), //海盐
    section_id_putuo("3715"), //舟山普陀区
    section_id_jzg("0"), //九寨沟

    getTongChengSecnicList_key_word_haiyan("海盐"),
    getTongChengSecnicList_key_word_putuo("普陀区"),
    getTongChengSecnicList_key_word_jzg("九寨沟县"),



    ;

    private String id;
    TongChengConstant(String id){
        this.id = id;
    }

    public String getV(){
        return this.id;
    }
    public String toDbCode(){
        if(getTongChengSecnicList_key_word_haiyan.equals(this) || section_id_haiyan.equals(this)){
            return "haiyan";
        }
        if(getTongChengSecnicList_key_word_putuo.equals(this) || section_id_putuo.equals(this)){
            return "putuo";
        }
        if(getTongChengSecnicList_key_word_jzg.equals(this) || section_id_jzg.equals(this)){
            return "jiuzhaigou";
        }
        return null;
    }
}
