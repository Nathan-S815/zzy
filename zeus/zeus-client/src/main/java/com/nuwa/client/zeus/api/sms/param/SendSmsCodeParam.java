package com.nuwa.client.zeus.api.sms.param;

import lombok.Data;

@Data
public class SendSmsCodeParam {
    private String mobile;
    private String bizCode;
    private String title;
    private String content;
    private String code;
}
