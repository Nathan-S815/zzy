package com.nuwa.client.ticket.api.alipaydata;

import com.alibaba.cola.dto.SingleResponse;
import com.nuwa.client.ticket.api.pubsystem.PubSystemClientI;
import org.springframework.stereotype.Component;

/**
 * AlipayDataClientFallBack
 *
 * @author hy
 * @date 2021/4/22 13:54
 * @since 1.0.0
 */
@Component
public class AlipayDataClientFallBack implements AlipayDataClientI {
    @Override
    public SingleResponse<?> takeAndSyncProduct() {
        return SingleResponse.buildFailure("9999", "FallBack");
    }

    @Override
    public SingleResponse<?> takeAndSyncOrder() {
        return SingleResponse.buildFailure("9999", "FallBack");
    }
}
