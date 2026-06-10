package com.nuwa.ticket.start.api.controller.product.vo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonSetter;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

/**
 * @author hy
 */
@Data
public class DayTimeVO {

    @ApiModelProperty("id")
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @ApiModelProperty("产品id")
    private Long scenicspotProductId;

    @ApiModelProperty("库存数量")
    private Long stockNumber;

    @ApiModelProperty("开始时间")
    private String start;

    @ApiModelProperty("结束时间")
    private String end;
}
