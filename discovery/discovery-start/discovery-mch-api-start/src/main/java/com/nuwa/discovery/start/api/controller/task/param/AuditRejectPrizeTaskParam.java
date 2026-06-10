package com.nuwa.discovery.start.api.controller.task.param;

import io.swagger.annotations.ApiModel;
import lombok.Data;
import lombok.experimental.Accessors;

/**
 * @author hy
 */
@Data
@Accessors(chain = true)
@ApiModel(value = "权益审核拒绝参数")
public class AuditRejectPrizeTaskParam {
    private Long recordId;
    private Long taskPrizeId;
    private String content;
    private String pictures;
}
