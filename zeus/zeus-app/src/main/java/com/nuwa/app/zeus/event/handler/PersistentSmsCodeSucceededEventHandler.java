package com.nuwa.app.zeus.event.handler;

import cn.hutool.json.JSONUtil;
import com.nuwa.client.zeus.dto.domainevent.sms.LoginSucceededEvent;
import com.nuwa.client.zeus.dto.domainevent.sms.PersistentSmsCodeSucceededEvent;
import com.nuwa.infrastructure.zeus.database.sms.entity.SmsCode;
import com.nuwa.infrastructure.zeus.database.sms.service.SmsCodeService;
import com.nuwa.infrastructure.zeus.enums.SmsStatusEnum;
import com.nuwa.infrastructure.zeus.third.ChannelResult;
import com.nuwa.infrastructure.zeus.third.sms.ZhuTongSmsHttpSender;
import com.nuwa.infrastructure.zeus.third.sms.req.SendSmsCodeReq;
import com.nuwa.infrastructure.zeus.third.sms.resp.SendSmsCodeResp;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

/**
 * PersistentSmsCodeSucceededEventHandler 持久化短信记录成功事件处理器
 *
 * @author hy
 * @date 2021/5/2 15:16
 * @since 1.0.0
 */
@Slf4j
@Component
public class PersistentSmsCodeSucceededEventHandler /*extends AbstractEventHandler<PersistentSmsCodeSucceededEvent> */ {

    @Autowired
    private SmsCodeService smsCodeService;

    @EventListener(PersistentSmsCodeSucceededEvent.class)
    public void handle(PersistentSmsCodeSucceededEvent event) {
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
