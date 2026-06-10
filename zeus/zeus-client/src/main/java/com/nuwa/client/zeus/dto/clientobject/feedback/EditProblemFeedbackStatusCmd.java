package com.nuwa.client.zeus.dto.clientobject.feedback;

import com.nuwa.framework.cola.starter.dto.NuwaCommand;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "商户问题反馈修改状态和备注")
public class EditProblemFeedbackStatusCmd extends NuwaCommand {
    @ApiModelProperty(value = "问题id")
    private Long id;

    @ApiModelProperty("状态（1-已提交，2-已完结）")
    private Integer status;

    @ApiModelProperty("备注")
    private String remark;
}
