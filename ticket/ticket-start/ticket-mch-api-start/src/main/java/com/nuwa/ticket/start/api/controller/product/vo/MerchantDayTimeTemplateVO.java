package com.nuwa.ticket.start.api.controller.product.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * @author hy
 */
@Data
public class MerchantDayTimeTemplateVO {
    @ApiModelProperty("id")
    private Long id;

    @ApiModelProperty("模板名称")
    private String title;

    @ApiModelProperty("列表")
    private List<DayTimeTemplateVO> dayTimeList;
}
