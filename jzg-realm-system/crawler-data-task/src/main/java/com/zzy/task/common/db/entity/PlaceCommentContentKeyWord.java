package com.zzy.task.common.db.entity;

import java.io.Serializable;
import java.util.Date;

public class PlaceCommentContentKeyWord implements Serializable {
    /**
     * 
     */
    private Integer id;

    /**
     * 场所名称
     */
    private String placeName;

    /**
     * 标签内容
     */
    private String tagContent;

    /**
     * 标签(关键字)来源
     */
    private String tagSource;

    /**
     * 打该标签的人数
     */
    private Integer tagCount;

    /**
     * 
     */
    private Date updateTime;

    /**
     * crawler_base_test..place_comment_content_key_word
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

    /**
     * 场所名称
     * @return place_name 场所名称
     */
    public String getPlaceName() {
        return placeName;
    }

    /**
     * 场所名称
     * @param placeName 场所名称
     */
    public void setPlaceName(String placeName) {
        this.placeName = placeName;
    }

    /**
     * 标签内容
     * @return tag_content 标签内容
     */
    public String getTagContent() {
        return tagContent;
    }

    /**
     * 标签内容
     * @param tagContent 标签内容
     */
    public void setTagContent(String tagContent) {
        this.tagContent = tagContent;
    }

    /**
     * 标签(关键字)来源
     * @return tag_source 标签(关键字)来源
     */
    public String getTagSource() {
        return tagSource;
    }

    /**
     * 标签(关键字)来源
     * @param tagSource 标签(关键字)来源
     */
    public void setTagSource(String tagSource) {
        this.tagSource = tagSource;
    }

    /**
     * 打该标签的人数
     * @return tag_count 打该标签的人数
     */
    public Integer getTagCount() {
        return tagCount;
    }

    /**
     * 打该标签的人数
     * @param tagCount 打该标签的人数
     */
    public void setTagCount(Integer tagCount) {
        this.tagCount = tagCount;
    }

    /**
     * 
     * @return update_time 
     */
    public Date getUpdateTime() {
        return updateTime;
    }

    /**
     * 
     * @param updateTime 
     */
    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }
}