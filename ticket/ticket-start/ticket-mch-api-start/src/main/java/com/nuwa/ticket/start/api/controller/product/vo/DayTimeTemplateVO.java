package com.nuwa.ticket.start.api.controller.product.vo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

/**
 * @author hy
 */
@Data
public class DayTimeTemplateVO {

    @ApiModelProperty("库存数量")
    private Long stockNumber;

    @ApiModelProperty("开始时间")
    @JsonFormat(pattern = "HH:mm")
    private String start;

    @ApiModelProperty("结束时间")
    @JsonFormat(pattern = "HH:mm")
    private String end;
}
