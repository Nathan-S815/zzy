package com.nuwa.infrastructure.ticket.third.paychannel.miniapp;

import cn.hutool.core.date.DateUtil;
import lombok.Data;

import java.util.Date;

/**
 * RequestHead
 *
 * @author hy
 * @date 2020/10/29 14:18
 * @since 1.0.0
 */
@Data
public class RequestHead {
    private String mchId;
    private String timestamp;

    public RequestHead(String mchId, Date timestamp) {
        this.mchId = mchId;
        this.timestamp = DateUtil.format(timestamp, "yyyy-MM-dd HH:mm:ss");
    }

    private String sign;
    private String charset = "utf-8";
    private String sign_type = "MD5";
    private String format = "json";
    private String version = "1.0";
}
