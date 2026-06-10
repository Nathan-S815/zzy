package com.nuwa.client.ticket.dto.clientobject.dict.co;

import com.nuwa.framework.cola.starter.dto.NuwaCO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "获取字段对应的值")
public class DictGetByColumnCO extends NuwaCO {

    @ApiModelProperty(value = "字典字段")
    private String dictColumn;
}
