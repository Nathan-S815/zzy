package com.zzy.task.client.domain;

import cn.hutool.core.util.StrUtil;
import com.zzy.core.constant.DataBaseConstant;
import com.zzy.task.common.db.entity.PlaceInfo;

import java.util.Date;

public class LvmmHotel {

    private String Name; //"蝶来海盐宾馆",
    private String Address; //
    private String score; //
    private String latitude;
    private String longitude;
    private String href;

    private String kw;

    public String getKw() {
        return kw;
    }

    public void setKw(String kw) {
        this.kw = kw;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getAddress() {
        return Address;
    }

    public void setAddress(String address) {
        Address = address;
    }

    public String getScore() {
        return score;
    }

    public void setScore(String score) {
        this.score = score;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public String getHref() {
        return href;
    }

    public void setHref(String href) {
        this.href = href;
    }

    public PlaceInfo toDataBasePlace(){
        PlaceInfo info = new PlaceInfo();
        info.setCommentNum(-1);
        if(StrUtil.isBlankOrUndefined(this.getLatitude())){
            info.setLatitude("-");
        }else{
            info.setLatitude(this.getLatitude());
        }
        if(StrUtil.isBlankOrUndefined(this.getLongitude())){
            info.setLongitude("-");
        }else{
            info.setLongitude(this.getLongitude());
        }
        info.setPlaceAddress(this.getAddress());
        info.setPlaceName(this.getName());
        if(StrUtil.isBlankOrUndefined(this.getScore())){
            info.setPlaceScore("-1");
        }else{
            info.setPlaceScore(this.getScore());
        }
        info.setPlaceSource(DataBaseConstant.SOURCE_PY_LVMAMA.getCode());
        info.setPlaceType(DataBaseConstant.PLACE_Type_HOTEL.getCode());
        info.setSiteHref(this.getHref());
        info.setKeyWord(this.getKw());
        info.setUpdateTime(new Date());
        return info;
    }



}
