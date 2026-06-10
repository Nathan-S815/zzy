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
 * 装修模板
 *
 * @author huyonghack@163.com
 * @since 2022-03-11
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
@ApiModel(value = "DiyTemplate对象")
public class DiyTemplate extends Model<DiyTemplate> {
    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @ApiModelProperty("模板名称")
    private String title;

    @ApiModelProperty("描述")
    private String descInfo;

    @ApiModelProperty("模板标识")
    private String mark;

    @ApiModelProperty("类型 DIYVIEW_INDEX:主页 DIYVIEW_USER_CENTER:个人中心 DIYVIEW_BOTTOM_BAR:底部导航")
    private String type;

    @ApiModelProperty("应用类型 SINGLE_SCENIC:单景点 PLATE:全域 ")
    private String appType;

    @ApiModelProperty("模板数据")
    private String value;

    @ApiModelProperty("模板图片")
    private Long image;


    public static final String ID = "id";

    public static final String TITLE = "title";

    public static final String DESC_INFO = "desc_info";

    public static final String MARK = "mark";

    public static final String TYPE = "type";

    public static final String APP_TYPE = "app_type";

    public static final String VALUE = "value";

    public static final String IMAGE = "image";

}
