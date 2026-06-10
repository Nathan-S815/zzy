package com.nuwa.discovery.start.api.handle;

import cn.hutool.json.JSONUtil;
import com.nuwa.client.discovery.dto.domainevent.UserPrizeSubmitEvent;
import com.nuwa.discovery.start.api.constants.SmsStatusEnum;
import com.nuwa.discovery.start.api.event.BindMobileSendCodeEvent;
import com.nuwa.discovery.start.api.third.ChannelResult;
import com.nuwa.discovery.start.api.third.sms.ZhuTongSmsHttpSender;
import com.nuwa.discovery.start.api.third.sms.req.SendSmsCodeReq;
import com.nuwa.discovery.start.api.third.sms.resp.SendSmsCodeResp;
import com.nuwa.framework.cola.starter.event.AbstractEventHandler;
import com.nuwa.infrastructure.discovery.database.user.entity.SmsCode;
import com.nuwa.infrastructure.discovery.database.user.service.SmsCodeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

/**
 * BindMobileSendCodeEventHandler 绑定手机号短信验证码事件处理
 *
 * @author hy
 * @date 2021/5/2 15:16
 * @since 1.0.0
 */
@Slf4j
@Component
public class BindMobileSendCodeEventHandler /*extends AbstractEventHandler<BindMobileSendCodeEvent>*/ {

    @Autowired
    private SmsCodeService smsCodeService;

    @EventListener(BindMobileSendCodeEvent.class)
    @Async
    public void handle(BindMobileSendCodeEvent event) {
        log.info("event:{}", JSONUtil.toJsonStr(event));
        Long smsId = event.getSmsId();
        SmsCode smsCode = smsCodeService.getById(smsId);

        SendSmsCodeReq req = new SendSmsCodeReq();
        req.setContent(smsCode.getContent());
        req.setMobile(smsCode.getMobile());
        ChannelResult<SendSmsCodeResp> sendResult = ZhuTongSmsHttpSender.sendSmsCode(req);
        if (sendResult.isSuccessful()) {
            smsCodeService.lambdaUpdate()
                    .set(SmsCode::getSmsId, sendResult.getData().getMsgId())
                    .set(SmsCode::getStatus, SmsStatusEnum.SENT.getValue())
                    .eq(SmsCode::getId, smsCode.getId())
                    .update();
        } else {
            smsCodeService.lambdaUpdate()
                    .set(SmsCode::getSmsId, sendResult.getData().getMsgId())
                    .set(SmsCode::getErrMsg, sendResult.getData().getMsg())
                    .set(SmsCode::getStatus, SmsStatusEnum.FAILURE.getValue())
                    .eq(SmsCode::getId, smsCode.getId())
                    .update();
        }
        log.info("send sms content:{}", smsCode.getContent());
    }
}
