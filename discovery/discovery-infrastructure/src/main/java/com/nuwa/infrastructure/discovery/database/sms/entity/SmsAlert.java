package com.nuwa.infrastructure.discovery.database.sms.entity;

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
 * 短信提醒表
 *
 * @author huyonghack@163.com
 * @since 2021-11-30
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
@ApiModel(value = "SmsAlert对象")
public class SmsAlert extends Model<SmsAlert> {
    private static final long serialVersionUID = 1L;

    @ApiModelProperty("id")
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @ApiModelProperty("业务类型")
    private String bizCode;

    @ApiModelProperty("手机号")
    private String mobile;

    @ApiModelProperty("标题")
    private String title;

    @ApiModelProperty("正文")
    private String content;

    @ApiModelProperty("发送时间")
    private Date sendTime;

    @ApiModelProperty("失效时间")
    private Date deadTime;

    @ApiModelProperty("发送方式 10,短信 ;20，语音")
    private Integer sendType;

    @ApiModelProperty("发送标志 1:待发送,2:发送成功,3:发送失败")
    private Integer status;

    @ApiModelProperty("外部短信号")
    private String smsId;

    @ApiModelProperty("错误原因")
    private String errMsg;

    @ApiModelProperty("外部短信号")
    private String ip;

    @ApiModelProperty("创建时间")
    private Date createDate;

    @ApiModelProperty("删除,0正常")
    private Integer deleteFlag;


    public static final String ID = "id";

    public static final String BIZ_CODE = "biz_code";

    public static final String MOBILE = "mobile";

    public static final String TITLE = "title";

    public static final String CONTENT = "content";

    public static final String SEND_TIME = "send_time";

    public static final String DEAD_TIME = "dead_time";

    public static final String SEND_TYPE = "send_type";

    public static final String STATUS = "status";

    public static final String SMS_ID = "sms_id";

    public static final String ERR_MSG = "err_msg";

    public static final String IP = "ip";

    public static final String CREATE_DATE = "create_date";

    public static final String DELETE_FLAG = "delete_flag";

}
