package com.nuwa.ticket.start.api.controller.param;

import com.nuwa.framework.cola.starter.dto.NuwaCommand;
import com.nuwa.ticket.start.api.controller.dto.ScenicspotBaseInfoDTO;
import com.nuwa.ticket.start.api.controller.dto.ScenicspotMaterialDTO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * @author hy
 */
@Data
@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "SaveScenicspotLabelParam 保存景点标签")
public class SaveScenicspotLabelParam extends NuwaCommand {

    @ApiModelProperty("标签名称")
    @NotBlank(message = "标签名称不能为空")
    private String labelName;

    @ApiModelProperty("所属商户id")
    private Long mchId;
}
