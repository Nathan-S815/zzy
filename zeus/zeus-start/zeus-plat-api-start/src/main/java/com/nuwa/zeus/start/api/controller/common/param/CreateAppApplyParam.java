package com.nuwa.zeus.start.api.controller.common.param;

import com.baomidou.mybatisplus.annotation.Version;
import com.nuwa.framework.cola.starter.dto.NuwaCommand;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "应用申请")
public class CreateAppApplyParam extends NuwaCommand {
    @ApiModelProperty("应用名称")
    private String appName;

    @ApiModelProperty("申请企业名称")
    private String applyCompanyName;

    @ApiModelProperty("申请企业类型")
    private String applyCompanyType;

    @ApiModelProperty("申请企业行业")
    private String applyCompanyIndustry;

    @ApiModelProperty("联系人")
    private String linkman;

    @ApiModelProperty("企业痛点")
    private String applyContent;

    @ApiModelProperty("其他应用需求")
    private String otherContent;

    @ApiModelProperty("联系电话")
    private String linkMobile;
}
