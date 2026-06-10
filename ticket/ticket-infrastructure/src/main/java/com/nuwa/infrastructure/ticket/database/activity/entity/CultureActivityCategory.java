package com.nuwa.infrastructure.ticket.database.activity.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.util.Date;

/**
 * 活动类别
 *
 * @author huyonghack@163.com
 * @since 2021-08-16
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
public class CultureActivityCategory extends Model<CultureActivityCategory> {
    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 商户ID
     */
    private Long mchId;

    /**
     * AppID
     */
    private Long appId;

    /**
     * 类别名称
     */
    private String categoryName;

    /**
     * 上下架状态[0下架 1上架]
     */
    private Integer publishStatus;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 更新时间
     */
    private Date updateTime;

    /**
     * 删除标志[0正常 1删除]
     */
    private Integer deleteFlag;


    public static final String ID = "id";

    public static final String MCH_ID = "mch_id";

    public static final String APP_ID = "app_id";

    public static final String CATEGORY_NAME = "category_name";

    public static final String PUBLISH_STATUS = "publish_status";

    public static final String CREATE_TIME = "create_time";

    public static final String UPDATE_TIME = "update_time";

    public static final String DELETE_FLAG = "delete_flag";

}
