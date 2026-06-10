package com.zzy.task.client.domain;

import com.zzy.core.constant.DataBaseConstant;
import com.zzy.task.common.db.entity.PlaceInfo;

import java.util.Date;

public class TongChengScenic {

    private String GradeId; //"4",
    private String Address; //"浙江省嘉兴市海盐县武原街道海滨东路37号",
    private String InnerTypeName; //"景点",
    private String ProductId; //"1979",
    private String Title; //"绮园景区",
    private String CityId; //385,
    private String CityName; //"嘉兴",
    private Integer CommentCount; //752,

    private String kw;

    public String getKw() {
        return kw;
    }

    public TongChengScenic setKw(String kw) {
        this.kw = kw;
        return this;
    }

    public String getGradeId() {
        return GradeId;
    }

    public void setGradeId(String gradeId) {
        GradeId = gradeId;
    }

    public String getAddress() {
        return Address;
    }

    public void setAddress(String address) {
        Address = address;
    }

    public String getInnerTypeName() {
        return InnerTypeName;
    }

    public void setInnerTypeName(String innerTypeName) {
        InnerTypeName = innerTypeName;
    }

    public String getProductId() {
        return ProductId;
    }

    public void setProductId(String productId) {
        ProductId = productId;
    }

    public String getTitle() {
        return Title;
    }

    public void setTitle(String title) {
        Title = title;
    }

    public String getCityId() {
        return CityId;
    }

    public void setCityId(String cityId) {
        CityId = cityId;
    }

    public String getCityName() {
        return CityName;
    }

    public void setCityName(String cityName) {
        CityName = cityName;
    }

    public Integer getCommentCount() {
        return CommentCount;
    }

    public void setCommentCount(Integer commentCount) {
        CommentCount = commentCount;
    }


    public PlaceInfo toDataBasePlace(){
        PlaceInfo info = new PlaceInfo();
        info.setCommentNum(this.getCommentCount());
        info.setLatitude("-");
        info.setLongitude("-");
        info.setPlaceAddress(this.getAddress());
        info.setPlaceName(this.getTitle());
        info.setPlaceScore("-1");
        info.setPlaceSource(DataBaseConstant.SOURCE_PY_TONGCHENGLVYOU.getCode());
        info.setPlaceType(DataBaseConstant.PLACE_Type_SCENIC.getCode());
        info.setSiteHref("-");
        info.setKeyWord(this.getKw());
        info.setUpdateTime(new Date());
        return info;
    }
}
