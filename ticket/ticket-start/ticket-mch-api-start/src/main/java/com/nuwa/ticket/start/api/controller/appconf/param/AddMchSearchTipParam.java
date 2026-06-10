package com.nuwa.ticket.start.api.controller.appconf.param;

import com.nuwa.framework.cola.starter.dto.NuwaCommand;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.NotBlank;

/**
 * @author hy
 */
@Data
@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "搜索提示配置 添加搜索配置")
public class AddMchSearchTipParam extends NuwaCommand {

    @ApiModelProperty("数据类别 hotel(酒店) scenicspot(景区)  wenchuang(文创) meishi(美食)")
    private String bizList;

    @ApiModelProperty("后端关键字")
    private String keyword;
}
