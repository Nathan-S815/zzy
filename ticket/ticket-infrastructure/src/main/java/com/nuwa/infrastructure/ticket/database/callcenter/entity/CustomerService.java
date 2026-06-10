package com.nuwa.infrastructure.ticket.database.callcenter.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.nuwa.infrastructure.ticket.json.serializer.MaterialJson;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.util.Date;

/**
 * 客服基础信息表
 *
 * @author huyonghack@163.com
 * @since 2021-05-15
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
public class CustomerService extends Model<CustomerService> {
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
     * 客服名称
     */
    private String name;

    /**
     * 客服头像
     */
    @JsonSerialize(using = MaterialJson.class)
    private String pic;

    /**
     * 备注
     */
    private String remark;

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

    public static final String NAME = "name";

    public static final String PIC = "pic";

    public static final String REMARK = "remark";

    public static final String CREATE_TIME = "create_time";

    public static final String UPDATE_TIME = "update_time";

    public static final String DELETE_FLAG = "delete_flag";

    public static final String SORT = "sort";

}
