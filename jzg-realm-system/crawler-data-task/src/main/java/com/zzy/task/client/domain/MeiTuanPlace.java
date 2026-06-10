package com.zzy.task.client.domain;

import com.zzy.core.constant.DataBaseConstant;
import com.zzy.task.common.constant.PlaceType;
import com.zzy.task.common.db.entity.PlaceInfo;

import java.util.Date;

public class MeiTuanPlace {

    private String id;  //1192856,
    private String title;  //"观海园",
    private String address;  //"海盐县武原镇秦山路观海园",
    private String lowestprice;  //0,
    private String avgprice;  //0,
    private String latitude;  //30.504607,
    private String longitude;  //120.95067,

    private String showType;  //"travel",
    private String avgscore;  //3.4,
    private Integer comments;  //0,评论数量
    private String backCateName;  //"景点",
    private String areaname;  //"海盐",
    private String cityId;  //185,
    private String city;  //"嘉兴",

    private String kw;

    public String getKw() {
        return kw;
    }

    public MeiTuanPlace setKw(String kw) {
        this.kw = kw;
        return this;
    }

    private PlaceType item;


    public PlaceType getItem() {
        return item;
    }

    public MeiTuanPlace setItem(PlaceType item) {
        this.item = item;
        return this;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getLowestprice() {
        return lowestprice;
    }

    public void setLowestprice(String lowestprice) {
        this.lowestprice = lowestprice;
    }

    public String getAvgprice() {
        return avgprice;
    }

    public void setAvgprice(String avgprice) {
        this.avgprice = avgprice;
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

    public String getShowType() {
        return showType;
    }

    public void setShowType(String showType) {
        this.showType = showType;
    }

    public String getAvgscore() {
        return avgscore;
    }

    public void setAvgscore(String avgscore) {
        this.avgscore = avgscore;
    }

    public Integer getComments() {
        return comments;
    }

    public void setComments(Integer comments) {
        this.comments = comments;
    }

    public String getBackCateName() {
        return backCateName;
    }

    public void setBackCateName(String backCateName) {
        this.backCateName = backCateName;
    }

    public String getAreaname() {
        return areaname;
    }

    public void setAreaname(String areaname) {
        this.areaname = areaname;
    }

    public String getCityId() {
        return cityId;
    }

    public void setCityId(String cityId) {
        this.cityId = cityId;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }


    public PlaceInfo toDataBasePlace(){
        PlaceInfo info = new PlaceInfo();
        info.setCommentNum(this.getComments());
        info.setLatitude(this.getLatitude());
        info.setLongitude(this.getLongitude());
        info.setPlaceAddress(this.getAddress());
        info.setPlaceName(this.getTitle());
        info.setPlaceScore(this.getAvgscore());
        info.setPlaceSource(DataBaseConstant.SOURCE_PY_MEITUAN.getCode());
        if(this.getItem().equals(PlaceType.MEITUAN_CATE_HOTEL)){
            info.setPlaceType(DataBaseConstant.PLACE_Type_HOTEL.getCode());
        }else if(this.getItem().equals(PlaceType.MEITUAN_CATE_RESTAURANT)){
            info.setPlaceType(DataBaseConstant.PLACE_Type_RESTAURANT.getCode());
        }else if(this.getItem().equals(PlaceType.MEITUAN_CATE_SCENIC)){
            info.setPlaceType(DataBaseConstant.PLACE_Type_SCENIC.getCode());
        }
        info.setSiteHref("placeId="+this.getId());
        info.setKeyWord(this.getKw());
        info.setUpdateTime(new Date());
        return info;
    }
}
