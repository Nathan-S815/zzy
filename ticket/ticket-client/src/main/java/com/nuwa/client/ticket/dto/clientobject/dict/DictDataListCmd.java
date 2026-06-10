package com.nuwa.client.ticket.dto.clientobject.dict;


import com.nuwa.client.ticket.dto.clientobject.dict.co.DictDataListCO;
import com.nuwa.framework.cola.starter.dto.NuwaCommand;
import io.swagger.annotations.ApiModel;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "字典列表")
public class DictDataListCmd extends NuwaCommand {

    private static final long serialVersionUID = 1L;
    private DictDataListCO dictDataListCO;
}
