package com.nuwa.ticket.start.api.controller.product.param;

import com.nuwa.framework.cola.starter.dto.NuwaCommand;
import com.nuwa.ticket.start.api.controller.product.vo.DayTimeTemplateVO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * @author hy
 */
@Data
@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "ModifyDayTimeTemplateParam 修改场次模板")
public class ModifyDayTimeTemplateParam extends NuwaCommand {
    @ApiModelProperty("id")
    @NotNull
    private Long id;

    @ApiModelProperty("场次名称")
    private String title;

    @ApiModelProperty("场次列表")
    @NotNull
    private List<DayTimeTemplateVO> dayTimeList;
}
