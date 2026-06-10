package com.nuwa.infrastructure.ticket.database.callcenter.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.util.Date;

/**
 * 
 *
 * @author huyonghack@163.com
 * @since 2021-05-11
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
public class OnlineProblem extends Model<OnlineProblem> {
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
     * 类别
     */
    private String category;

    /**
     * 类型
     */
    private String type;

    /**
     * 问题
     */
    private String problem;

    /**
     * 问题结果
     */
    private String result;

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


    public static final String ID = "id";

    public static final String MCH_ID = "mch_id";

    public static final String APP_ID = "app_id";

    public static final String CATEGORY = "category";

    public static final String TYPE = "type";

    public static final String PROBLEM = "problem";

    public static final String RESULT = "result";

    public static final String CREATE_TIME = "create_time";

    public static final String UPDATE_TIME = "update_time";

    public static final String DELETE_FLAG = "delete_flag";

    public static final String SORT = "sort";

}
