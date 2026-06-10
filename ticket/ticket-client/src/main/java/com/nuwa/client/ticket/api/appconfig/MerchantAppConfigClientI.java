package com.nuwa.client.ticket.api.appconfig;

import com.alibaba.cola.dto.SingleResponse;
import com.nuwa.client.ticket.api.appconfig.param.*;
import com.nuwa.client.ticket.dto.vo.MerchantAppPayInfoClientVO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;


/**
 * 获取商户小程序配置信息
 *
 * @author hy
 */
@FeignClient(
        value = "ticket-service-${spring.profiles.active}",
        fallback = MerchantAppConfigClientIFallBack.class
)
public interface MerchantAppConfigClientI {
    String API_PREFIX = "/merchant/appconfig";

    /**
     * 获取支付参数
     *
     * @param param ChannelPaymentSuccessNotifyParam
     * @return SingleResponse
     */
    @PostMapping(API_PREFIX + "/getPayInfoByOutAppId")
    @ResponseBody
    public SingleResponse<MerchantAppPayInfoClientVO> getPayInfoByOutAppId(@RequestBody GetAppConfigByOutAppIdParam param);

}
