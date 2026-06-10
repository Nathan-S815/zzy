package com.nuwa.client.ticket.api.alipaydata;

import com.alibaba.cola.dto.SingleResponse;
import com.nuwa.client.ticket.api.pubsystem.PubSystemClientFallBack;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;


/**
 * 支付宝数据同步
 *
 * @author hy
 */
@FeignClient(
        value = "ticket-service-${spring.profiles.active}",
        fallback = AlipayDataClientFallBack.class
)
public interface AlipayDataClientI {
    String API_PREFIX = "/alipyaData";

    /**
     * 同步产品数据
     *
     * @return SingleResponse
     */
    @PostMapping(API_PREFIX + "/takeAndSyncProduct")
    @ResponseBody
    public SingleResponse<?> takeAndSyncProduct();

    /**
     * 同步订单数据
     *
     * @return SingleResponse
     */
    @PostMapping(API_PREFIX + "/takeAndSyncOrder")
    @ResponseBody
    public SingleResponse<?> takeAndSyncOrder();

}
