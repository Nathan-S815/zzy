package com.nuwa.client.ticket.api.appconfig;

import com.alibaba.cola.dto.SingleResponse;
import com.nuwa.client.ticket.api.appconfig.param.*;
import com.nuwa.client.ticket.dto.vo.MerchantAppPayInfoClientVO;
import org.springframework.stereotype.Component;

/**
 * OrderClientFallBack
 *
 * @author hy
 * @date 2021/4/22 13:54
 * @since 1.0.0
 */
@Component
public class MerchantAppConfigClientIFallBack implements MerchantAppConfigClientI {

    @Override
    public SingleResponse<MerchantAppPayInfoClientVO> getPayInfoByOutAppId(GetAppConfigByOutAppIdParam param) {
        return SingleResponse.buildFailure("9999", "FallBack");
    }
}
