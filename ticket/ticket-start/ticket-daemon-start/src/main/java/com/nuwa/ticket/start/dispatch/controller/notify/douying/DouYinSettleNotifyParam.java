package com.nuwa.ticket.start.dispatch.controller.notify.douying;

import cn.hutool.json.JSONUtil;
import lombok.Data;

import java.util.Date;

/**
 * 抖音回调参数
 *
 * @author hy
 */
@Data
public class DouYinSettleNotifyParam {
    private Long timestamp;
    private String nonce;
    private String type;
    private String msg;
    private String msg_signature;

    public Body toBody() {
        return JSONUtil.toBean(this.getMsg(), Body.class);
    }

    @Data
    public static class Body {
        private String appid;
        private String cp_settle_no;
        /**
         * 分账状态，PROCESSING-处理中|SUCCESS-成功|FAIL-失败
         */
        private String status;

        /**
         * 该笔交易分账环境收取的手续费金额
         */
        private String rake;
        /**
         * way 字段中标识了支付渠道：2-支付宝，1-微信
         */
        private String way;
        private String cp_extra;
        private String extra;

        private Date settled_at;
    }
}
