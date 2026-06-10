package com.zzy.task.client.domain;

public class HotelInfo {
    private String hotelId;  //": 223098517,
    private String chineseName;  //": "如家联盟·华驿酒店(海盐三毛乐园绮园大润发店)",
    private String latitude;  //": 30.529117,
    private String address;  //": "百尺北路169-2号",
    private String decorateDate;  //": "2016",
    private String longitude;  //": 120.936838


    public String getHotelId() {
        return hotelId;
    }

    public void setHotelId(String hotelId) {
        this.hotelId = hotelId;
    }

    public String getChineseName() {
        return chineseName;
    }

    public void setChineseName(String chineseName) {
        this.chineseName = chineseName;
    }


    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }


    public String getDecorateDate() {
        return decorateDate;
    }

    public void setDecorateDate(String decorateDate) {
        this.decorateDate = decorateDate;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }
}
