package com.nuwa.ticket.start.server.client.feign;

import com.alibaba.cola.dto.SingleResponse;
import com.nuwa.client.ticket.api.appconfig.MerchantAppConfigClientI;
import com.nuwa.client.ticket.api.appconfig.param.GetAppConfigByOutAppIdParam;
import com.nuwa.client.ticket.dto.vo.MerchantAppPayInfoClientVO;
import com.nuwa.client.ticket.dto.vo.PaymentConfigVO;
import com.nuwa.infrastructure.ticket.database.mchconfig.MerchantAppPayConf;
import com.nuwa.infrastructure.ticket.database.mchconfig.entity.MerchantAppBaseConf;
import com.nuwa.infrastructure.ticket.database.mchconfig.service.MerchantAppBaseConfService;
import com.nuwa.infrastructure.ticket.database.mchconfig.service.MerchantAppPayConfService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Objects;

/**
 * MerchantAppConfigClientI实现
 *
 * @author hy
 */
@Slf4j
@RestController
public class MerchantAppConfigClientImpl implements MerchantAppConfigClientI {

    @Autowired
    private MerchantAppBaseConfService merchantAppBaseConfService;

    @Autowired
    private MerchantAppPayConfService merchantAppPayConfService;

    @Override
    public SingleResponse<MerchantAppPayInfoClientVO> getPayInfoByOutAppId(@RequestBody GetAppConfigByOutAppIdParam param) {
        MerchantAppBaseConf appBaseConf = merchantAppBaseConfService.lambdaQuery()
                .eq(MerchantAppBaseConf::getOutAppId, param.getOutAppId())
                .eq(MerchantAppBaseConf::getMchId, param.getMchId())
                .one();
        if (Objects.isNull(appBaseConf)) {
            return SingleResponse.buildFailure("9632", "没找到支付信息");
        }
        MerchantAppPayInfoClientVO vo = new MerchantAppPayInfoClientVO();
        BeanUtils.copyProperties(appBaseConf, vo);
        MerchantAppPayConf merchantAppPayConf = merchantAppPayConfService.lambdaQuery()
                .eq(MerchantAppPayConf::getMchId, param.getMchId())
                .eq(MerchantAppPayConf::getOutAppId, param.getOutAppId())
                .one();
        if (Objects.isNull(merchantAppPayConf)) {
            return SingleResponse.buildFailure("9632", "没找到支付信息");
        }
        PaymentConfigVO paymentConfigVO = new PaymentConfigVO();
        BeanUtils.copyProperties(merchantAppPayConf, paymentConfigVO);
        vo.setPaymentConfig(paymentConfigVO);
        return SingleResponse.of(vo);
    }
}
