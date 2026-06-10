package com.nuwa.client.ticket.dto.clientobject.payconfig.qry;

import com.nuwa.framework.cola.starter.dto.NuwaPageQry;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * 支付平台配置分页查询
 *
 * @author hy
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "PayConfigPageQry")
public class PayConfigPageQry extends NuwaPageQry {

    private Long mchId;

}
