package com.zzy.task.client.domain;

public class TuNiuAreaReq {
    private Integer code; //",40795);
    private Integer poiType; //",2);
    private String poiName; //","海盐县");
    private String longitude; //",120.943542);
    private String latitude; //",30.523761);


    public TuNiuAreaReq() {
    }

    public TuNiuAreaReq(Integer code, Integer poiType, String poiName, String longitude, String latitude) {
        this.code = code;
        this.poiType = poiType;
        this.poiName = poiName;
        this.longitude = longitude;
        this.latitude = latitude;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public Integer getPoiType() {
        return poiType;
    }

    public void setPoiType(Integer poiType) {
        this.poiType = poiType;
    }

    public String getPoiName() {
        return poiName;
    }

    public void setPoiName(String poiName) {
        this.poiName = poiName;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }
}
