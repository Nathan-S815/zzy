package com.nuwa.ticket.start.api.pubsystem.param;

import cn.hutool.json.JSONUtil;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.nuwa.framework.cola.starter.dto.NuwaCommand;
import io.swagger.annotations.ApiModel;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "VersionUploadParam 小程序基于模板上传版本")
public class VersionUploadParam extends NuwaCommand {
    private String templateId;
    private String templateVersion;
    private String appId;
    private String appVersion;
}
