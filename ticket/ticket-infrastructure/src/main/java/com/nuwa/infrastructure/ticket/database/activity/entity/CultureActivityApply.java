package com.nuwa.infrastructure.ticket.database.activity.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.util.Date;

/**
 * 文化活动报名
 *
 * @author huyonghack@163.com
 * @since 2021-06-08
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
public class CultureActivityApply extends Model<CultureActivityApply> {
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
     * 活动ID
     */
    private Long activityId;

    /**
     * 报名用户ID
     */
    private Long userId;

    /**
     * 联系人姓名
     */
    private String contactsName;

    /**
     * 联系人电话
     */
    private String contactsMobile;

    /**
     * 人数
     */
    private Integer peopleNum;

    /**
     * 身份证号
     */
    private String idCard;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 更新时间
     */
    private Date updateTime;

    /**
     * 删除标志 [0正常,1删除]
     */
    private Integer deleteFlag;


    public static final String ID = "id";

    public static final String MCH_ID = "mch_id";

    public static final String APP_ID = "app_id";

    public static final String ACTIVITY_ID = "activity_id";

    public static final String USER_ID = "user_id";

    public static final String CONTACTS_NAME = "contacts_name";

    public static final String CONTACTS_MOBILE = "contacts_mobile";

    public static final String PEOPLE_NUM = "people_num";

    public static final String ID_CARD = "id_card";

    public static final String CREATE_TIME = "create_time";

    public static final String UPDATE_TIME = "update_time";

    public static final String DELETE_FLAG = "delete_flag";

}
