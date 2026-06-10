package com.nuwa.client.ticket.dto.clientobject.mall.qry;

import com.nuwa.framework.cola.starter.dto.NuwaPageQry;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.util.Date;


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
public class MallTradePageQry extends NuwaPageQry {
    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "交易订单号")
    private String tradeNo;

    @ApiModelProperty(value = "商品名称")
    private String productName;

    @ApiModelProperty(value = "订单状态(10待支付 11代发货 12待收货 13已完成 14退款审核 15退款中 16已取消 17已退款 18退款失败)")
    private Integer orderStatus;


    @ApiModelProperty(value = "订单完成时间开始")
    private Date finishTimeStart;

    @ApiModelProperty(value = "订单完成时间结束")
    private Date finishTimeEnd;


    @ApiModelProperty(value = "创建时间开始")
    private Date createTimeStart;

    @ApiModelProperty(value = "创建时间结束")
    private Date createTimeEnd;

    @ApiModelProperty(value = "一级分类ID")
    private Long classificationFirstId;

    private Long appId;

}
