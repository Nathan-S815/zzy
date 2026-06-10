package com.nuwa.client.ticket.dto.clientobject.dict.co;

import com.nuwa.framework.cola.starter.dto.NuwaCO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "修改字典")
public class EditDictDataCO extends NuwaCO {

    @ApiModelProperty(value = "字典编码（id）")
    private String dictCode;

    @ApiModelProperty(value = "字典标签")
    private String dictLabel;
    @ApiModelProperty(value = "字典键值")
    private String dictValue;

}
