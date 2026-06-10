package com.nuwa.ticket.start.api.controller.product.param;

import com.nuwa.framework.cola.starter.dto.NuwaCommand;
import com.nuwa.ticket.start.api.controller.dto.*;
import io.swagger.annotations.ApiModel;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.NotNull;

/**
 * @author hy
 */
@Data
@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "SaveScenicspotProductParam 保存产品参数")
public class SaveScenicspotProductParam extends NuwaCommand {
    /**
     * 产品基础信息
     */
    @NotNull(message = "产品基础信息不能为空")
    private ProductBaseInfoDTO baseInfo;

    /**
     * 产品预订规则配置
     */
    @NotNull(message = "产品预订规则配置不能为空")
    private ProductBookRuleConfigDTO bookConfig;

    /**
     * 产品核销规则配置
     */
    @NotNull(message = "产品核销规则配置不能为空")
    private ProductVerificationRuleConfigDTO verificationConfig;

    /**
     * 退款规则
     */
    @NotNull(message = "退款规则配置不能为空")
    private ProductRefundRuleConfigDTO refundConfig;

    /**
     * 有效期设置
     */
    @NotNull(message = "有效期设置不能为空")
    private ProductValidPeriodConfigDTO validPeriodConfig;

}
