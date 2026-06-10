package com.nuwa.client.ticket.dto.clientobject.dict;

import com.nuwa.framework.cola.starter.dto.NuwaCommand;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "根据id删除字典")
public class DelDictDataCmd extends NuwaCommand {
    @ApiModelProperty(value = "字典编码（id）")
    private String dictCode;
}
