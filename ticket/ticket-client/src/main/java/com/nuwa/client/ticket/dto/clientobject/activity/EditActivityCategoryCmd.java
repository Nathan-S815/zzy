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
@ApiModel(value = "修改活动分类命令")
public class EditActivityCategoryCmd extends NuwaCommand {

    private static final long serialVersionUID = 1L;

    private Long id;

    @ApiModelProperty(value = "分类名称")
    private String categoryName;

    private Long appId;
}
