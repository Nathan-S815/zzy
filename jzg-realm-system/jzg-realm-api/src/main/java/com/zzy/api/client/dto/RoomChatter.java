package com.zzy.api.client.dto;

import lombok.Data;

@Data
public class RoomChatter {

    private String memberName;
    private String loginName;
    private Integer userId;
    private Boolean isLive;
    private String headIcon;

    public RoomChatter(){}


    public RoomChatter(String memberName, String loginName, Integer userId, String headIcon,boolean isLive) {
        this.memberName = memberName;
        this.loginName = loginName;
        this.userId = userId;
        this.headIcon = headIcon;
        this.isLive = isLive;
    }

    public static RoomChatter build(String memberName, String loginName, Integer userId, String headIcon,boolean isLive){
        return new RoomChatter(memberName,loginName,userId,headIcon,isLive);
    }

}
