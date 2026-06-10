package com.nuwa.client.ticket.dto.clientobject.dict.co;

import com.nuwa.framework.cola.starter.dto.NuwaCO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "新增字典")
public class DictDataCO extends NuwaCO {

    /**
     * 字典标签
     */
    @ApiModelProperty(value = "字典字段")
    private String dictColumn;

    @ApiModelProperty(value = "字典标签")
    private String dictLabel;
    @ApiModelProperty(value = "字典键值")
    private String dictValue;
    @ApiModelProperty(value = "字典名称")
    private String remark;
}
