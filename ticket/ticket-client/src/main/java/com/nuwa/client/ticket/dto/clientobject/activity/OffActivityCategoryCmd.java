package com.nuwa.client.ticket.dto.clientobject.activity;

import com.nuwa.framework.cola.starter.dto.NuwaCommand;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "下架活动分类命令")
public class OffActivityCategoryCmd extends NuwaCommand {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "活动分类id")
    private Long id;

}
