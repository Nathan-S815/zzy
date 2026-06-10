package com.zzy.task.client.domain;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.StrUtil;
import com.alibaba.fastjson.util.TypeUtils;
import com.zzy.core.constant.DataBaseConstant;
import com.zzy.core.utils.UserDataUtil;
import com.zzy.task.common.db.entity.HotelCommentInfo;
import com.zzy.task.common.util.DomainUtil;

import java.util.Date;

public class TongChengHotelComment {

    private String dpUserName; //"会员6942",
    private String dpDate; //"2019-06-09",
    private String lineAccess; //"好评",
    private String dpContent; //"酒店距离绮园、海滨公园和步行街很近，步行5-20分钟均可到达。早餐简单齐全，热牛奶、瓶装果汁等都有，可选种类可以吃的舒服有选择。地面有停车位，房间设施设备也齐全方便。入住时发现马桶没有清洁干净，因急着外出，晚上给服务员一说，立马又清理消毒了一遍。不过还是希望应杜绝此类问题的发生，毕竟清洁安全是第一位的。",
    private String score; //""
    private String placeTitle;
    private String placeKw;

    public String getPlaceKw() {
        return placeKw;
    }

    public TongChengHotelComment setPlaceKw(String placeKw) {
        this.placeKw = placeKw;
        return this;
    }

    public String getDpUserName() {
        return dpUserName;
    }

    public void setDpUserName(String dpUserName) {
        this.dpUserName = dpUserName;
    }

    public String getDpDate() {
        return dpDate;
    }

    public void setDpDate(String dpDate) {
        this.dpDate = dpDate;
    }

    public String getLineAccess() {
        return lineAccess;
    }

    public void setLineAccess(String lineAccess) {
        this.lineAccess = lineAccess;
    }

    public String getDpContent() {
        return dpContent;
    }

    public void setDpContent(String dpContent) {
        this.dpContent = dpContent;
    }

    public String getScore() {
        return score;
    }

    public void setScore(String score) {
        this.score = score;
    }

    public String getPlaceTitle() {
        return placeTitle;
    }

    public void setPlaceTitle(String placeTitle) {
        this.placeTitle = placeTitle;
    }



    public HotelCommentInfo toHotelComment(){
        HotelCommentInfo info = new HotelCommentInfo();
        info.setCommentContent(DomainUtil.removeEmojiUtf8mb4(this.getDpContent()));
        info.setCommentPlaceName(this.getPlaceTitle());
        info.setCommentSource(DataBaseConstant.SOURCE_PY_TONGCHENGLVYOU.getCode());
        if("昨天".equals(this.getDpDate())){
            info.setCommentTime(DateUtil.offsetDay(new Date(),-1));
        } else if("前天".equals(this.getDpDate())){
            info.setCommentTime(DateUtil.offsetDay(new Date(),-2));
        }else if("今天".equals(this.getDpDate())){
            info.setCommentTime(new Date());
        }
        else {
            info.setCommentTime(TypeUtils.castToDate(this.getDpDate()));
        }
        info.setCommentUser(UserDataUtil.toAnonymousStr(this.getDpUserName()));
        info.setCreateTime(new Date());
        double s = 5.0;
        if(StrUtil.isBlank(this.getLineAccess())){
            info.setCommentType(1);
            info.setCommentScore(s);
        }else{
            if(this.getLineAccess().contains("中")){
                s = 3.0;
                info.setCommentType(2);
            }else if(this.getLineAccess().contains("好")){
                s = 5.0;
                info.setCommentType(1);
            }else if(this.getLineAccess().contains("差")){
                s = 1.0;
                info.setCommentType(0);
            }else {
                s = 5.0;
                info.setCommentType(1);
            }
            info.setCommentScore(s);
        }
        info.setPlaceKeyWord(this.getPlaceKw());
        return info;
    }

}
