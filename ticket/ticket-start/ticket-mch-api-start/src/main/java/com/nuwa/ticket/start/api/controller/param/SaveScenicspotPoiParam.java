package com.nuwa.ticket.start.api.controller.param;

import com.nuwa.framework.cola.starter.dto.NuwaCommand;
import com.nuwa.ticket.start.api.controller.dto.ScenicspotBaseInfoDTO;
import com.nuwa.ticket.start.api.controller.dto.ScenicspotMaterialDTO;
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
@ApiModel(value = "SaveScenicspotPOIForm 保存景点POI对象")
public class SaveScenicspotPoiParam extends NuwaCommand {
    @ApiModelProperty(value = "基本信息", required = true)
    @NotNull(message = "baseInfo信息不能为空")
    private ScenicspotBaseInfoDTO baseInfo;

    @ApiModelProperty(value = "基本信息")
    private List<ScenicspotMaterialDTO> materialList;
}
