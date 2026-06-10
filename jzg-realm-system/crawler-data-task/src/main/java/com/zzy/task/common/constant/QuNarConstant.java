package com.zzy.task.common.constant;

public enum QuNarConstant {

    KEY_WORD_HAIYAN("海盐县"),
    KEY_WORD_PUTUO("舟山普陀"),
    KEY_WORD_JZG("九寨沟县"),
    getQuNarHotelList_CITY_URL_HOTEL_HAIYAN("haiyan"),
    getQuNarHotelList_CITY_URL_HOTEL_ZHOUSHAN("zhoushan"),
    getQuNarHotelList_CITY_URL_HOTEL_JZG("jiuzhaigou"),
    query_word_putuo("普陀区"),
    query_word_jzg("九寨沟县"),

    ;


    private String val;
    QuNarConstant(String kw){
        this.val = kw;
    }

    public String getVal(){
        return this.val;
    }

    public String toDBcode(){
        if(KEY_WORD_HAIYAN.equals(this) || getQuNarHotelList_CITY_URL_HOTEL_HAIYAN.equals(this)){
            return "haiyan";
        }
        if(KEY_WORD_PUTUO.equals(this)
                || getQuNarHotelList_CITY_URL_HOTEL_ZHOUSHAN.equals(this)
        ||query_word_putuo.equals(this)){
            return "putuo";
        }
        if(query_word_jzg.equals(this) || getQuNarHotelList_CITY_URL_HOTEL_JZG.equals(this)
        || KEY_WORD_JZG.equals(this)){
            return "jiuzhaigou";
        }
        return null;
    }


}
