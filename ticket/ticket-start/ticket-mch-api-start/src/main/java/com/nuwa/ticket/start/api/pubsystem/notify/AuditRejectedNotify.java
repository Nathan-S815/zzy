package com.nuwa.ticket.start.api.pubsystem.notify;

import cn.hutool.json.JSONUtil;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
public class AuditRejectedNotify {

    @JsonProperty("charset")
    private String charset;
    @JsonProperty("biz_content")
    private String bizContent;
    @JsonProperty("utc_timestamp")
    private String utcTimestamp;
    @JsonProperty("sign")
    private String sign;
    @JsonProperty("version")
    private String version;
    @JsonProperty("notify_id")
    private String notifyId;
    @JsonProperty("msg_method")
    private String msgMethod;
    @JsonProperty("app_id")
    private String appId;
    @JsonProperty("sign_type")
    private String signType;

    public AuditRejectedNotify.BizDTO getBizDTO() {
        return JSONUtil.toBean(bizContent, AuditRejectedNotify.BizDTO.class);
    }

    @NoArgsConstructor
    @Data
    public static class BizDTO {
        @JsonProperty("mini_app_id")
        private String miniAppId;

        @JsonProperty("mini_app_version")
        private String miniAppVersion;

        @JsonProperty("audit_reason")
        private String auditReason;
    }
}
