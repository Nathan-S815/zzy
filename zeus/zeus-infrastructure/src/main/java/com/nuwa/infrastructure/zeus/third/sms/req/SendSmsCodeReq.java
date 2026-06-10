package com.nuwa.infrastructure.zeus.third.sms.req;

import lombok.Data;

/**
 * SendSmsCodeReq
 *
 * @author hy
 * @date 2021/6/9 16:19
 * @since 1.0.0
 */
@Data
public class SendSmsCodeReq {
    private String mobile;
    private String content;
}
