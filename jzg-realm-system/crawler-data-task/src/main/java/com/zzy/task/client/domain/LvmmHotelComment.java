package com.zzy.task.client.domain;

import cn.hutool.core.util.StrUtil;
import com.alibaba.fastjson.util.TypeUtils;
import com.zzy.core.constant.DataBaseConstant;
import com.zzy.core.utils.UserDataUtil;
import com.zzy.task.common.db.entity.HotelCommentInfo;
import com.zzy.task.common.util.DomainUtil;

import java.util.Date;

public class LvmmHotelComment {

    private String titleName;
    private String userName;
    private String commentTime;
    private String content;
    private String score;
    private Integer commentNums;
    private String placeKw;


    public String getPlaceKw() {
        return placeKw;
    }

    public LvmmHotelComment setPlaceKw(String placeKw) {
        this.placeKw = placeKw;
        return this;
    }

    public Integer getCommentNums() {
        return commentNums;
    }

    public void setCommentNums(Integer commentNums) {
        this.commentNums = commentNums;
    }

    public String getTitleName() {
        return titleName;
    }

    public void setTitleName(String titleName) {
        this.titleName = titleName;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getCommentTime() {
        return commentTime;
    }

    public void setCommentTime(String commentTime) {
        this.commentTime = commentTime;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getScore() {
        return score;
    }

    public void setScore(String score) {
        this.score = score;
    }

    public HotelCommentInfo toHotelComment(){
        HotelCommentInfo info = new HotelCommentInfo();
        info.setCommentContent(DomainUtil.removeEmojiUtf8mb4(this.getContent()));
        info.setCommentPlaceName(this.getTitleName());
        info.setCommentSource(DataBaseConstant.SOURCE_PY_LVMAMA.getCode());
        info.setCommentTime(TypeUtils.castToDate(this.getCommentTime()));
        info.setCommentUser(UserDataUtil.toAnonymousStr(this.getUserName()));
        info.setCreateTime(new Date());
        if(StrUtil.isBlank(this.getScore())){
            this.setScore("5");
        }
        double s = Double.parseDouble(this.getScore());
        info.setCommentScore(s);
        if(s>=4.0){
            info.setCommentType(1);
        }else if(s>=3.0 && s < 4.0){
            info.setCommentType(2);
        }else if(s<3.0 && s>=0){
            info.setCommentType(0);
        }
        info.setPlaceKeyWord(this.getPlaceKw());
        return info;
    }
}
