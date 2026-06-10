package com.nuwa.ticket.start.api.pubsystem.notify;

import cn.hutool.json.JSONUtil;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@NoArgsConstructor
@Data
public class CommerceDataScenicAuditNotify {

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

    public CommerceDataScenicAuditNotify.BizDTO getBizDTO() {
        return JSONUtil.toBean(bizContent, CommerceDataScenicAuditNotify.BizDTO.class);
    }

    @NoArgsConstructor
    @Data
    public static class BizDTO {
        /**
         * 支付宝内部每个景区的唯一ID
         */
        @JsonProperty("scenic_id")
        private String scenicId;

        /**
         * 服务商负责的景区所在小程序的APP ID
         */
        @JsonProperty("scenic_app_id")
        private String scenicAppId;

        /**
         * 服务商景区ID
         */
        @JsonProperty("outer_id_isv")
        private String outerIdIsv;

        /**
         * 支付宝域内维护的景区外部id
         */
        @JsonProperty("outer_id_zfb")
        private String outerIdZfb;

        /**
         * 内外id映射关系创建时间
         */
        @JsonProperty("outer_id_map_gmt_create")
        private Date outerIdMapGmtCreate;

        /**
         * 审核结果
         */
        @JsonProperty("audit_status")
        private String auditStatus;

        /**
         * 审核信息
         */
        @JsonProperty("audit_msg")
        private String auditMsg;
    }
}
