package com.nuwa.infrastructure.ticket.database.mall.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.util.Date;

/**
 * 短信信息
 *
 * @author huyonghack@163.com
 * @since 2021-06-01
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
public class MallMessage extends Model<MallMessage> {
    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 发布人
     */
    private Long mchId;

    /**
     * AppID
     */
    private Long appId;

    /**
     * 创建用户
     */
    private String createBy;

    /**
     * 更新用户
     */
    private String updateBy;

    /**
     * 业务类型(下单;退款)
     */
    private String bizCode;

    /**
     * 信息内容
     */
    private String messageContent;

    /**
     * 接收方手机号
     */
    private String receivePhone;

    /**
     * 发送状态(10等待发送;11发送中;20成功;30失败)
     */
    private Integer sendStatus;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 更新时间
     */
    private Date updateTime;

    /**
     * 删除标志  0正常1删除
     */
    private Integer deleteFlag;


    public static final String ID = "id";

    public static final String MCH_ID = "mch_id";

    public static final String APP_ID = "app_id";

    public static final String CREATE_BY = "create_by";

    public static final String UPDATE_BY = "update_by";

    public static final String BIZ_CODE = "biz_code";

    public static final String MESSAGE_CONTENT = "message_content";

    public static final String RECEIVE_PHONE = "receive_phone";

    public static final String SEND_STATUS = "send_status";

    public static final String CREATE_TIME = "create_time";

    public static final String UPDATE_TIME = "update_time";

    public static final String DELETE_FLAG = "delete_flag";

}
