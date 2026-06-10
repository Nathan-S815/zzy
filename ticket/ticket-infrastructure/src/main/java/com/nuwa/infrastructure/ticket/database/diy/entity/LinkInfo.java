package com.nuwa.infrastructure.ticket.database.diy.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import com.baomidou.mybatisplus.annotation.Version;
import com.baomidou.mybatisplus.annotation.TableId;

import java.io.Serializable;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * 装修链接
 *
 * @author huyonghack@163.com
 * @since 2022-03-16
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
@ApiModel(value = "LinkInfo对象")
public class LinkInfo extends Model<LinkInfo> {
    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @ApiModelProperty("唯一标识")
    private String code;

    @ApiModelProperty("中文名称")
    private String title;

    @ApiModelProperty("父级id")
    private Integer parentId;

    @ApiModelProperty("级别")
    private Integer level;

    @ApiModelProperty("类别 0目录 1:链接")
    private Integer type;

    @ApiModelProperty("wap端跳转路径")
    private String wapUrl;

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


    public static final String ID = "id";

    public static final String CODE = "code";

    public static final String TITLE = "title";

    public static final String PARENT_ID = "parent_id";

    public static final String LEVEL = "level";

    public static final String TYPE = "type";

    public static final String WAP_URL = "wap_url";

    public static final String SUPPORT_DIY_VIEW = "support_diy_view";

    public static final String JUMP_TYPE = "jump_type";

    public static final String APP_ID = "app_id";

    public static final String APP_PAGE_URL = "app_page_url";

}
