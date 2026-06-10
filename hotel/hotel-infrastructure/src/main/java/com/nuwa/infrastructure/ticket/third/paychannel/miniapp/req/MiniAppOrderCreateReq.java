package com.nuwa.infrastructure.ticket.third.paychannel.miniapp.req;

import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.nuwa.infrastructure.ticket.third.paychannel.miniapp.RequestHead;
import com.nuwa.infrastructure.ticket.third.paychannel.miniapp.util.SignUtils;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

import java.util.HashMap;
import java.util.Objects;

/**
 * WxMiniOrderCreateReq 微信小程序下单参数
 *
 * @author hy
 * @date 2020/10/29 13:51
 * @since 1.0.0
 */
@Slf4j
@Data
public class MiniAppOrderCreateReq {

    private RequestHead head;

    private Body body;

    public String buildSign(String secretKey) {
        HashMap<String, String> needSignParams = new HashMap<String, String>();
        JSONObject jsonObject = JSONUtil.parseObj(this.getBody());
        jsonObject.forEach((k, v) -> {
            if (!Objects.isNull(v)) {
                needSignParams.put(k, v.toString());
            }
        });
        needSignParams.put("charset", head.getCharset());
        needSignParams.put("format", head.getFormat());
        needSignParams.put("sign_type", head.getSign_type());
        needSignParams.put("timestamp", head.getTimestamp());
        needSignParams.put("version", head.getVersion());
        needSignParams.put("mchId", head.getMchId());
        try {
            return SignUtils.sign(needSignParams, null, secretKey);
        } catch (Exception ex) {
            log.error("获取签名出错", ex
            );
        }
        return null;
    }

    @Data
    public static class Body {


        /**
         * appId不能为空
         */
        private String appId;

        /**
         * 商户订单号
         */
        private String orderNo;

        /**
         * 订单金额
         */
        private Long totalAmount;

        /**
         * 订单标题
         */
        private String subject;

        /**
         * 订单描述
         */
        private String body;

        /**
         * 订单超时时间
         */
        private String txnTimeOut;

        /**
         * 支付宝/微信 buyerId
         */
        private String buyerId;

        /**
         * 异步回调地址
         */
        private String notifyUrl;

        /**
         * 客户端Ip
         */
        private String clientIp = "127.0.0.1";

        /**
         * 租户id
         */
        private String tenementId;

        private String extend;
    }
}
