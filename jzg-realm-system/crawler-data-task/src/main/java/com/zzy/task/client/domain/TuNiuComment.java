package com.zzy.task.client.domain;

import cn.hutool.core.util.StrUtil;
import com.vdurmont.emoji.EmojiParser;
import com.zzy.core.constant.DataBaseConstant;
import com.zzy.core.utils.UserDataUtil;
import com.zzy.task.common.db.entity.HotelCommentInfo;

import java.util.Date;

public class TuNiuComment {

    private String titleName;
    private String reviewerName; //": "嫩个憨皮",
    private String content; //": "毛巾浴巾有点扎，卫生纸质量也太差了吧。其他还行",
    private String score; //": 4,
    private TuNiuCommentSubInfo comment;
    private Integer contentsCommentNum;

    public Integer getContentsCommentNum() {
        return contentsCommentNum;
    }

    public TuNiuComment setContentsCommentNum(Integer contentsCommentNum) {
        this.contentsCommentNum = contentsCommentNum;
        return this;
    }

    public String getTitleName() {
        return titleName;
    }

    public TuNiuComment setTitleName(String titleName) {
        this.titleName = titleName;
        return this;
    }

    public String getReviewerName() {
        return reviewerName;
    }

    public void setReviewerName(String reviewerName) {
        this.reviewerName = reviewerName;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getScore() {
        return score;
    }

    public void setScore(String score) {
        this.score = score;
    }

    public TuNiuCommentSubInfo getComment() {
        return comment;
    }

    public void setComment(TuNiuCommentSubInfo comment) {
        this.comment = comment;
    }



    public HotelCommentInfo toHotelComment(){
        HotelCommentInfo info = new HotelCommentInfo();
        info.setCommentContent(EmojiParser.removeAllEmojis(this.getContent()));
        info.setCommentPlaceName(this.getTitleName());
        info.setCommentSource(DataBaseConstant.SOURCE_PY_TUNIU.getCode());
        info.setCommentTime(new Date());
        info.setCommentUser(UserDataUtil.toAnonymousStr(this.getReviewerName()));
        info.setCreateTime(new Date());
        if(StrUtil.isBlank(this.getScore())){
            this.setScore("-1");
            info.setCommentType(-1);
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


