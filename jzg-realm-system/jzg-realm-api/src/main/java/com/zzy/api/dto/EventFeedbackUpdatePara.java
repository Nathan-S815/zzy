package com.zzy.api.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import org.springframework.web.multipart.MultipartFile;


@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class EventFeedbackUpdatePara {


    /**
     * 所属事件ID
     */
    @ApiModelProperty(value = "所属事件ID", required = true)
    private Integer eventId;

    /**
     * 事件反馈内容
     */
    @ApiModelProperty(value = "事件反馈内容", required = true)
    private String processMsg;


}
