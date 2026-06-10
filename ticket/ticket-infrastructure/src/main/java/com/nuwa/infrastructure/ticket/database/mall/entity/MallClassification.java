package com.nuwa.infrastructure.ticket.database.mall.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.util.Date;

/**
 * 商城分类
 *
 * @author huyonghack@163.com
 * @since 2021-06-01
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
public class MallClassification extends Model<MallClassification> {
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
     * 父ID
     */
    private Long pid;

    /**
     * 分类名称
     */
    private String classificationName;

    /**
     * 分类图标
     */
    private String icon;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 更新时间
     */
    private Date updateTime;

    /**
     * 删除标志 [0正常 1删除]
     */
    private Integer deleteFlag;


    public static final String ID = "id";

    public static final String MCH_ID = "mch_id";

    public static final String APP_ID = "app_id";

    public static final String PID = "pid";

    public static final String CLASSIFICATION_NAME = "classification_name";

    public static final String ICON = "icon";

    public static final String CREATE_TIME = "create_time";

    public static final String UPDATE_TIME = "update_time";

    public static final String DELETE_FLAG = "delete_flag";

}
