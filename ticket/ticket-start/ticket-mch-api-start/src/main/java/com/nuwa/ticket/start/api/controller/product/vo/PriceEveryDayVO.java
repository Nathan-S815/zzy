package com.nuwa.ticket.start.api.controller.product.vo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.nuwa.ticket.start.api.controller.dto.DateTimeDTO;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * @author hy
 */
@Data
public class PriceEveryDayVO {

    @ApiModelProperty("id")
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @ApiModelProperty("产品id")
    private Long scenicspotProductId;

    @ApiModelProperty("日期")
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date date;

    @ApiModelProperty("库存模式 0:非场次 1：场次")
    private Integer stockModel;

    @ApiModelProperty("库存数量")
    private Long stockNumber;

    @ApiModelProperty("销售价")
    private BigDecimal salePrice;

    @ApiModelProperty("市场价")
    private BigDecimal marketPrice;

    @ApiModelProperty("采购价")
    private BigDecimal purchasePrice;

    @ApiModelProperty("提前预定天数")
    private Integer beforeDay;

    @ApiModelProperty("场次信息")
    private List<DateTimeDTO> dayTimeList;
}
