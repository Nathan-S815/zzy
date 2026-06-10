package com.nuwa.ticket.start.api.controller.appconf.param;

import com.nuwa.framework.cola.starter.dto.NuwaCommand;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

/**
 * @author hy
 */
@Data
@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "ModifySearchOrderNumConfParam 修改搜索排序字段")
public class BatchModifySearchOrderNumConfParam extends NuwaCommand {

    private List<SearchOrderNumDTO> orderNumItems;

}
