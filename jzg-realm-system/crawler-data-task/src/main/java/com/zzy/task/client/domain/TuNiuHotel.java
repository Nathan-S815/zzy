package com.zzy.task.client.domain;

import com.zzy.core.constant.DataBaseConstant;
import com.zzy.task.common.db.entity.PlaceInfo;

import java.util.Date;

public class TuNiuHotel {

    private HotelInfo hotel;
    private TuNiuCommentSubInfo comment;

    private String kw;

    public String getKw() {
        return kw;
    }

    public TuNiuHotel setKw(String kw) {
        this.kw = kw;
        return this;
    }

    public HotelInfo getHotel() {
        return hotel;
    }

    public void setHotel(HotelInfo hotel) {
        this.hotel = hotel;
    }

    public TuNiuCommentSubInfo getComment() {
        return comment;
    }

    public void setComment(TuNiuCommentSubInfo comment) {
        this.comment = comment;
    }


    public PlaceInfo toDataBasePlace(){
        PlaceInfo info = new PlaceInfo();
        if(this.getComment().getCount()==null){
            info.setCommentNum(0);
        }else{
            info.setCommentNum(this.getComment().getCount());
        }
        info.setLatitude(this.getHotel().getLatitude());
        info.setLongitude(this.getHotel().getLongitude());
        info.setPlaceAddress(this.getHotel().getAddress());
        info.setPlaceName(this.getHotel().getChineseName());
        if(this.getComment().getScore()==null){
            info.setPlaceScore("-1");
        }else{
            info.setPlaceScore(this.getComment().getScore());
        }
        info.setPlaceSource(DataBaseConstant.SOURCE_PY_TUNIU.getCode());
        info.setPlaceType(DataBaseConstant.PLACE_Type_HOTEL.getCode());
        info.setSiteHref("-");
        info.setKeyWord(this.getKw());
        info.setUpdateTime(new Date());
        return info;
    }
}
