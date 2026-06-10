package com.zzy.task.common.constant;

public enum PlaceType {

    MEITUAN_CATE_SCENIC(296),
    MEITUAN_CATE_RESTAURANT(1),
    MEITUAN_CATE_HOTEL(20),
    XIECHENG_SIGHT_PATH("/sightlist"),
    XIECHENG_RESTAURANT_PATH("/restaurantlist"),
    XIECHENG_SIGHT_PATh_2("/sight"),


    ;

    private int placeId;
    private String path;
    PlaceType(int id){
        this.placeId = id;
    }
    PlaceType(String path){
        this.path = path;
    }

    public int getId(){
        return this.placeId;
    }
    public String getPath(){
        return this.path;
    }


    public String getPlaceTypeDbStr(){
        if(MEITUAN_CATE_HOTEL.equals(this)){
            return "HOTEL";
        }
        if(MEITUAN_CATE_RESTAURANT.equals(this)){
            return "RESTAURANT";
        }
        if(MEITUAN_CATE_SCENIC.equals(this)){
            return "SCENIC";
        }
        return null;
    }


}
