package com.nuwa.client.ticket.dto.clientobject.supplier.qry;

import com.nuwa.framework.cola.starter.dto.NuwaPageQry;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.util.Date;


/**
 * <pre>
 * 供应商配置 PageQry参数对象
 * </pre>
 *
 * @author huyonghack@163.com
 * @date 2021-12-17
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "供应商配置PageQry")
public class SupplierConfPageQry extends NuwaPageQry {

    @ApiModelProperty(value = "商户id",hidden = true)
    private Long mchId;

    @ApiModelProperty("名称")
    private String name;

    @ApiModelProperty("供应商类别")
    private Long supplierId;

    @ApiModelProperty("供应商商户id")
    private String channelMerchantId;
}
