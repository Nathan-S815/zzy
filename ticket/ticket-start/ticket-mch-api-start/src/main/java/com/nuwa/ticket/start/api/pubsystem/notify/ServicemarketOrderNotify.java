package com.nuwa.ticket.start.api.pubsystem.notify;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
@ApiModel(value = "服务市场订阅通知")
public class ServicemarketOrderNotify {

    @JsonProperty("item_code")
    private String itemCode;
    @JsonProperty("charset")
    private String charset;
    @JsonProperty("sign")
    private String sign;
    @JsonProperty("order_time")
    private String orderTime;
    @JsonProperty("title")
    private String title;
    @JsonProperty("specifications")
    private String specifications;
    @JsonProperty("notify_id")
    private String notifyId;
    @JsonProperty("merchant_pid")
    private String merchantPid;
    @JsonProperty("notify_type")
    private String notifyType;
    @JsonProperty("order_item_num")
    private String orderItemNum;
    @JsonProperty("app_id")
    private String appId;
    @JsonProperty("sign_type")
    private String signType;
    @JsonProperty("isv_ticket")
    private String isvTicket;
    @JsonProperty("timestamp")
    private String timestamp;
    @JsonProperty("notify_time")
    private String notifyTime;
    @JsonProperty("commodity_order_id")
    private String commodityOrderId;
    @JsonProperty("quantity")
    private String quantity;
    @JsonProperty("total_price")
    private String totalPrice;
    @JsonProperty("method")
    private String method;
    @JsonProperty("miniapp_release_bundle")
    private String miniappReleaseBundle;
    @JsonProperty("version")
    private String version;
    @JsonProperty("package_count")
    private String packageCount;
    @JsonProperty("period_day")
    private String periodDay;
    @JsonProperty("phone")
    private String phone;
    @JsonProperty("name")
    private String name;
    @JsonProperty("consumer_miniAppId")
    private String consumerMiniappid;
    @JsonProperty("contactor")
    private String contactor;

    /**
     * {
     *     "item_code": "AM010301000000105442",
     *     "charset": "UTF-8",
     *     "sign": "WAefFHMHAp3S3gJtCj1iY+pHETVTI1mD4iu50g/lOVmX0azn8lJ1U8I89eA2qkIgQbuPzsnptQ0mJ2ugKzv4+bfEMxnGUoZbg4SNXo7RaFKqQ+GyT7zW1iw18dHxw9mHXNq446CUDPt1WLaRUtav5mptZXgnZRmaz0rOw4INKkEu5Upke9A1m5fI/X66nL8xVaCQ91BDJV5GMGhkgrmPAsiuR0+86xR5CFvmOsbSXnyb5uabXbnyoCYFsSAcgiG26lGthZHsYiDsNwarloNbb64x6N1QJdE5XsFRPbPXUPOPxaDZMrmKv+zavOCRxhbJgLtWUek0Cm82soldKPa4mw==",
     *     "order_time": "2022-05-07 11:40:18",
     *     "title": "美客引擎模版",
     *     "specifications": "标准版",
     *     "notify_id": "2022050700222114018083401442873617",
     *     "merchant_pid": "2088731356422683",
     *     "notify_type": "servicemarket_order_notify",
     *     "order_item_num": "1",
     *     "app_id": "2021002189618144",
     *     "sign_type": "RSA2",
     *     "isv_ticket": "",
     *     "timestamp": "2022-05-07 11:40:18",
     *     "notify_time": "2022-05-07 11:40:18",
     *     "commodity_order_id": "202205070000000008344772",
     *     "quantity": "1",
     *     "total_price": "0.00",
     *     "method": "alipay.open.servicemarket.order.notify",
     *     "miniapp_release_bundle": "1032",
     *     "version": "1.0",
     *     "package_count": "0",
     *     "period_day": "30",
     *     "phone": "18768138549",
     *     "name": "杭*************司",
     *     "consumer_miniAppId": "2021002123650074",
     *     "contactor": "鲍佳璐"
     * }
     */
}
