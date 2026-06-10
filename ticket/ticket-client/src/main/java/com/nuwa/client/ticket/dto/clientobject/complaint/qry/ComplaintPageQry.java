package com.nuwa.client.ticket.dto.clientobject.complaint.qry;

import com.nuwa.framework.cola.starter.dto.NuwaPageQry;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;


/**
 * <pre>
 * 用户投诉 PageQry参数对象
 * </pre>
 *
 * @author huyonghack@163.com
 * @date 2021-05-31
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "用户投诉PageQry")
public class ComplaintPageQry extends NuwaPageQry {
    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "处理状态(101未处理 102已处理)")
    private Integer auditStatus;

    @ApiModelProperty(value = "处理结果(101调解成功 102调解失败 103不予受理 104和解 105其他)")
    private String auditResult;

    @ApiModelProperty(value = "用户名")
    private String userName;

    private Long appId;
}
