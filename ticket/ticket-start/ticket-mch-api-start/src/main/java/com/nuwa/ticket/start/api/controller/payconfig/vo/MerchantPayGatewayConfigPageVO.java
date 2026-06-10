package com.nuwa.ticket.start.api.controller.payconfig.vo;

import cn.hutool.json.JSONObject;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class MerchantPayGatewayConfigPageVO {
    @ApiModelProperty("编号")
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @ApiModelProperty("支付平台商户号")
    private String gatewayMchId;

    @ApiModelProperty("商户id")
    private Long mchId;

    @ApiModelProperty("密钥")
    private String gatewaySecretKey;

    @ApiModelProperty("支付方式 小程序：alipay_mini 服务商小程序alipay_mini_service  小程序模板:alipay_mini_template")
    private String gatewayPayType;

    @ApiModelProperty("支付应用id")
    private String channelAppId;

    @ApiModelProperty("支付商户id")
    private String channelMerchantId;

    @ApiModelProperty("名称")
    private String name;

    @ApiModelProperty("扩展参数")
    private JSONObject extendObj;

    @ApiModelProperty("状态,0-停止使用,1-使用中")
    private Integer status;
}
