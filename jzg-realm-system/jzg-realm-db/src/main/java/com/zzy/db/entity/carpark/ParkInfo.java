package com.zzy.db.entity.carpark;


import lombok.Data;

@Data
public class ParkInfo {

    //停车场唯一编号
    private String parkKey;

    //停车场名称
    private String parkName;

    //停车场纬度
    private String parkLatitude;

    //停车场经度
    private String parkLongitude;

    //总车位数
    private String spaceTotal;

    //剩余车位数
    private String spaceRemai;

    //生成时间yyyy-MM-dd HH:mm:ss
    private String time;
}
