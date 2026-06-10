package com.nuwa.ticket.start.api.controller.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

/**
 * @author hy
 */
@Data
@Accessors(chain = true)
@ApiModel(value = "ProductBookRuleConfigDTO 产品预订规则")
public class ProductBookRuleConfigDTO {
    @ApiModelProperty("每笔订单最多购买数量（-1不限）")
    private Integer maxNumberByOrder;

    @ApiModelProperty("每笔订单最少购买数量（-1不限）")
    private Integer minNumberByOrder;

    @ApiModelProperty("订单确认模式 0:系统自动 1:人工")
    private Integer orderConfirmMode;

    @ApiModelProperty("联系人信息 1：需要 0：不需要")
    private Integer needContactPerson;

    @ApiModelProperty("游玩人信息 1：需要 0：不需要 2：只需要1位游玩人信息")
    private Integer needVisitPerson;

    @ApiModelProperty("预订成功短信 1:发送 0:不发送")
    private String bookSuccessSmsSend;

    @ApiModelProperty("必填联系人信息（Name|Mobile|IdCard）")
    private String requiredByContactPerson;

    @ApiModelProperty("必填游玩人身份信息（Name|Mobile）")
    private String requiredByPlayPersonInfo;

    @ApiModelProperty("必填游玩人证件信息（IdCard|Other）")
    private String requiredByPlayPersonCertificate;

    @ApiModelProperty("手机号n天之内")
    private Integer limitDayByMobile;

    @ApiModelProperty("手机号最大购买数量 （-1不限）")
    private Integer maxNumberByMobile;

    @ApiModelProperty("身份证最大购买数量（-1不限）")
    private Integer maxNumberByCardId;

    @ApiModelProperty("身份证n天之内")
    private Integer limitDayByCardId;
}
