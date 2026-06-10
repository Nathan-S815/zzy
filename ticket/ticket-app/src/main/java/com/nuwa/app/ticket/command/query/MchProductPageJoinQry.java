package com.nuwa.app.ticket.command.query;

import com.nuwa.framework.cola.starter.dto.NuwaPageQry;
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
@ApiModel(value = "商户景区分页PageQry")
public class MchProductPageJoinQry extends NuwaPageQry {

    @ApiModelProperty("产品Id")
    private Long id;

    @ApiModelProperty("产品名称")
    private String name;

    @ApiModelProperty("供应商id")
    private String supplierId;

    @ApiModelProperty("供应商产品编码")
    private String supplierProductCode;

    @ApiModelProperty("销售订单数")
    private Integer sellOrder;

    @ApiModelProperty("状态 0:未上架 1:已上架")
    private Integer status;

    @ApiModelProperty("景点POI名称")
    private String scenicspotName;

    @ApiModelProperty("景点POI id")
    private String scenicspotId;

    @ApiModelProperty("上架时间-开始")
    private Date publishTimeStart;

    @ApiModelProperty("上架时间-结束")
    private Date publishTimeEnd;
}
