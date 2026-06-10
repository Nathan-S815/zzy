package com.zzy.task.client.domain;

import cn.hutool.core.util.StrUtil;
import com.alibaba.fastjson.util.TypeUtils;
import com.zzy.core.constant.DataBaseConstant;
import com.zzy.core.utils.UserDataUtil;
import com.zzy.task.common.db.entity.ScenicCommentInfo;
import com.zzy.task.common.util.DomainUtil;

import java.util.Date;

public class TongChengScenicComment {


    private String dpContent; //"好几个景点都是关门的，奕仙城全是饭店，湖边感觉好，车还不让进，这个服务和质量有点贵。大家看图。",
    private String dpDate; //"2019-01-06",
    private String DPItemId; //"1964",
    private String DPItemName; //"嘉兴南北湖",
    private String dpUserName; //"同程会员_182ED12D98C",
    private String lineAccess; //"中评",


    public String getDpContent() {
        return dpContent;
    }

    public void setDpContent(String dpContent) {
        this.dpContent = dpContent;
    }

    public String getDpDate() {
        return dpDate;
    }

    public void setDpDate(String dpDate) {
        this.dpDate = dpDate;
    }

    public String getDPItemId() {
        return DPItemId;
    }

    public void setDPItemId(String DPItemId) {
        this.DPItemId = DPItemId;
    }

    public String getDPItemName() {
        return DPItemName;
    }

    public void setDPItemName(String DPItemName) {
        this.DPItemName = DPItemName;
    }

    public String getDpUserName() {
        return dpUserName;
    }

    public void setDpUserName(String dpUserName) {
        this.dpUserName = dpUserName;
    }

    public String getLineAccess() {
        return lineAccess;
    }

    public void setLineAccess(String lineAccess) {
        this.lineAccess = lineAccess;
    }


    public ScenicCommentInfo toScenicComment(){
        ScenicCommentInfo info = new ScenicCommentInfo();
        info.setCommentContent(DomainUtil.removeEmojiUtf8mb4(this.getDpContent()));
        info.setCommentPlaceName(this.getDPItemName().replace("嘉兴",""));
        info.setCommentSource(DataBaseConstant.SOURCE_PY_TONGCHENGLVYOU.getCode());
        info.setCommentTime(TypeUtils.castToDate(this.getDpDate()));
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
            }
            info.setCommentScore(s);
        }
        info.setPlaceKeyWord("jzg");
        return info;
    }


}
