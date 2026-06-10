package com.nuwa.client.ticket.dto.clientobject.complaint.co;

import com.nuwa.framework.cola.starter.dto.NuwaCO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * PaidOrderCO 订单支付完成
 *
 * @author hy
 * @date 2021/4/16 13:54
 * @since 1.0.0
 */
@Data
@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "处理投诉")
public class AuditComplaintCO extends NuwaCO {

    @ApiModelProperty(value = "处理结果(101调解成功 102调解失败 103不予受理 104和解 105其他)")
    private Integer states;

    @ApiModelProperty(value = "remark(100字以内)")
    private String remark;

    @ApiModelProperty(value = "处理人")
    private String auditUserName;

}
