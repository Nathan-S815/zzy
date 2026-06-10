package com.nuwa.infrastructure.zeus.database.base.entity;

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
 * 平台更新日志详情
 *
 * @author huyonghack@163.com
 * @since 2021-07-12
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
@ApiModel(value = "PlatUpgradeDetails对象")
public class PlatUpgradeDetails extends Model<PlatUpgradeDetails> {
    private static final long serialVersionUID = 1L;

    @ApiModelProperty("主键")
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @ApiModelProperty("平台更新日志ID")
    private Long platUpgradeId;

    @ApiModelProperty("详情")
    private String item;

    @ApiModelProperty("排序")
    private Integer sortNum;

    @ApiModelProperty("创建时间")
    private Date createTime;

    @ApiModelProperty("更新时间")
    private Date updateTime;

    @ApiModelProperty("删除标志[0正常 1删除]")
    private Integer deleteFlag;


    public static final String ID = "id";

    public static final String PLAT_UPGRADE_ID = "plat_upgrade_id";

    public static final String ITEM = "item";

    public static final String SORT_NUM = "sort_num";

    public static final String CREATE_TIME = "create_time";

    public static final String UPDATE_TIME = "update_time";

    public static final String DELETE_FLAG = "delete_flag";

}
