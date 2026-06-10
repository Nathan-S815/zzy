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
@ApiModel(value = "商户新增问题反馈")
public class AddProblemFeedbackCmd extends NuwaCommand {

    @ApiModelProperty("商户信息")
    private String mchName;

    @ApiModelProperty("注册手机号")
    private String registPhone;

    @ApiModelProperty("联系人")
    private String contactPeople;

    @ApiModelProperty("联系电话")
    private String contactPhone;

    @ApiModelProperty("反馈内容")
    private String contentBack;

    @ApiModelProperty("备注")
    private String remark;

    @ApiModelProperty("图片")
    private String image;
}
