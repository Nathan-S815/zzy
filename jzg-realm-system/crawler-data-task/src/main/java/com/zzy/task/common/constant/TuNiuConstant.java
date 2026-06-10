package com.zzy.task.common.constant;

import com.zzy.task.client.domain.TuNiuAreaReq;

public enum TuNiuConstant {

    cityCode_jiaXing("3410"),
    cityCode_zhoushan("3427"),
    cityCode_jzg("2815"),
    pois_code_haiyan("40795"),
    pois_code_jzg("40002"),
    pois_poiType("2"),
    pois_poi_Name_haiyan("海盐县"),
    pois_longitude_haiyan("120.943542"),
    pois_latitude_haiyan("30.523761"),
    pois_code_putuo("40819"),
    pois_poi_Name_putuo("普陀区"),
    pois_longitude_putuo("122.371291"),
    pois_latitude_putuo("29.939779"),
    pois_poi_Name_jzg("九寨沟县"),
    pois_longitude_jzg("104.223387"),
    pois_latitude_jzg("33.264302"),

    getTuNiuScenicList_location_haiyan("海盐"),
    getTuNiuScenicList_location_putuo("普陀区"),
    getTuNiuScenicList_location_jzg("九寨沟县"),
    key_word_haiyan("haiyan"),
    key_word_putuo("putuo"),
    key_word_jzg("jiuzhaigou"),

    ;

    private String code;
    TuNiuConstant(String code){
        this.code = code;
    }




    public String getCode(){
        return this.code;
    }


    public String getDbCode(){
        if(pois_poi_Name_haiyan.equals(this)
                || getTuNiuScenicList_location_haiyan.equals(this)
        || pois_code_haiyan.equals(this)
        || key_word_haiyan.equals(this)){
            return "haiyan";
        }
        if(getTuNiuScenicList_location_putuo.equals(this)
        || key_word_putuo.equals(this)){
            return "putuo";
        }
        if(getTuNiuScenicList_location_jzg.equals(this) || key_word_jzg.equals(this)){
            return "jiuzhaigou";
        }
        return null;
    }

    public static TuNiuAreaReq getTuNiuAreaReq_HaiYan(){
        return new TuNiuAreaReq(Integer.parseInt(pois_code_haiyan.getCode()),
                Integer.parseInt(pois_poiType.getCode()),
                pois_poi_Name_haiyan.getCode(),
                pois_longitude_haiyan.getCode(),
                pois_latitude_haiyan.getCode());
    }

    public static TuNiuAreaReq getTuNiuAreaReq_PuoTuo(){
        return new TuNiuAreaReq(Integer.parseInt(pois_code_putuo.getCode()),
                Integer.parseInt(pois_poiType.getCode()),
                pois_poi_Name_putuo.getCode(),
                pois_longitude_putuo.getCode(),
                pois_latitude_putuo.getCode());
    }

    public static TuNiuAreaReq getTuNiuAreaReq_JZG(){
        return new TuNiuAreaReq(Integer.parseInt(pois_code_jzg.getCode()),
                Integer.parseInt(pois_poiType.getCode()),
                pois_poi_Name_jzg.getCode(),
                pois_longitude_jzg.getCode(),
                pois_latitude_jzg.getCode());
    }

}
