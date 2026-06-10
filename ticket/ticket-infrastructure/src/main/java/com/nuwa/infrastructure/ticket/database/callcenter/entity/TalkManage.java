package com.nuwa.infrastructure.ticket.database.callcenter.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.util.Date;

/**
 * 会话管理
 *
 * @author huyonghack@163.com
 * @since 2021-05-15
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
public class TalkManage extends Model<TalkManage> {
    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 商户id
     */
    private Long mchId;

    /**
     * 所属Appid
     */
    private Long appId;

    /**
     * 提示语
     */
    private String title;

    /**
     * 问题id
     */
    private String problems;

    /**
     * 创建时间IM
     */
    private Date createTime;

    /**
     * 更新时间IM
     */
    private Date updateTime;

    /**
     * 删除标志[1删除 0正常]IM
     */
    private Integer deleteFlag;

    /**
     * 排序
     */
    private Integer sort;

    /**
     * 帐号启用状态  10启用11禁用
     */
    private Integer enableStatus;


    public static final String ID = "id";

    public static final String MCH_ID = "mch_id";

    public static final String APP_ID = "app_id";

    public static final String TITLE = "title";

    public static final String PROBLEMS = "problems";

    public static final String CREATE_TIME = "create_time";

    public static final String UPDATE_TIME = "update_time";

    public static final String DELETE_FLAG = "delete_flag";

    public static final String SORT = "sort";

    public static final String ENABLE_STATUS = "enable_status";

}
