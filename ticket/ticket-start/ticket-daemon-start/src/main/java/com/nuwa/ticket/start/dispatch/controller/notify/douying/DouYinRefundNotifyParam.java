package com.nuwa.ticket.start.dispatch.controller.notify.douying;

import cn.hutool.json.JSONUtil;
import lombok.Data;

/**
 * 抖音回调参数
 *
 * @author hy
 */
@Data
public class DouYinRefundNotifyParam {
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
        private String cp_refundno;
        private String cp_extra;
        /**
         * 退款状态 PROCESSING-处理中|SUCCESS-成功|FAIL-失败
         */
        private String status;

        private Integer refund_amount;
    }
}
