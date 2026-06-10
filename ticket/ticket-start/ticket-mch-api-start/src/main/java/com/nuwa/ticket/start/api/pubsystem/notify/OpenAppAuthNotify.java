package com.nuwa.ticket.start.api.pubsystem.notify;

import cn.hutool.json.JSONUtil;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
public class OpenAppAuthNotify {

    @JsonProperty("charset")
    private String charset;
    @JsonProperty("biz_content")
    private String bizContent;
    @JsonProperty("notify_time")
    private String notifyTime;
    @JsonProperty("sign")
    private String sign;
    @JsonProperty("version")
    private String version;
    @JsonProperty("notify_id")
    private String notifyId;
    @JsonProperty("notify_type")
    private String notifyType;
    @JsonProperty("auth_app_id")
    private String authAppId;
    @JsonProperty("app_id")
    private String appId;
    @JsonProperty("sign_type")
    private String signType;
    @JsonProperty("status")
    private String status;

    public BizDTO getBizDTO() {
        return JSONUtil.toBean(bizContent, BizDTO.class);
    }

    @NoArgsConstructor
    @Data
    public static class BizDTO {

        @JsonProperty("notify_context")
        private NotifyContextDTO notifyContext;
        @JsonProperty("detail")
        private DetailDTO detail;
        @JsonProperty("error")
        private ErrorDTO error;

        @NoArgsConstructor
        @Data
        public static class NotifyContextDTO {
            @JsonProperty("trigger")
            private String trigger;
        }

        @NoArgsConstructor
        @Data
        public static class DetailDTO {
            @JsonProperty("app_auth_token")
            private String appAuthToken;
            @JsonProperty("user_id")
            private String userId;
            @JsonProperty("re_expires_in")
            private Integer reExpiresIn;
            @JsonProperty("auth_time")
            private Long authTime;
            @JsonProperty("app_refresh_token")
            private String appRefreshToken;
        }

        @NoArgsConstructor
        @Data
        public static class ErrorDTO {
        }
    }

    /**
     *{
     *     "charset": "UTF-8",
     *     "biz_content": "{\"notify_context\":{\"trigger\":\"appstore\"},\"detail\":{\"app_auth_token\":\"202205BBc07be5924e9d487086f70bfbefad6X68\",\"user_id\":\"2088731356422683\",\"re_expires_in\":32140800,\"auth_time\":1651894819272,\"app_refresh_token\":\"202205BB2a45fd8367074d43aff5722663b3dX68\",\"auth_app_id\":\"2021002123650074\",\"app_id\":\"2021002189618144\",\"expires_in\":31536000,\"app_auth_code\":\"9dfdb0c902af4d5d90998836df0c3B68\"},\"error\":{}}",
     *     "notify_time": "2022-05-07 11:40:19",
     *     "sign": "bXuuTmasSR6vvHyjEgSoQsw3Mz+nSCyxIO/pHzZYJNLkyO7kgocQXgwI682vmn/Dkvbd0veaDFwtW505n7BwK44DepPmp5+CHoUzMqzUjTtnxZ9xVFSXFoCRSrYsPg/levCl7mnLC7/Z2krjMX54olhuYblnGmCYv01U7p27FtL8tgtrc7dDpkE0R5orjMtP8krxg606YbeLc8SYkKdkmOjzIwOHaMAIhgM7vJnI3jYYTxLhCI8wOufWxbUeO9d2KDoeeMhQi6E8Eis2Ux/Bgwf3AHkCp4jGOhQLCfiX00gaM8Nl2Qq0z/GiX/KPDPAj/IIppk4oaMFkjhMoO3NgkA==",
     *     "version": "1.0",
     *     "notify_id": "2022050700222114019009211430200303",
     *     "notify_type": "open_app_auth_notify",
     *     "auth_app_id": "2021002123650074",
     *     "app_id": "2021002189618144",
     *     "sign_type": "RSA2",
     *     "status": "execute_auth"
     * }
     */
}
