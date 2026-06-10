package com.nuwa.ticket.start.api.pubsystem.util;

import com.alipay.api.AlipayClient;
import com.alipay.api.DefaultAlipayClient;
import com.nuwa.infrastructure.ticket.database.pubsystem.entity.PsAlipayConfig;

public class AlipayClientWrap {
     public static AlipayClient getAlipayClient(PsAlipayConfig config){
         String prikey = config.getPrivateKey(),
                 channelPubKey = config.getAlipayPublicKey();
         return new DefaultAlipayClient("https://openapi.alipay.com/gateway.do", config.getAppId(), prikey, "json", "utf-8", channelPubKey, "RSA2");
     }
}
