package com.nuwa.client.ticket.dto.clientobject.dict.co;

import com.nuwa.framework.cola.starter.dto.NuwaCO;
import io.swagger.annotations.ApiModel;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "字典列表")
public class DictDataListCO extends NuwaCO {
}
