package com.zzy.task.client.domain;

import com.zzy.core.constant.DataBaseConstant;
import com.zzy.task.common.db.entity.PlaceInfo;

import java.util.Date;

public class TuNiuScenic extends XieChengPlace{


    @Override
    public PlaceInfo toDataBasePlace() {
        PlaceInfo info = new PlaceInfo();
        info.setCommentNum(this.getComments());
        info.setLatitude("-");
        info.setLongitude("-");
        info.setPlaceAddress(this.getAddress());
        info.setPlaceName(this.getTitle());
        info.setPlaceScore(this.getScore());
        info.setPlaceSource(DataBaseConstant.SOURCE_PY_TUNIU.getCode());
        info.setPlaceType(DataBaseConstant.PLACE_Type_SCENIC.getCode());
        info.setSiteHref(this.getHref());
        info.setKeyWord(this.getKw());
        info.setUpdateTime(new Date());
        return info;
    }
}
