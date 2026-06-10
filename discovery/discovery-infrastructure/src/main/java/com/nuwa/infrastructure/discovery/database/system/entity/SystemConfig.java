package com.nuwa.infrastructure.discovery.database.system.entity;

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
 * 系统配置
 *
 * @author huyonghack@163.com
 * @since 2022-09-30
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
@ApiModel(value = "SystemConfig对象")
public class SystemConfig extends Model<SystemConfig> {
    private static final long serialVersionUID = 1L;

    @ApiModelProperty("用户ID，主键，自动增长")
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @ApiModelProperty("键")
    private String confKey;

    @ApiModelProperty("值")
    private String confValue;

    @ApiModelProperty("备注")
    private String remarks;

    @ApiModelProperty("类型  1：业务参数")
    private Integer type;

    @ApiModelProperty("删除标志[1删除 0正常]IM")
    private Integer deleteFlag;

    @ApiModelProperty("更新时间IM")
    private Date updateTime;

    @ApiModelProperty("创建时间IM")
    private Date createTime;


    public static final String ID = "id";

    public static final String CONF_KEY = "conf_key";

    public static final String CONF_VALUE = "conf_value";

    public static final String REMARKS = "remarks";

    public static final String TYPE = "type";

    public static final String DELETE_FLAG = "delete_flag";

    public static final String UPDATE_TIME = "update_time";

    public static final String CREATE_TIME = "create_time";

}
