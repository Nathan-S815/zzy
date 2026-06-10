package com.nuwa.client.ticket.dto.clientobject.order.qry;

import com.nuwa.framework.cola.starter.dto.NuwaPageQry;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;


/**
 * <pre>
 * 用户佣金结算汇总PageQry
 * </pre>
 *
 * @author huyonghack@163.com
 * @date 2021-12-17
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "用户佣金结算汇总PageQry")
public class UserPromoteSettleTotalPageQry extends NuwaPageQry {

}
