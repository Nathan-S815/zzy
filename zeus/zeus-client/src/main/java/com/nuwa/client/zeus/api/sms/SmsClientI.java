package com.nuwa.client.zeus.api.sms;

import com.alibaba.cola.dto.SingleResponse;
import com.nuwa.client.zeus.api.merchant.vo.MerchantSimpleVO;
import com.nuwa.client.zeus.api.sms.param.SendSmsCodeParam;
import com.nuwa.client.zeus.api.sms.param.VerifySmsCodeParam;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.validation.Valid;
import java.util.List;


/**
 * 短信验证码接口
 *
 * @author hy
 */
@FeignClient(
        value = "zeus-client-provider",
        fallback = SmsClientFallBack.class
)
public interface SmsClientI {
    String API_PREFIX = "/sms";

    @PostMapping(API_PREFIX + "/send")
    @ResponseBody
    public SingleResponse<?> send(@RequestBody @Valid SendSmsCodeParam param);

    @PostMapping(API_PREFIX + "/verify")
    @ResponseBody
    public SingleResponse<?> verify(@RequestBody @Valid VerifySmsCodeParam param);

}
