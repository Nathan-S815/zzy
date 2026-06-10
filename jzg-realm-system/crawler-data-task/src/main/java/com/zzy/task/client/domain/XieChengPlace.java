package com.zzy.task.client.domain;

import com.zzy.core.constant.DataBaseConstant;
import com.zzy.task.common.constant.PlaceType;
import com.zzy.task.common.db.entity.PlaceInfo;

import java.util.Date;

public class XieChengPlace {

    private String title;
    private String href;
    private String address;
    private String score;
    private Integer comments;

    private String kw;

    public XieChengPlace(){}

    public XieChengPlace(String title, String href) {
        this.title = title;
        this.href = href;
    }

    public String getKw() {
        return kw;
    }

    public void setKw(String kw) {
        this.kw = kw;
    }

    private PlaceType item;


    public PlaceType getItem() {
        return item;
    }

    public XieChengPlace setItem(PlaceType item) {
        this.item = item;
        return this;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getHref() {
        return href;
    }

    public void setHref(String href) {
        this.href = href;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getScore() {
        return score;
    }

    public void setScore(String score) {
        this.score = score;
    }

    public Integer getComments() {
        return comments;
    }

    public void setComments(Integer comments) {
        this.comments = comments;
    }


    public PlaceInfo toDataBasePlace(){
        PlaceInfo info = new PlaceInfo();
        info.setCommentNum(this.getComments());
        info.setLatitude("-");
        info.setLongitude("-");
        info.setPlaceAddress(this.getAddress());
        info.setPlaceName(this.getTitle());
        info.setPlaceScore(this.getScore());
        info.setPlaceSource(DataBaseConstant.SOURCE_PY_XIECHENG.getCode());
        if(this.getItem().equals(PlaceType.XIECHENG_RESTAURANT_PATH)){
            info.setPlaceType(DataBaseConstant.PLACE_Type_RESTAURANT.getCode());
        }else if(this.getItem().equals(PlaceType.XIECHENG_SIGHT_PATH)){
            info.setPlaceType(DataBaseConstant.PLACE_Type_SCENIC.getCode());
        }else if(this.getItem().equals(PlaceType.XIECHENG_SIGHT_PATh_2)){
            info.setPlaceType(DataBaseConstant.PLACE_Type_SCENIC.getCode());
        }
        info.setSiteHref(this.getHref());
        info.setKeyWord(this.getKw());
        info.setUpdateTime(new Date());
        return info;
    }
}
