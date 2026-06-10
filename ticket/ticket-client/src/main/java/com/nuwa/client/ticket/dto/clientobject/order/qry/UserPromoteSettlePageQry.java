package com.nuwa.client.ticket.dto.clientobject.order.qry;

import com.nuwa.framework.cola.starter.dto.NuwaPageQry;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.util.Date;


/**
 * <pre>
 * 用户佣金结算PageQry
 * </pre>
 *
 * @author huyonghack@163.com
 * @date 2021-12-17
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "用户佣金结算PageQry")
public class UserPromoteSettlePageQry extends NuwaPageQry {

    @ApiModelProperty("用户id")
    private Long userId;

    @ApiModelProperty("景区id")
    private Long scenicId;

}
