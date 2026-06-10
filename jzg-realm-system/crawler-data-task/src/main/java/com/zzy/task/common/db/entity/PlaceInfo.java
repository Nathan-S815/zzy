package com.zzy.task.common.db.entity;

import com.zzy.task.client.domain.MeiTuanPlace;
import com.zzy.task.client.domain.XieChengPlace;

import java.io.Serializable;
import java.util.Date;

public class PlaceInfo implements Serializable {
    /**
     * 
     */
    private Integer id;

    /**
     * 场所名称
     */
    private String placeName;

    /**
     * 场所地址
     */
    private String placeAddress;

    /**
     * 
     */
    private String siteHref;

    /**
     * 
     */
    private Integer commentNum;

    /**
     * 纬度
     */
    private String latitude;

    /**
     * 经度
     */
    private String longitude;

    /**
     * 
     */
    private String placeSource;

    /**
     * 
     */
    private String placeScore;

    /**
     * 
     */
    private String placeType;

    /**
     * 
     */
    private Date updateTime;

    /**
     * 
     */
    private String keyWord;

    /**
     * crawler_base..place_info
     */
    private static final long serialVersionUID = 1L;

    /**
     * 
     * @return id 
     */
    public Integer getId() {
        return id;
    }

    /**
     * 
     * @param id 
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * 场所名称
     * @return place_name 场所名称
     */
    public String getPlaceName() {
        return placeName;
    }

    /**
     * 场所名称
     * @param placeName 场所名称
     */
    public void setPlaceName(String placeName) {
        this.placeName = placeName;
    }

    /**
     * 场所地址
     * @return place_address 场所地址
     */
    public String getPlaceAddress() {
        return placeAddress;
    }

    /**
     * 场所地址
     * @param placeAddress 场所地址
     */
    public void setPlaceAddress(String placeAddress) {
        this.placeAddress = placeAddress;
    }

    /**
     * 
     * @return site_href 
     */
    public String getSiteHref() {
        return siteHref;
    }

    /**
     * 
     * @param siteHref 
     */
    public void setSiteHref(String siteHref) {
        this.siteHref = siteHref;
    }

    /**
     * 
     * @return comment_num 
     */
    public Integer getCommentNum() {
        return commentNum;
    }

    /**
     * 
     * @param commentNum 
     */
    public void setCommentNum(Integer commentNum) {
        this.commentNum = commentNum;
    }

    /**
     * 纬度
     * @return latitude 纬度
     */
    public String getLatitude() {
        return latitude;
    }

    /**
     * 纬度
     * @param latitude 纬度
     */
    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    /**
     * 经度
     * @return longitude 经度
     */
    public String getLongitude() {
        return longitude;
    }

    /**
     * 经度
     * @param longitude 经度
     */
    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    /**
     * 
     * @return place_source 
     */
    public String getPlaceSource() {
        return placeSource;
    }

    /**
     * 
     * @param placeSource 
     */
    public void setPlaceSource(String placeSource) {
        this.placeSource = placeSource;
    }

    /**
     * 
     * @return place_score 
     */
    public String getPlaceScore() {
        return placeScore;
    }

    /**
     * 
     * @param placeScore 
     */
    public void setPlaceScore(String placeScore) {
        this.placeScore = placeScore;
    }

    /**
     * 
     * @return place_type 
     */
    public String getPlaceType() {
        return placeType;
    }

    /**
     * 
     * @param placeType 
     */
    public void setPlaceType(String placeType) {
        this.placeType = placeType;
    }

    /**
     * 
     * @return update_time 
     */
    public Date getUpdateTime() {
        return updateTime;
    }

    /**
     * 
     * @param updateTime 
     */
    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    /**
     * 
     * @return key_word 
     */
    public String getKeyWord() {
        return keyWord;
    }

    /**
     * 
     * @param keyWord 
     */
    public void setKeyWord(String keyWord) {
        this.keyWord = keyWord;
    }



    public MeiTuanPlace toMeiTuanPlace(){
        MeiTuanPlace mt = new MeiTuanPlace();
        mt.setTitle(this.getPlaceName());
        mt.setId(this.getSiteHref().replace("placeId=", ""));
        mt.setKw(this.getKeyWord());
        mt.setComments(this.getCommentNum());
        return mt;
    }


    public XieChengPlace toXieChengPlace() {
        XieChengPlace xc = new XieChengPlace();
        xc.setKw("jiuzhaigou");
        xc.setTitle(this.getPlaceName());
        xc.setHref(this.getSiteHref());
        xc.setComments(this.getCommentNum());
        return xc;
    }
}