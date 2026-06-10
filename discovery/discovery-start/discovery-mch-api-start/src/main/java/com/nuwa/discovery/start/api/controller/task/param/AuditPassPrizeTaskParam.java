package com.nuwa.discovery.start.api.controller.task.param;

import io.swagger.annotations.ApiModel;
import lombok.Data;
import lombok.experimental.Accessors;

/**
 * @author hy
 */
@Data
@Accessors(chain = true)
@ApiModel(value = "权益审核通过参数")
public class AuditPassPrizeTaskParam {
    private Long taskPrizeId;
    private Long recordId;
    private String content;
    private String pictures;
}
