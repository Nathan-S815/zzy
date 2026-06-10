package com.nuwa.infrastructure.ticket.database.product.entity;

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
 * 景区产品核销规则配置
 *
 * @author huyonghack@163.com
 * @since 2021-12-09
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
@ApiModel(value = "ScenicspotProductVerificationConfig对象")
public class ScenicspotProductVerificationConfig extends Model<ScenicspotProductVerificationConfig> {
    private static final long serialVersionUID = 1L;

    @ApiModelProperty("id")
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    private Date createTime;

    private Integer deleteFlag;

    private String createByName;

    private String createById;

    private String lastUpdateByName;

    private String lastUpdateById;

    private Date lastUpdateTime;

    @ApiModelProperty("入园方式 0:无需换票，直接验证入园 1:换票入园")
    private Integer entranceMode;

    @ApiModelProperty("入园地址")
    private String inAddresses;

    @ApiModelProperty("换票地址")
    private String ticketGetAddress;

    @ApiModelProperty("入园凭证(多个逗号隔开) qrCode|verificationCode|mobile|idCard")
    private String entranceCertificate;

    @ApiModelProperty("出票(发码发货)操作 0:需要 1：不需要")
    private Integer ticketingMode;

    @ApiModelProperty("出票信息发送 0:发送 1:不发送")
    private Integer enabledTicketingSms;

    @ApiModelProperty("游玩短信发送 0：发送 1：不发送")
    private Integer enabledPlayTipSms;

    @ApiModelProperty("景区产品id")
    private Long scenicspotProductId;

    @ApiModelProperty("版本号")
    @Version
    private Long version;


    public static final String ID = "id";

    public static final String CREATE_TIME = "create_time";

    public static final String DELETE_FLAG = "delete_flag";

    public static final String CREATE_BY_NAME = "create_by_name";

    public static final String CREATE_BY_ID = "create_by_id";

    public static final String LAST_UPDATE_BY_NAME = "last_update_by_name";

    public static final String LAST_UPDATE_BY_ID = "last_update_by_id";

    public static final String LAST_UPDATE_TIME = "last_update_time";

    public static final String ENTRANCE_MODE = "entrance_mode";

    public static final String IN_ADDRESSES = "in_addresses";

    public static final String TICKET_GET_ADDRESS = "ticket_get_address";

    public static final String ENTRANCE_CERTIFICATE = "entrance_certificate";

    public static final String TICKETING_MODE = "ticketing_mode";

    public static final String ENABLED_TICKETING_SMS = "enabled_ticketing_sms";

    public static final String ENABLED_PLAY_TIP_SMS = "enabled_play_tip_sms";

    public static final String SCENICSPOT_PRODUCT_ID = "scenicspot_product_id";

    public static final String VERSION = "version";

}
