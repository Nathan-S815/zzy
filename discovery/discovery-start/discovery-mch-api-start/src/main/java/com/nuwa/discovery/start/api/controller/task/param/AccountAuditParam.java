package com.nuwa.discovery.start.api.controller.task.param;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

/**
 * @author hy
 */
@Data
@Accessors(chain = true)
@ApiModel(value = "账号审核参数")
public class AccountAuditParam {
    private Long id;

    @ApiModelProperty("审核状态 1:审核通过 2:审核拒绝 ")
    private Integer status;
}
