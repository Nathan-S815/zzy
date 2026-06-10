package com.nuwa.client.ticket.dto.clientobject.callcenter.talk.co;

import com.nuwa.framework.cola.starter.dto.NuwaCO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "修改会话管理")
public class UpdateTalkManageCO extends NuwaCO {

    @ApiModelProperty(value = "主键id")
    private Long id;
    /**
     * 提示语
     */
    @ApiModelProperty(value = "提示语")
    private String title;

    /**
     * 问题id
     */
    @ApiModelProperty(value = "问题id")
    private String problems;

    /**
     * 帐号启用状态  10启用11禁用
     */
    @ApiModelProperty(value = "启用状态  10启用11禁用")
    private Integer enableStatus;
}
