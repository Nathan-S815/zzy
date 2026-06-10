package com.zzy.task.client.domain;

import com.zzy.core.constant.DataBaseConstant;
import com.zzy.core.utils.UserDataUtil;
import com.zzy.task.common.db.entity.HotelCommentInfo;
import com.zzy.task.common.db.entity.RestaurantCommentInfo;
import com.zzy.task.common.db.entity.ScenicCommentInfo;
import com.zzy.task.common.util.DomainUtil;

import java.util.Date;

public class XieChengComment {

    private String uid;  //"东问西问",
    private String content;  //"南北湖真是好时光，这里非常的美，集山海湖一体的国家4A旅游景区，休闲度假好去处，停车也方便，里面景点很多，吃饭就在湖边的农家乐解决的，价格还算公道。",
    private Date date;  //"2019-12-04 10:23",
    private String score;  //"5",
    private String placeTitle;
//    private String sightStar; //4
//    private String interestStar;  //4
//    private String costPerformanceStar;  //4


    public String getTitle() {
        return placeTitle;
    }

    public XieChengComment setTitle(String title) {
        this.placeTitle = title;
        return this;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getScore() {
        return score;
    }

    public void setScore(String score) {
        this.score = score;
    }


    public RestaurantCommentInfo toRestautantComment(){
        RestaurantCommentInfo info = new RestaurantCommentInfo();
        info.setCommentContent(DomainUtil.removeEmojiUtf8mb4(this.getContent()));
        info.setCommentPlaceName(this.getTitle());
        info.setCommentSource(DataBaseConstant.SOURCE_PY_XIECHENG.getCode());
        info.setCommentTime(this.getDate());
        info.setCommentUser(UserDataUtil.toAnonymousStr(this.getUid()));
        info.setCreateTime(new Date());
        if(this.getScore()==null){//没评分的一律按好评
            this.setScore("5");
        }
        double s = Double.valueOf(this.getScore());
        info.setCommentScore(s);
        if(s>=4.0){
            info.setCommentType(1);
        }else if(s>=3.0 && s < 4.0){
            info.setCommentType(2);
        }else if(s<3.0 && s>0){
            info.setCommentType(0);
        }else{
            info.setCommentType(-1);
        }
        return info;
    }

    public ScenicCommentInfo toScenicComment(){
        ScenicCommentInfo info = new ScenicCommentInfo();
        info.setCommentContent(DomainUtil.removeEmojiUtf8mb4(this.getContent()));
        info.setCommentPlaceName(this.getTitle());
        info.setCommentSource(DataBaseConstant.SOURCE_PY_XIECHENG.getCode());
        info.setCommentTime(this.getDate());
        info.setCommentUser(UserDataUtil.toAnonymousStr(this.getUid()));
        info.setCreateTime(new Date());
        if(this.getScore()==null){
            this.setScore("5");
        }
        double s = Double.valueOf(this.getScore());
        info.setCommentScore(s);
        if(s>=4.0){
            info.setCommentType(1);
        }else if(s>=3.0 && s < 4.0){
            info.setCommentType(2);
        }else if(s<3.0 && s>0){
            info.setCommentType(0);
        }else{
            info.setCommentType(-1);
        }
        return info;
    }


    public HotelCommentInfo toHotelComment(){
        HotelCommentInfo info = new HotelCommentInfo();
        info.setCommentContent(DomainUtil.removeEmojiUtf8mb4(this.getContent()));
        info.setCommentPlaceName(this.getTitle());
        info.setCommentSource(DataBaseConstant.SOURCE_PY_XIECHENG.getCode());
        info.setCommentTime(this.getDate());
        info.setCommentUser(UserDataUtil.toAnonymousStr(this.getUid()));
        info.setCreateTime(new Date());
        if(this.getScore()==null){
            this.setScore("4");
        }
        double s = Double.valueOf(this.getScore());
        info.setCommentScore(s);
        if(s>=4.0){
            info.setCommentType(1);
        }else if(s>=3.0 && s < 4.0){
            info.setCommentType(2);
        }else if(s<3.0 && s>=0){
            info.setCommentType(0);
        }
        return info;
    }


}
