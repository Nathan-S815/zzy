package com.nuwa.client.ticket.dto.clientobject.mall;

import com.nuwa.framework.cola.starter.dto.NuwaCommand;
import io.swagger.annotations.ApiModel;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.util.List;

@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "支付回调命令")
public class MallOrderNotifyWeChatCmd extends NuwaCommand {

    private static final long serialVersionUID = 1L;

    private String promotionDetail;

    private String deviceInfo;

    private String openid;

    private String isSubscribe;

    private String subOpenid;

    private String subIsSubscribe;

    private String tradeType;

    private String bankType;

    private Integer totalFee;

    private Integer settlementTotalFee;

    private String feeType;

    private Integer cashFee;

    private String cashFeeType;

    private Integer couponFee;

    private Integer couponCount;

    private List<WxPayOrderNotifyCoupon> couponList;

    private String transactionId;

    private String outTradeNo;

    private String attach;

    private String timeEnd;

    private String version;

    @Data
    public static class WxPayOrderNotifyCoupon {
        private String couponId;
        private String couponType;
        private Integer couponFee;
    }
}
