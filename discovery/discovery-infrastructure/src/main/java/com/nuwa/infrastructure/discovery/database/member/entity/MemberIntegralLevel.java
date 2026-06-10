package com.nuwa.infrastructure.discovery.database.member.entity;

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
 * 达人积分等级表
 *
 * @author huyonghack@163.com
 * @since 2022-08-23
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
@ApiModel(value = "MemberIntegralLevel对象")
public class MemberIntegralLevel extends Model<MemberIntegralLevel> {
    private static final long serialVersionUID = 1L;

    @ApiModelProperty("id，主键，自动增长")
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @ApiModelProperty("等级")
    private Integer level;

    @ApiModelProperty("等级对应积分数")
    private Integer levelIntegralCount;

    @ApiModelProperty("等级人数")
    private Integer count;

    @ApiModelProperty("等级名称")
    private String levelName;

    @ApiModelProperty("更新时间IM")
    private Date updateTime;

    @ApiModelProperty("创建时间IM")
    private Date createTime;


    public static final String ID = "id";

    public static final String LEVEL = "level";

    public static final String LEVEL_INTEGRAL_COUNT = "level_integral_count";

    public static final String COUNT = "count";

    public static final String LEVEL_NAME = "level_name";

    public static final String UPDATE_TIME = "update_time";

    public static final String CREATE_TIME = "create_time";

}
