package com.nuwa.discovery.start.api.third.sms.resp;

import lombok.Data;

/**
 * SendSmsCodeResp
 *
 * @author hy
 * @date 2021/6/9 16:21
 * @since 1.0.0
 */
@Data
public class SendSmsCodeResp {
    private String code;
    private String msg;
    private String msgId;
    private String contNum;
}
