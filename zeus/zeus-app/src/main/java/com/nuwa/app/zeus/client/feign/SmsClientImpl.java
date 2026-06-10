package com.nuwa.app.zeus.client.feign;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.RandomUtil;
import cn.hutool.core.util.StrUtil;
import com.alibaba.cola.dto.SingleResponse;
import com.nuwa.client.zeus.api.sms.SmsClientI;
import com.nuwa.client.zeus.api.sms.param.SendSmsCodeParam;
import com.nuwa.client.zeus.api.sms.param.VerifySmsCodeParam;
import com.nuwa.client.zeus.dto.domainevent.sms.PersistentSmsCodeSucceededEvent;
import com.nuwa.infrastructure.zeus.common.event.DomainEventPublisher;
import com.nuwa.infrastructure.zeus.constant.SmsBizConstant;
import com.nuwa.infrastructure.zeus.database.sms.entity.SmsCode;
import com.nuwa.infrastructure.zeus.database.sms.service.SmsCodeService;
import com.nuwa.infrastructure.zeus.enums.ErrorEnum;
import com.nuwa.infrastructure.zeus.enums.SmsStatusEnum;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;

@Slf4j
@RestController
public class SmsClientImpl implements SmsClientI {

    @Autowired
    private DomainEventPublisher domainEventPublisher;

    @Autowired
    private SmsCodeService smsCodeService;

    @Override
    public SingleResponse<?> send(SendSmsCodeParam param) {
        boolean checkFlag = smsCodeService.lambdaQuery()
                .eq(SmsCode::getBizCode, param.getBizCode())
                .eq(SmsCode::getMobile, param.getMobile())
                .gt(SmsCode::getSendTime, DateUtil.offsetMinute(new Date(), -1))
                .count() > 0;
        if (checkFlag) {
            return ErrorEnum.VALID_CODE_TO_BUSY.buildFailure();
        }

        boolean checkFlagLimit10 = smsCodeService.lambdaQuery()
                .eq(SmsCode::getBizCode, param.getBizCode())
                .eq(SmsCode::getMobile, param.getMobile())
                .gt(SmsCode::getSendTime, DateUtil.offsetHour(new Date(), -6))
                .count() > 10;
        if (checkFlagLimit10) {
            return ErrorEnum.VALID_CODE_TO_BUSY.buildFailure();
        }

        Date timestamp = new Date();
        SmsCode sms = new SmsCode();
        sms.setTitle(param.getTitle());
        sms.setCode(param.getCode());
        sms.setContent(param.getContent());
        sms.setCreateDate(timestamp);
        sms.setMobile(param.getMobile());
        sms.setSendTime(timestamp);
        sms.setDeadTime(DateUtil.offsetSecond(timestamp, 300));
        sms.setStatus(SmsStatusEnum.UNSENT.getValue());
        sms.setSmsId(StrUtil.EMPTY);
        sms.setBizCode(param.getBizCode());
        sms.setSendType(10);
        sms.setCheckStatus(10);
        boolean insert = sms.insert();
        if (insert) {
            domainEventPublisher.publishEvent(new PersistentSmsCodeSucceededEvent(sms.getId()));
            return SingleResponse.buildSuccess();
        }
        return ErrorEnum.VALID_CODE_SEND_FAILED.buildFailure();
    }

    @Override
    public SingleResponse<?> verify(VerifySmsCodeParam param) {
        SmsCode smsCode = smsCodeService.lambdaQuery()
                .eq(SmsCode::getBizCode, param.getBizCode())
                .eq(SmsCode::getMobile, param.getMobile())
                .eq(SmsCode::getCheckStatus, 10)
                .gt(SmsCode::getDeadTime, new Date())
                .orderByDesc(SmsCode::getSendTime)
                .last("limit 1")
                .one();
        if (BeanUtil.isEmpty(smsCode) || !smsCode.getCode().equals(param.getCode())) {
            return ErrorEnum.CODE_CHECK_FAILED.buildFailure();
        }
        smsCodeService.lambdaUpdate()
                .set(SmsCode::getCheckStatus, 20)
                .eq(SmsCode::getCode, param.getCode())
                .eq(SmsCode::getMobile, param.getMobile())
                .update();
        return SingleResponse.buildSuccess();
    }
}
