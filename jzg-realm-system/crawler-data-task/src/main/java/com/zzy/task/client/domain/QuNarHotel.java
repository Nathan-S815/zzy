package com.zzy.task.client.domain;

import com.zzy.core.constant.DataBaseConstant;
import com.zzy.task.common.db.entity.PlaceInfo;

import java.util.Date;

public class QuNarHotel extends XieChengPlace {


    @Override
    public PlaceInfo toDataBasePlace() {
        PlaceInfo info = new PlaceInfo();
        info.setCommentNum(-1);
        info.setLatitude("-");
        info.setLongitude("-");
        info.setPlaceAddress(this.getAddress());
        info.setPlaceName(this.getTitle());
        info.setPlaceScore("-1");
        info.setPlaceSource(DataBaseConstant.SOURCE_PY_QUNAR.getCode());
        info.setPlaceType(DataBaseConstant.PLACE_Type_HOTEL.getCode());
        info.setSiteHref(this.getHref());
        info.setKeyWord(this.getKw());
        info.setUpdateTime(new Date());
        return info;
    }
}
