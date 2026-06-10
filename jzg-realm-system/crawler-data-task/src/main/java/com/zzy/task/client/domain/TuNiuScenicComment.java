package com.zzy.task.client.domain;

import cn.hutool.core.util.StrUtil;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.util.TypeUtils;
import com.zzy.core.constant.DataBaseConstant;
import com.zzy.core.utils.UserDataUtil;
import com.zzy.task.common.db.entity.ScenicCommentInfo;
import com.zzy.task.common.util.DomainUtil;

import java.util.Date;
import java.util.Objects;

public class TuNiuScenicComment {

    private String titleName;
    private String userName; // "8178086200",
    private JSONObject subCompGrade; // { "取票便捷; // 3, "景区服务": 3, "预订优惠": 3
    private String remarkTime; // "2019-10-21 10:46:49",
    private String content; // "买的值，演出不错！同志们都很开心",
    private String compGrade; // 3


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TuNiuScenicComment that = (TuNiuScenicComment) o;
        return titleName.equals(that.titleName) &&
                userName.equals(that.userName) &&
                content.equals(that.content);
    }

    @Override
    public int hashCode() {
        return Objects.hash(titleName, userName, content);
    }

    public String getTitleName() {
        return titleName;
    }

    public TuNiuScenicComment setTitleName(String titleName) {
        this.titleName = titleName;
        return this;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public JSONObject getSubCompGrade() {
        return subCompGrade;
    }

    public void setSubCompGrade(JSONObject subCompGrade) {
        this.subCompGrade = subCompGrade;
    }

    public String getRemarkTime() {
        return remarkTime;
    }

    public void setRemarkTime(String remarkTime) {
        this.remarkTime = remarkTime;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getCompGrade() {
        return compGrade;
    }

    public void setCompGrade(String compGrade) {
        this.compGrade = compGrade;
    }

    public ScenicCommentInfo toScenicComment(){
        ScenicCommentInfo info = new ScenicCommentInfo();
        info.setCommentContent(DomainUtil.removeEmojiUtf8mb4(this.getContent()));
        info.setCommentPlaceName(this.getTitleName());
        info.setCommentSource(DataBaseConstant.SOURCE_PY_TUNIU.getCode());
        info.setCommentTime(TypeUtils.castToDate(this.getRemarkTime()));
        info.setCommentUser(UserDataUtil.toAnonymousStr(this.getUserName()));
        info.setCreateTime(new Date());
        if(StrUtil.isBlank(this.getCompGrade())){
            this.setCompGrade("-1");
            info.setCommentType(-1);
        }else{
            double s = Double.valueOf(this.getCompGrade());
            info.setCommentScore(s);
            if(s>=4.0){
                info.setCommentType(1);
            }else if(s>=3.0 && s < 4.0){
                info.setCommentType(2);
            }else if(s<3.0 && s>=0){
                info.setCommentType(0);
            }
        }
        return info;
    }
}
