package com.nuwa.app.zeus.vo;


import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.beans.BeanUtils;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Data
public class AuthorityMenuTree extends TreeNode implements Serializable {
    String text;
    List<AuthorityMenuTree> nodes = new ArrayList<AuthorityMenuTree>();
    String icon;

    private String type;

    private String description;

    private String href;

    private Integer orderNum;

    private String code;

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public AuthorityMenuTree(String text, List<AuthorityMenuTree> nodes) {
        this.text = text;
        this.nodes = nodes;
    }

    public AuthorityMenuTree() {
    }

    @Override
    public void setChildren(List<TreeNode> children) {
        super.setChildren(children);
        nodes = new ArrayList<AuthorityMenuTree>();
    }

    @Override
    public void add(TreeNode node) {
        super.add(node);
        AuthorityMenuTree n = new AuthorityMenuTree();
        BeanUtils.copyProperties(node, n);
        nodes.add(n);
    }
}
