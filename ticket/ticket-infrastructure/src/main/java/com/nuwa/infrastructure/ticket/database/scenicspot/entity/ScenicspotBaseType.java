package com.nuwa.infrastructure.ticket.database.scenicspot.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import java.util.Date;
import com.baomidou.mybatisplus.annotation.Version;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * 景区类型表
 *
 * @author huyonghack@163.com
 * @since 2021-12-13
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
@ApiModel(value = "ScenicspotBaseType对象")
public class ScenicspotBaseType extends Model<ScenicspotBaseType> {
    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @ApiModelProperty("类型名称")
    private String labelName;

    @ApiModelProperty("图标")
    private String icon;

    @ApiModelProperty("权重")
    private Integer weight;

    @ApiModelProperty(value = "poiType[scenic(景区) venue(文博)]")
    private String poiType;

    @ApiModelProperty("创建时间")
    private Date createTime;

    @ApiModelProperty("删除标志")
    private Integer deleteFlag;

    @ApiModelProperty("创建人id")
    private String createById;

    @ApiModelProperty("创建人姓名")
    private String createByName;


    public static final String ID = "id";

    public static final String LABEL_NAME = "label_name";

    public static final String ICON = "icon";

    public static final String WEIGHT = "weight";

    public static final String CREATE_TIME = "create_time";

    public static final String DELETE_FLAG = "delete_flag";

    public static final String CREATE_BY_ID = "create_by_id";

    public static final String CREATE_BY_NAME = "create_by_name";

}
