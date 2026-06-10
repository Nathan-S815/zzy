package com.nuwa.client.ticket.dto.clientobject.product.qry;

import com.nuwa.framework.cola.starter.dto.NuwaPageQry;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.util.Date;


/**
 * <pre>
 * 用户分页查询PageQry
 * </pre>
 *
 * @author huyonghack@163.com
 * @date 2021-12-17
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "获取供应商分页查询PageQry")
public class MerchantGetSupplierPageQry extends NuwaPageQry {

    @ApiModelProperty(value = "分销商-商户id", hidden = true)
    private Long distributeMerchantId;

    @ApiModelProperty(value = "供应商-商户id", hidden = true)
    private Long supplierMerchantId;

    @ApiModelProperty("供应商名称")
    private String supplierName;

    @ApiModelProperty("分销商名称")
    private String distributeName;

}
