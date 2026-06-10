package com.nuwa.client.zeus.api.sms;

import com.alibaba.cola.dto.SingleResponse;
import com.nuwa.client.zeus.api.merchant.MerchantClientI;
import com.nuwa.client.zeus.api.merchant.vo.ClientAppVO;
import com.nuwa.client.zeus.api.merchant.vo.MerchantSimpleVO;
import com.nuwa.client.zeus.api.sms.param.SendSmsCodeParam;
import com.nuwa.client.zeus.api.sms.param.VerifySmsCodeParam;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.List;

/**
 * SmsClientFallBack
 *
 * @author hy
 * @date 2021/4/22 13:54
 * @since 1.0.0
 */
@Component
public class SmsClientFallBack implements SmsClientI {

    @Override
    public SingleResponse<?> send(SendSmsCodeParam param) {
        return SingleResponse.buildFailure("9026", "FallBack");
    }

    @Override
    public SingleResponse<?> verify(VerifySmsCodeParam param) {
        return SingleResponse.buildFailure("9026", "FallBack");
    }
}
