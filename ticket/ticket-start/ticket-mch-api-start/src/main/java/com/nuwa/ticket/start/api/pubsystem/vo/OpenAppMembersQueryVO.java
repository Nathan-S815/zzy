package com.nuwa.ticket.start.api.pubsystem.vo;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@NoArgsConstructor
@Data
public class OpenAppMembersQueryVO {

    @JsonProperty("code")
    private String code;
    @JsonProperty("msg")
    private String msg;
    @JsonProperty("app_member_info_list")
    private List<AppMemberInfoListDTO> appMemberInfoList;

    @NoArgsConstructor
    @Data
    public static class AppMemberInfoListDTO {
        @JsonProperty("user_id")
        private String userId;
        @JsonProperty("nick_name")
        private String nickName;
        @JsonProperty("portrait")
        private String portrait;
        @JsonProperty("status")
        private String status;
        @JsonProperty("gmt_join")
        private String gmtJoin;
        @JsonProperty("logon_id")
        private String logonId;
        @JsonProperty("gmt_invite")
        private String gmtInvite;
        @JsonProperty("role")
        private String role;
    }
}
