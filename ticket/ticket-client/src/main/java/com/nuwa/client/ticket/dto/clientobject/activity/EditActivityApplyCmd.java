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
@ApiModel(value = "修改活动报名命令")
public class EditActivityApplyCmd extends NuwaCommand {

    private static final long serialVersionUID = 1L;

    private Long id;

    @ApiModelProperty(value = "活动ID")
    private Long activityId;

    @ApiModelProperty(value = "联系人姓名")
    private String contactsName;

    @ApiModelProperty(value = "联系人电话")
    private String contactsMobile;

    @ApiModelProperty(value = "人数")
    private Integer peopleNum;

    @ApiModelProperty(value = "身份证号")
    private String idCard;

    private Long appId;
}
