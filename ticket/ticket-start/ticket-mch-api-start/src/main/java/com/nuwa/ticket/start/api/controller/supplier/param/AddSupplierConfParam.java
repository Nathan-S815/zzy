package com.nuwa.ticket.start.api.controller.supplier.param;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.nuwa.framework.cola.starter.dto.NuwaCommand;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Date;

/**
 * @author hy
 */
@Data
@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "AddSupplierConfParam 添加供应商配置")
public class AddSupplierConfParam extends NuwaCommand {
    @ApiModelProperty("名称")
    private String name;

    @ApiModelProperty("供应商id")
    private Long supplierId;

    @ApiModelProperty("渠道商户id")
    private String channelMerchantId;

    @ApiModelProperty("渠道秘钥")
    private String channelSecretKey;

    @ApiModelProperty("分销商渠道过期时间")
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date expirationDate;

    @ApiModelProperty("接口地址")
    private String apiUrl;
}
