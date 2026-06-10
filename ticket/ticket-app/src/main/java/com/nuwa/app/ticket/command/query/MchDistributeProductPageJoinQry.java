package com.nuwa.app.ticket.command.query;

import com.nuwa.framework.cola.starter.dto.NuwaPageQry;
import com.nuwa.framework.database.tk.join.annotation.JoinColumn;
import com.nuwa.infrastructure.ticket.database.product.entity.MerchantProductDistribute;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.util.Date;

/**
 * @author hy
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "商户(分销商)产品分页PageQry")
public class MchDistributeProductPageJoinQry extends NuwaPageQry {

    @ApiModelProperty("产品Id")
    private Long id;

    @ApiModelProperty("产品名称")
    private String name;

    @ApiModelProperty("供应商id")
    private Long supplierId;

    @ApiModelProperty("供应商名称")
    private String supplierName;

    @ApiModelProperty("分销商-商户id")
    private Long distributeMchId;

    @ApiModelProperty("销售订单数")
    private Integer sellOrder;

    @ApiModelProperty("分销商上架状态 0:未上架 1:已上架")
    private Integer distributePublishStatus;

    @ApiModelProperty("供应商上架状态 0:未上架 1:已上架")
    private Integer supplierPublishStatus;

    @ApiModelProperty("审核状态 待审核:0  审核通过:1 审核拒绝:2")
    private Integer auditStatus;

    @ApiModelProperty("景点POI名称")
    private String scenicspotName;

    @ApiModelProperty("上架时间-开始")
    private Date publishTimeStart;

    @ApiModelProperty("上架时间-结束")
    private Date publishTimeEnd;

    @ApiModelProperty("申请时间-开始")
    private Date createTimeStart;

    @ApiModelProperty("申请时间-结束")
    private Date createTimeEnd;

    @ApiModelProperty("POI-省编号")
    private Long provinceId;

    @ApiModelProperty("POI-市编号")
    private Long cityId;

    @ApiModelProperty("POI-区编号")
    private Long areaId;
}
