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
 * 景区产品预订规则配置
 *
 * @author huyonghack@163.com
 * @since 2021-12-10
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
@ApiModel(value = "ScenicspotProductBookRuleConfig对象")
public class ScenicspotProductBookRuleConfig extends Model<ScenicspotProductBookRuleConfig> {
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

    @ApiModelProperty("景区产品id")
    private Long scenicspotProductId;

    @ApiModelProperty("每笔订单最多购买数量（-1不限）")
    private Integer maxNumberByOrder;

    @ApiModelProperty("每笔订单最少购买数量（-1不限）")
    private Integer minNumberByOrder;

    @ApiModelProperty("手机号n天之内")
    private Integer limitDayByMobile;

    @ApiModelProperty("手机号最大购买数量 （-1不限）")
    private Integer maxNumberByMobile;

    @ApiModelProperty("身份证最大购买数量（-1不限）")
    private Integer maxNumberByCardId;

    @ApiModelProperty("身份证n天之内")
    private Integer limitDayByCardId;

    @ApiModelProperty("0:不限制 1:限制")
    private Integer ageLimit;

    @ApiModelProperty("最小年龄")
    private Integer minAge;

    @ApiModelProperty("最大年龄")
    private Integer maxAge;

    @ApiModelProperty("订单确认模式 0:系统 1:人工")
    private Integer orderConfirmMode;

    @ApiModelProperty("购票后生效小时")
    private Integer buyAfterHour;

    @ApiModelProperty("购票后生效分钟")
    private Integer buyAfterMinute;

    @ApiModelProperty("订单自动取消时间")
    private Integer orderAutoCancelMinute;

    @ApiModelProperty("预订成功短信 1:发送 0:不发送")
    private String bookSuccessSmsSend;

    @ApiModelProperty("最小游玩人数量 -1:不需要 10000：所有")
    private String minNumberByPlay;

    @ApiModelProperty("必填联系人身份信息（Name|Mobile|IdCard）")
    private String requiredByContactPerson;

    @ApiModelProperty("必填游玩人身份信息（Name|Mobile）")
    private String requiredByPlayPersonInfo;

    @ApiModelProperty("必填游玩人证件信息（IdCard|Passport|TaiWanese|HongKongMacaoPass|Dltwtxz）")
    private String requiredByPlayPersonCertificate;

    @ApiModelProperty("联系人信息 1：需要 0：不需要")
    private Integer needContactPerson;

    @ApiModelProperty("版本号")
    @Version
    private Long version;

    @ApiModelProperty("游玩人信息 1：需要 0：不需要")
    private Integer needVisitPerson;


    public static final String ID = "id";

    public static final String CREATE_TIME = "create_time";

    public static final String DELETE_FLAG = "delete_flag";

    public static final String CREATE_BY_NAME = "create_by_name";

    public static final String CREATE_BY_ID = "create_by_id";

    public static final String LAST_UPDATE_BY_NAME = "last_update_by_name";

    public static final String LAST_UPDATE_BY_ID = "last_update_by_id";

    public static final String LAST_UPDATE_TIME = "last_update_time";

    public static final String SCENICSPOT_PRODUCT_ID = "scenicspot_product_id";

    public static final String MAX_NUMBER_BY_ORDER = "max_number_by_order";

    public static final String MIN_NUMBER_BY_ORDER = "min_number_by_order";

    public static final String LIMIT_DAY_BY_MOBILE = "limit_day_by_mobile";

    public static final String MAX_NUMBER_BY_MOBILE = "max_number_by_mobile";

    public static final String MAX_NUMBER_BY_CARD_ID = "max_number_by_card_id";

    public static final String LIMIT_DAY_BY_CARD_ID = "limit_day_by_card_id";

    public static final String AGE_LIMIT = "age_limit";

    public static final String MIN_AGE = "min_age";

    public static final String MAX_AGE = "max_age";

    public static final String ORDER_CONFIRM_MODE = "order_confirm_mode";

    public static final String BUY_AFTER_HOUR = "buy_after_hour";

    public static final String BUY_AFTER_MINUTE = "buy_after_minute";

    public static final String ORDER_AUTO_CANCEL_MINUTE = "order_auto_cancel_minute";

    public static final String BOOK_SUCCESS_SMS_SEND = "book_success_sms_send";

    public static final String MIN_NUMBER_BY_PLAY = "min_number_by_play";

    public static final String REQUIRED_BY_CONTACT_PERSON = "required_by_contact_person";

    public static final String REQUIRED_BY_PLAY_PERSON_INFO = "required_by_play_person_info";

    public static final String REQUIRED_BY_PLAY_PERSON_CERTIFICATE = "required_by_play_person_certificate";

    public static final String NEED_CONTACT_PERSON = "need_contact_person";

    public static final String VERSION = "version";

    public static final String NEED_VISIT_PERSON = "need_visit_person";

}
