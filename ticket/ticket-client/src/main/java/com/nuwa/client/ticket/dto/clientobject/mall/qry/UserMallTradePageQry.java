package com.nuwa.client.ticket.dto.clientobject.mall.qry;

import com.nuwa.framework.cola.starter.dto.NuwaPageQry;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;


/**
 * <pre>
 *  PageQry参数对象
 * </pre>
 *
 * @author huyonghack@163.com
 * @date 2021-06-01
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "PageQry")
public class UserMallTradePageQry extends NuwaPageQry {
    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "交易订单号")
    private String tradeNo;

    @ApiModelProperty(value = "商品名称")
    private String productName;

    @ApiModelProperty(value = "订单状态(10待支付 11代发货 12待收货 13已完成)")
    private Integer orderStatus;

    @ApiModelProperty(value = "一级分类ID")
    private Long classificationFirstId;

}
