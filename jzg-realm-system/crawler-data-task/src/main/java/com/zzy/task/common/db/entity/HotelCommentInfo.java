package com.zzy.task.common.db.entity;

import cn.hutool.core.util.StrUtil;

import java.io.Serializable;
import java.util.Date;

public class HotelCommentInfo implements Serializable {
    /**
     * 
     */
    private Integer id;

    /**
     * 评论对象
     */
    private String commentPlaceName;

    private String placeKeyWord;

    /**
     * 评论时间
     */
    private Date commentTime;

    /**
     * 评论者
     */
    private String commentUser;

    /**
     * 评价类型(好评:1,差评:0,中评:2)
     */
    private Integer commentType;

    /**
     * 评分
     */
    private Double commentScore;

    /**
     * 评论数据来源
     */
    private String commentSource;

    /**
     * 数据入库时间
     */
    private Date createTime;

    /**
     * 评论内容
     */
    private String commentContent;

    /**
     * crawler_base..hotel_comment_info
     */
    private static final long serialVersionUID = 1L;

    /**
     * 
     * @return id 
     */
    public Integer getId() {
        return id;
    }

    /**
     * 
     * @param id 
     */
    public void setId(Integer id) {
        this.id = id;
    }


    public String getPlaceKeyWord() {
        return placeKeyWord;
    }

    public void setPlaceKeyWord(String placeKeyWord) {
        this.placeKeyWord = placeKeyWord;
    }

    /**
     * 评论对象
     * @return comment_place_name 评论对象
     */
    public String getCommentPlaceName() {
        return commentPlaceName;
    }

    /**
     * 评论对象
     * @param commentPlaceName 评论对象
     */
    public void setCommentPlaceName(String commentPlaceName) {
        this.commentPlaceName = commentPlaceName;
    }

    /**
     * 评论时间
     * @return comment_time 评论时间
     */
    public Date getCommentTime() {
        return commentTime;
    }

    /**
     * 评论时间
     * @param commentTime 评论时间
     */
    public void setCommentTime(Date commentTime) {
        this.commentTime = commentTime;
    }

    /**
     * 评论者
     * @return comment_user 评论者
     */
    public String getCommentUser() {
        return commentUser;
    }

    /**
     * 评论者
     * @param commentUser 评论者
     */
    public void setCommentUser(String commentUser) {
        this.commentUser = commentUser;
    }

    /**
     * 评价类型(好评:1,差评:0,中评:2)
     * @return comment_type 评价类型(好评:1,差评:0,中评:2)
     */
    public Integer getCommentType() {
        return commentType;
    }

    /**
     * 评价类型(好评:1,差评:0,中评:2)
     * @param commentType 评价类型(好评:1,差评:0,中评:2)
     */
    public void setCommentType(Integer commentType) {
        this.commentType = commentType;
    }

    /**
     * 评分
     * @return comment_score 评分
     */
    public Double getCommentScore() {
        return commentScore;
    }

    /**
     * 评分
     * @param commentScore 评分
     */
    public void setCommentScore(Double commentScore) {
        this.commentScore = commentScore;
    }

    /**
     * 评论数据来源
     * @return comment_source 评论数据来源
     */
    public String getCommentSource() {
        return commentSource;
    }

    /**
     * 评论数据来源
     * @param commentSource 评论数据来源
     */
    public void setCommentSource(String commentSource) {
        this.commentSource = commentSource;
    }

    /**
     * 数据入库时间
     * @return create_time 数据入库时间
     */
    public Date getCreateTime() {
        return createTime;
    }

    /**
     * 数据入库时间
     * @param createTime 数据入库时间
     */
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    /**
     * 评论内容
     * @return comment_content 评论内容
     */
    public String getCommentContent() {
        if(StrUtil.isBlank(commentContent)){
            commentContent = "系统默认评论";
        }
        return commentContent;
    }

    /**
     * 评论内容
     * @param commentContent 评论内容
     */
    public void setCommentContent(String commentContent) {
        this.commentContent = commentContent;
    }
}