package com.nuwa.client.ticket.dto.clientobject.order.qry;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import com.nuwa.framework.cola.starter.dto.NuwaPageQry;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;


/**
 * <pre>
 * 订单表 PageQry参数对象
 * </pre>
 *
 * @author huyonghack@163.com
 * @date 2021-12-17
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "订单表PageQry")
public class UserTicketOrderPageQry extends NuwaPageQry {
    private static final long serialVersionUID = 1L;

    /**
     * 创建:0;
     * 待支付:1;
     * 待出票:2;
     * 已出票:3;
     * 已完成:4;
     * 已取消:5
     */
    @ApiModelProperty("状态 待支付:1|出票中:2|待使用:3|已完成:4")
    private Integer status;

}
