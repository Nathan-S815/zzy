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
public class MchTicketOrderPageQry extends NuwaPageQry {

    @ApiModelProperty("订单id")
    private Long orderId;

    @ApiModelProperty("订单号")
    private Long orderNo;

    @ApiModelProperty("供应商-商户id")
    private Long supplierMerchantId;

    @ApiModelProperty("分销商-商户id")
    private Long distributeMerchantId;

    @ApiModelProperty("产品名称")
    private String productName;

    @ApiModelProperty("抖音uid")
    private String uid;

    @ApiModelProperty("产品编号")
    private Long productId;

    @ApiModelProperty("供应商id")
    private Long supplierId;

    @ApiModelProperty("供应商产品编码")
    private String supplierProductCode;

    @ApiModelProperty("游玩日期-start")
    private Date visitDateStart;

    @ApiModelProperty("游玩日期-start")
    private Date visitDateEnd;

    @ApiModelProperty("下单时间-start")
    private Date createDateStart;

    @ApiModelProperty("下单时间-start")
    private Date createDateEnd;

    @ApiModelProperty("联系人姓名")
    private String linkName;

    @ApiModelProperty("联系人手机号")
    private String linkMobile;

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

    @ApiModelProperty("是否是导出数据")
    private boolean exportFlag;
}
