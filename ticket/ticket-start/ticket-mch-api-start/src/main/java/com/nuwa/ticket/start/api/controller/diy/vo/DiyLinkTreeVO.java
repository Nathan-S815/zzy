package com.nuwa.ticket.start.api.controller.diy.vo;


import com.nuwa.ticket.start.api.controller.util.TreeNode;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

/**
 * @author Ace
 * @date 2017/6/12
 */
@EqualsAndHashCode(callSuper = true)
@ToString
@Data
public class DiyLinkTreeVO extends TreeNode {
    String title;
    String type;
    Integer level;
    String url;

    @ApiModelProperty("支持的自定义页面（为空表示公共组件都支持）")
    private String supportDiyView;

    @ApiModelProperty("跳转方式 mini_inner_page(小程序内部页面),mini_outer_page(小程序外部页面)" +
            "plugs_page(插件页面)" +
            ",web_outer_url(跳转到外部url)")
    private String jumpType;

    @ApiModelProperty("应用id")
    private String appId;

    @ApiModelProperty("应用路径")
    private String appPageUrl;

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    String label;

    public DiyLinkTreeVO() {
    }

    public DiyLinkTreeVO(int id, String name, int parentId) {
        this.id = id;
        this.parentId = parentId;
        this.title = name;
        this.label = name;
    }

    public DiyLinkTreeVO(int id, String name, DiyLinkTreeVO parent) {
        this.id = id;
        this.parentId = parent.getId();
        this.title = name;
        this.label = name;
    }
}
