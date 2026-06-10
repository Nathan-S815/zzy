package com.nuwa.client.ticket.dto.clientobject.callcenter.center.co;

import com.nuwa.framework.cola.starter.dto.NuwaCO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "修改呼叫中心")
public class UpdateCallCenterCO  extends NuwaCO {

    @ApiModelProperty(value = "主键id")
    private Long id;

    @ApiModelProperty(value = "名称")
    private String name;

    /**
     * 电话
     */
    @ApiModelProperty(value = "电话")
    private String phone;

    /**
     * 工作时间
     */
    @ApiModelProperty(value = "工作时间")
    private String workTime;
}
