package com.nuwa.client.ticket.dto.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class MemberVO {
    private Integer userId;

    @ApiModelProperty("openId")
    private String openId;

    @ApiModelProperty("渠道 weixn,douyin")
    private String channelCode;

    @ApiModelProperty("用户登录账户")
    private String userAccount;

    @ApiModelProperty("用户登录密码")
    private String userPassword;

    @ApiModelProperty("用户昵称")
    private String userNike;

    @ApiModelProperty("用户手机")
    private String userPhone;

    @ApiModelProperty("用户虚拟头像")
    private String userImg;

    @ApiModelProperty("用户二维码")
    private String userQrCode;

    @ApiModelProperty("用户身份证ID")
    private String userIdCard;

    @ApiModelProperty("用户真实姓名")
    private String userRealName;

    @ApiModelProperty("用户性别")
    private Integer userSex;

    @ApiModelProperty("用户邮箱")
    private String userEmail;

    @ApiModelProperty("用户生日")
    private String userBirthday;

    @ApiModelProperty("用户所在地区，城市-区县")
    private String region;

    @ApiModelProperty("用户余额")
    private BigDecimal balance;

    @ApiModelProperty("会员等级id，预留字段")
    private Integer userLevelId;

    @ApiModelProperty("用户注册ip")
    private String ip;

    @ApiModelProperty("来源appId")
    private Long srcAppId;

    @ApiModelProperty("来源appName")
    private String srcAppName;

    @ApiModelProperty("设备mac地址。任何终端都有mac地址。")
    private String mac;

    @ApiModelProperty("用户是否被禁用，默认0表示正常用户，-1表示黑名单被禁用的用户")
    private Integer isDisabled;

    @ApiModelProperty("所属商户id")
    private Long mchId;

    @ApiModelProperty("分享code")
    private String shareCode;
}
