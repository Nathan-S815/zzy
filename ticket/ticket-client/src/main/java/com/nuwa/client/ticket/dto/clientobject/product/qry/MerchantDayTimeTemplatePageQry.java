package com.nuwa.client.ticket.dto.clientobject.product.qry;

import com.nuwa.framework.cola.starter.dto.NuwaPageQry;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * 场次模板分页
 *
 * @author hy
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "MerchantDayTimeTemplatePageQry")
public class MerchantDayTimeTemplatePageQry extends NuwaPageQry {
    @ApiModelProperty("标题")
    private String title;
}
