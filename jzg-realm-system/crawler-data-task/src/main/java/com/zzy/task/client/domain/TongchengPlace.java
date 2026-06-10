package com.zzy.task.client.domain;

import com.zzy.task.common.constant.PlaceTypeEnum;

public class TongchengPlace {

    private String title;
    private String href;
    private String address;
    private String score;
    private Integer comments;
    private PlaceTypeEnum type;


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

    public PlaceTypeEnum getType() {
        return type;
    }

    public void setType(PlaceTypeEnum type) {
        this.type = type;
    }
}
