package com.nuwa.ticket.start.api.controller.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * 景区产品门票有效期规则设置
 *
 * @author huyonghack@163.com
 * @since 2021-10-25
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
@ApiModel(value = "ProductValidPeriodConfigDTO")
public class ProductValidPeriodConfigDTO{

    @ApiModelProperty("规则模式 0:游玩日当天有效,1:游玩日延后n天有效,2:预订日延后n天有效,3:指定日期范围有效")
    private Integer ruleMode;

}
