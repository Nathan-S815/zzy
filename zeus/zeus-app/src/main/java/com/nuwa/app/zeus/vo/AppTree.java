package com.nuwa.app.zeus.vo;


import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.beans.BeanUtils;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Data
public class AppTree extends TreeNode implements Serializable {
    String text;
    String icon;
    Boolean checked = false;

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public AppTree(String text) {
        this.text = text;
    }

    public AppTree() {
    }

    @Override
    public void setChildren(List<TreeNode> children) {
        super.setChildren(children);
    }

    @Override
    public void add(TreeNode node) {
        super.add(node);
        AppTree n = new AppTree();
        BeanUtils.copyProperties(node, n);
    }
}
