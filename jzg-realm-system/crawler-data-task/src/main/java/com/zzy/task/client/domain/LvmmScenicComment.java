package com.zzy.task.client.domain;

import cn.hutool.core.util.StrUtil;
import com.alibaba.fastjson.util.TypeUtils;
import com.zzy.core.constant.DataBaseConstant;
import com.zzy.core.utils.UserDataUtil;
import com.zzy.task.common.db.entity.ScenicCommentInfo;
import com.zzy.task.common.util.DomainUtil;

import java.util.Date;

public class LvmmScenicComment extends LvmmHotelComment {


    public ScenicCommentInfo toScenicComment(){
        ScenicCommentInfo info = new ScenicCommentInfo();
        info.setCommentContent(DomainUtil.removeEmojiUtf8mb4(this.getContent()));
        info.setCommentPlaceName(this.getTitleName());
        info.setCommentSource(DataBaseConstant.SOURCE_PY_LVMAMA.getCode());
        info.setCommentTime(TypeUtils.castToDate(this.getCommentTime()));
        info.setCommentUser(UserDataUtil.toAnonymousStr(this.getUserName()));
        info.setCreateTime(new Date());
        if(StrUtil.isBlank(this.getScore())){
            this.setScore("5");
        }
        double s = Double.parseDouble(this.getScore());
        info.setCommentScore(s);
        if(s>=4.0 || s<0){
            info.setCommentType(1);
        }else if(s>=3.0 && s < 4.0){
            info.setCommentType(2);
        }else if(s<3.0 && s>=0){
            info.setCommentType(0);
        }
        return info;
    }
}
