package com.nuwa.ticket.start.api.controller.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import java.util.List;

/**
 * @author hy
 */
@Data
@Accessors(chain = true)
@ApiModel(value = "ProductVerificationRuleConfigDTO 景区产品核销规则配置")
public class ProductVerificationRuleConfigDTO {
    @ApiModelProperty("入园方式 0:无需换票，直接验证入园 1:换票入园")
    private Integer entranceMode;

    @ApiModelProperty("入园地址")
    private String inAddresses;

    @ApiModelProperty("入园凭证(多个逗号隔开) qrCode|verificationCode|mobile|idCard")
    private String entranceCertificate;

    private List<EntranceCertificateVO> entranceCertificateItems;

}
