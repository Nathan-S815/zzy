package com.nuwa.ticket.start.api.controller.articel.vo;


import com.nuwa.ticket.start.api.controller.util.TreeNode;
import lombok.ToString;

/**
 *
 * @author Ace
 * @date 2017/6/12
 */
@ToString
public class ArticleCategoryTreeVO extends TreeNode {
    String title;
    Integer level;
    String label;

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public ArticleCategoryTreeVO() {
    }

    public ArticleCategoryTreeVO(int id, String name, int parentId) {
        this.id = id;
        this.parentId = parentId;
        this.title = name;
        this.label = name;
    }
    public ArticleCategoryTreeVO(int id, String name, ArticleCategoryTreeVO parent) {
        this.id = id;
        this.parentId = parent.getId();
        this.title = name;
        this.label = name;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Integer getLevel() {
        return level;
    }

    public void setLevel(Integer level) {
        this.level = level;
    }
}
