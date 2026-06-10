package com.nuwa.ticket.start.dispatch.controller.notify.douying;

import cn.hutool.json.JSONUtil;
import lombok.Data;

/**
 * 抖音回调参数
 *
 * @author hy
 */
@Data
public class DouYinPaymentNotifyParam {
    private Long timestamp;
    private String nonce;
    private String type;
    private String msg;
    private String msg_signature;

    public Body toBody(){
        return JSONUtil.toBean(this.getMsg(), Body.class);
    }

    @Data
    public static class Body {
        private String appid;
        private String cp_orderno;
        /**
         * way 字段中标识了支付渠道：2-支付宝，1-微信
         */
        private String way;
        private String cp_extra;
        /**
         * 订单来源视频对应视频 id
         */
        private String item_id;
        private String seller_uid;
        private String extra;
    }
}
