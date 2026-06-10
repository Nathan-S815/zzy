package com.zzy.task.client.domain;

import cn.hutool.core.util.StrUtil;
import com.alibaba.fastjson.util.TypeUtils;
import com.zzy.core.constant.DataBaseConstant;
import com.zzy.core.utils.UserDataUtil;
import com.zzy.task.common.db.entity.ScenicCommentInfo;
import com.zzy.task.common.util.DomainUtil;

import java.util.Date;
import java.util.Objects;

public class QuNarScenicComment {

    private String author; // "迷*t",
    private String content; // "玩的很不错，小孩特别喜欢，就是吃的一般般，类别也少",
    private String date; // "2018-09-25",
    private String score; // 5,
    private String commentId; // "207978446",
    private String userNickName; // "迷*t"
    private String titleName;
    private Integer cmtNums;


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        QuNarScenicComment that = (QuNarScenicComment) o;
        return author.equals(that.author) &&
                content.equals(that.content) &&
                date.equals(that.date) &&
                titleName.equals(that.titleName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(author, content, date, titleName);
    }

    public Integer getCmtNums() {
        return cmtNums;
    }

    public QuNarScenicComment setCmtNums(Integer cmtNums) {
        this.cmtNums = cmtNums;
        return this;
    }

    public String getTitleName() {
        return titleName;
    }

    public QuNarScenicComment setTitleName(String titleName) {
        this.titleName = titleName;
        return this;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getScore() {
        return score;
    }

    public void setScore(String score) {
        this.score = score;
    }

    public String getCommentId() {
        return commentId;
    }

    public void setCommentId(String commentId) {
        this.commentId = commentId;
    }

    public String getUserNickName() {
        return userNickName;
    }

    public void setUserNickName(String userNickName) {
        this.userNickName = userNickName;
    }


    public ScenicCommentInfo toScenicComment(){
        ScenicCommentInfo info = new ScenicCommentInfo();
        info.setCommentContent(DomainUtil.removeEmojiUtf8mb4(this.getContent()));
        info.setCommentPlaceName(this.getTitleName());
        info.setCommentSource(DataBaseConstant.SOURCE_PY_QUNAR.getCode());
        info.setCommentTime(TypeUtils.castToDate(this.getDate()));
        info.setCommentUser(UserDataUtil.toAnonymousStr(this.getAuthor()));
        info.setCreateTime(new Date());
        if(StrUtil.isBlank(this.getScore())){
            this.setScore("5.0");
        }
        double s = Double.parseDouble(this.getScore());
        info.setCommentScore(s);
        if(s>=4.0){
            info.setCommentType(1);
        }else if(s>=3.0 && s < 4.0){
            info.setCommentType(2);
        }else if(s<3.0 && s>=0){
            info.setCommentType(0);
        }
        return info;
    }

}
