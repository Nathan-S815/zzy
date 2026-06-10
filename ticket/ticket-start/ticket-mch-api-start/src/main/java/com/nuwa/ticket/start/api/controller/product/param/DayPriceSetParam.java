package com.nuwa.ticket.start.api.controller.product.param;

import com.nuwa.framework.cola.starter.dto.NuwaCommand;
import com.nuwa.ticket.start.api.controller.dto.DateTimeDTO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.math.BigDecimal;
import java.util.List;

/**
 * @author hy
 */
@Data
@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "DayPriceSetParam 价格日志设置")
public class DayPriceSetParam extends NuwaCommand {

    @ApiModelProperty("产品id")
    private Long scenicspotProductId;

    private List<String> dayList;

    @ApiModelProperty("库存数量")
    private Long stockNumber;

    @ApiModelProperty("销售价")
    private BigDecimal salePrice;

    @ApiModelProperty("市场价")
    private BigDecimal marketPrice;

    @ApiModelProperty("采购价")
    private BigDecimal purchasePrice;

    @ApiModelProperty("库存模式 0:非场次 1：场次")
    private Integer stockModel;

    @ApiModelProperty("提前预定条数")
    private Integer beforeDay;

    @ApiModelProperty("场次信息")
    private List<DateTimeDTO> dayTimeList;

    @Override
    public String toJson() {
        return super.toJson();
    }
}
