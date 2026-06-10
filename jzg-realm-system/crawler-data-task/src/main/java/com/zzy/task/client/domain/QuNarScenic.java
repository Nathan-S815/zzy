package com.zzy.task.client.domain;

import cn.hutool.core.util.StrUtil;
import com.zzy.core.constant.DataBaseConstant;
import com.zzy.task.common.db.entity.PlaceInfo;

import java.util.Date;
import java.util.Objects;

public class QuNarScenic {

    private String score; // "0.0",
    private String sightId; // 1085799396,
    private String point; // "120.87138496234,30.383041568146",
    private String address; // "浙江省嘉兴市海盐县澉浦长青路18号南北湖内",
    private String sightName; // "白云阁",
    private String parentSightId; // 1168,

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        QuNarScenic that = (QuNarScenic) o;
        return sightId.equals(that.sightId) &&
                point.equals(that.point) &&
                sightName.equals(that.sightName) &&
                parentSightId.equals(that.parentSightId) &&
                parentSightName.equals(that.parentSightName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(sightId, point, sightName, parentSightId, parentSightName);
    }

    private String parentSightName; // "南北湖",
    private String recommend; // false,
    private String recommendLevel; // "0.0",
    private String kw;

    public String getKw() {
        return kw;
    }

    public QuNarScenic setKw(String kw) {
        this.kw = kw;
        return this;
    }

    public String getScore() {
        return score;
    }

    public void setScore(String score) {
        this.score = score;
    }

    public String getSightId() {
        return sightId;
    }

    public void setSightId(String sightId) {
        this.sightId = sightId;
    }

    public String getPoint() {
        return point;
    }

    public void setPoint(String point) {
        this.point = point;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getSightName() {
        return sightName;
    }

    public void setSightName(String sightName) {
        this.sightName = sightName;
    }

    public String getParentSightId() {
        return parentSightId;
    }

    public void setParentSightId(String parentSightId) {
        this.parentSightId = parentSightId;
    }

    public String getParentSightName() {
        return parentSightName;
    }

    public void setParentSightName(String parentSightName) {
        this.parentSightName = parentSightName;
    }

    public String getRecommend() {
        return recommend;
    }

    public void setRecommend(String recommend) {
        this.recommend = recommend;
    }

    public String getRecommendLevel() {
        return recommendLevel;
    }

    public void setRecommendLevel(String recommendLevel) {
        this.recommendLevel = recommendLevel;
    }


    public PlaceInfo toDataBasePlace(){
        PlaceInfo info = new PlaceInfo();
        info.setCommentNum(-1);
        if(!StrUtil.isBlankOrUndefined(this.getPoint())){
            info.setLatitude(this.getPoint().split(",")[1]);
            info.setLongitude(this.getPoint().split(",")[0]);
        }else{
            info.setLatitude("-");
            info.setLongitude("-");
        }
        info.setPlaceAddress(this.getAddress());
        info.setPlaceName(this.getSightName());
        info.setPlaceScore(this.getScore());
        info.setPlaceSource(DataBaseConstant.SOURCE_PY_QUNAR.getCode());
        info.setPlaceType(DataBaseConstant.PLACE_Type_SCENIC.getCode());
        info.setSiteHref("-");
        info.setKeyWord(this.getKw());
        info.setUpdateTime(new Date());
        return info;
    }






}
