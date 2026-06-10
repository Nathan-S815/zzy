package com.nuwa.client.ticket.dto.clientobject.activity;

import com.nuwa.framework.cola.starter.dto.NuwaCommand;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "新增活动报名命令")
public class CreateActivityApplyV2Cmd extends NuwaCommand {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "活动ID", required = true)
    @NotNull(message = "活动ID不能为空")
    private Long activityId;

    @ApiModelProperty(value = "联系人姓名", required = true)
    @NotBlank(message = "联系人姓名不能为空")
    private String contactsName;

    @ApiModelProperty(value = "联系人电话", required = true)
    @NotBlank(message = "联系人电话不能为空")
    private String contactsMobile;

    @ApiModelProperty(value = "人数", required = true)
    @NotNull(message = "人数不能为空")
    @Min(value = 0, message = "报名人数必须大于0")
    private Integer peopleNum;

    @ApiModelProperty(value = "身份证号")
    @NotBlank(message = "身份证号不能为空")
    private String idCard;

    private Long appId;

    private Long mchId;

}
