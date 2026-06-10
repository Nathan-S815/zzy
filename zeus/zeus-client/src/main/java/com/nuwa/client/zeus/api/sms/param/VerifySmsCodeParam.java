package com.nuwa.client.zeus.api.sms.param;

import lombok.Data;

@Data
public class VerifySmsCodeParam {
    private String mobile;
    private String code;
    private String bizCode;
}
