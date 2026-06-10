package com.nuwa.infrastructure.ticket.third.paychannel.douyin.resp;

import lombok.Data;

/**
 * @author hy
 */
@Data
public class Code2SessionResp {
    private String err_no;
    private String err_tips;

    private Data data;

    @lombok.Data
    public static class Data{
        private String session_key;
        private String openid;
        private String anonymous_openid;
        private String unionid;
    }
}
