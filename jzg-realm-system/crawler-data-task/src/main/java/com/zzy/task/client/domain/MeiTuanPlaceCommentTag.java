package com.zzy.task.client.domain;

import cn.hutool.core.util.StrUtil;
import com.alibaba.fastjson.util.TypeUtils;
import com.zzy.core.constant.DataBaseConstant;
import com.zzy.core.utils.UserDataUtil;
import com.zzy.task.common.db.entity.HotelCommentInfo;
import com.zzy.task.common.db.entity.PlaceCommentContentKeyWord;
import com.zzy.task.common.db.entity.RestaurantCommentInfo;
import com.zzy.task.common.db.entity.ScenicCommentInfo;
import com.zzy.task.common.util.DomainUtil;

import java.util.Date;

public class MeiTuanPlaceCommentTag {

    private String placeTitle;
    private String tag;
    private int count;

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public String getPlaceTitle() {
        return placeTitle;
    }

    public MeiTuanPlaceCommentTag setPlaceTitle(String placeTitle) {
        this.placeTitle = placeTitle;
        return this;
    }


    public PlaceCommentContentKeyWord toPlaceCommentContentKeyWord(){
        PlaceCommentContentKeyWord k = new PlaceCommentContentKeyWord();
        k.setPlaceName(this.getPlaceTitle());
        if(StrUtil.isBlankOrUndefined(this.getTag())){
            this.setTag("-");
        }
        k.setTagContent(this.getTag());
        k.setTagCount(this.getCount());
        k.setTagSource("MEITUAN");
        k.setUpdateTime(new Date());
        return k;
    }




}
