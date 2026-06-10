package com.nuwa.infrastructure.discovery.database.user.entity;

import java.math.BigDecimal;
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
 * 达人账号绑定表
 *
 * @author huyonghack@163.com
 * @since 2022-04-12
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
@ApiModel(value = "MemberAccountBind对象")
public class MemberAccountBind extends Model<MemberAccountBind> {
    private static final long serialVersionUID = 1L;

    @ApiModelProperty("id，主键，自动增长")
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @ApiModelProperty("用户Id")
    private Integer userId;

    @ApiModelProperty("达人平台用户id")
    private String accountId;

    @ApiModelProperty("认证平台 douyin|xiaohognshu")
    private String channelCode;

    @ApiModelProperty("达人uid")
    private String uId;

    @ApiModelProperty("微信号")
    private String weixinId;

    @ApiModelProperty("昵称")
    private String nick;

    @ApiModelProperty("粉丝数")
    private Integer fansCount;

    @ApiModelProperty("订单数")
    private Integer orderCount;

    @ApiModelProperty("用户地址")
    private String webUrl;

    @ApiModelProperty("用户身份证Id")
    private String idCard;

    @ApiModelProperty("用户真实姓名")
    private String realName;

    @ApiModelProperty("用户性别")
    private Integer sex;

    @ApiModelProperty("用户邮箱")
    private String email;

    @ApiModelProperty("用户生日")
    private String birthday;

    @ApiModelProperty("用户所在地区，城市-区县")
    private String region;

    @ApiModelProperty("等级")
    private String level;

    @ApiModelProperty("用户注册ip")
    private String ip;

    @ApiModelProperty("邀请码")
    private String inviteCode;

    @ApiModelProperty("状态 0:已提交,待审核 1:绑定成功 2:绑定失败 3:已过期")
    private Integer status;

    @ApiModelProperty("删除标志[1删除 0正常]IM")
    private Integer deleteFlag;

    @ApiModelProperty("更新时间IM")
    private Date updateTime;

    @ApiModelProperty("创建时间IM")
    private Date createTime;

    @ApiModelProperty("交易总额")
    private BigDecimal orderTotalAmount;

    @ApiModelProperty("过期时间")
    private Date expiresTime;

    @ApiModelProperty("文字信息")
    private String content;

    @ApiModelProperty("图片信息")
    private String pictures;

    @ApiModelProperty("第三方平台标签 多个之间用逗号隔开")
    private String thirdPartyTag;

    @ApiModelProperty("重新认证状态0：未提交 1：已认证 2：需重新认证 3：认证失败")
    private Integer recertificationStatus;

    public static final String ID = "id";

    public static final String USER_ID = "user_id";

    public static final String ACCOUNT_ID = "account_id";

    public static final String CHANNEL_CODE = "channel_code";

    public static final String U_ID = "u_id";

    public static final String WEIXIN_ID = "weixin_id";

    public static final String NICK = "nick";

    public static final String FANS_COUNT = "fans_count";

    public static final String ORDER_COUNT = "order_count";

    public static final String WEB_URL = "web_url";

    public static final String ID_CARD = "id_card";

    public static final String REAL_NAME = "real_name";

    public static final String SEX = "sex";

    public static final String EMAIL = "email";

    public static final String BIRTHDAY = "birthday";

    public static final String REGION = "region";

    public static final String LEVEL = "level";

    public static final String IP = "ip";

    public static final String INVITE_CODE = "invite_code";

    public static final String STATUS = "status";

    public static final String DELETE_FLAG = "delete_flag";

    public static final String UPDATE_TIME = "update_time";

    public static final String CREATE_TIME = "create_time";

    public static final String ORDER_TOTAL_AMOUNT = "order_total_amount";

    public static final String EXPIRES_TIME = "expires_time";

    public static final String CONTENT = "content";

    public static final String PICTURES = "pictures";

    public static final String THIRD_PARTY_TAG = "third_party_tag";

}
