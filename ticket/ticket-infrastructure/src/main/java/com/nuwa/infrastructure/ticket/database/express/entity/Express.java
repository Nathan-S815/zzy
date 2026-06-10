package com.nuwa.infrastructure.ticket.database.express.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.util.Date;

/**
 * 物流信息
 *
 * @author huyonghack@163.com
 * @since 2021-05-18
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
public class Express extends Model<Express> {
    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * AppID
     */
    private Long appId;

    /**
     * 商户ID
     */
    private Long mchId;

    /**
     * 物流公司ID
     */
    private Integer expressCompany;

    /**
     * 物流编号
     */
    private String expressNo;

    /**
     * 备注
     */
    private String remarks;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 更新时间
     */
    private Date updateTime;

    /**
     * 删除标志 0正常1删除
     */
    private Integer deleteFlag;


    public static final String ID = "id";

    public static final String APP_ID = "app_id";

    public static final String MCH_ID = "mch_id";

    public static final String EXPRESS_COMPANY = "express_company";

    public static final String EXPRESS_NO = "express_no";

    public static final String CREATE_TIME = "create_time";

    public static final String UPDATE_TIME = "update_time";

    public static final String DELETE_FLAG = "delete_flag";

}
