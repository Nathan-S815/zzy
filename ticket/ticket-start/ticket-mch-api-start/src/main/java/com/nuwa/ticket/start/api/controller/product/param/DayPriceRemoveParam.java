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
@ApiModel(value = "DayPriceRemoveParam 价格日志删除")
public class DayPriceRemoveParam extends NuwaCommand {

    @ApiModelProperty("产品id")
    private Long scenicspotProductId;

    private List<String> dayList;

}
