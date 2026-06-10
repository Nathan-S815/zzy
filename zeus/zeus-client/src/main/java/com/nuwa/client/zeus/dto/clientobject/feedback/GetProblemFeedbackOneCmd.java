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
@ApiModel(value = "根据id获取问题详情")
public class GetProblemFeedbackOneCmd extends NuwaCommand {
    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "问题id")
    private Long id;
}
