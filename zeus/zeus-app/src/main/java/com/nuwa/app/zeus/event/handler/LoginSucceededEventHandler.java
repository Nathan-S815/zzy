package com.nuwa.app.zeus.event.handler;

import cn.hutool.json.JSONUtil;
import com.nuwa.client.zeus.dto.domainevent.sms.LoginSucceededEvent;
import com.nuwa.client.zeus.dto.domainevent.sms.PersistentSmsCodeSucceededEvent;
import com.nuwa.framework.cola.starter.event.AbstractEventHandler;
import com.nuwa.infrastructure.zeus.database.base.entity.BaseUser;
import com.nuwa.infrastructure.zeus.database.base.entity.LoginLog;
import com.nuwa.infrastructure.zeus.database.base.service.BaseUserService;
import com.nuwa.infrastructure.zeus.database.base.service.LoginLogService;
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
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;

import java.util.Date;

/**
 * PersistentSmsCodeSucceededEventHandler 持久化短信记录成功事件处理器
 *
 * @author hy
 * @date 2021/5/2 15:16
 * @since 1.0.0
 */
@Slf4j
@Component
public class LoginSucceededEventHandler /*extends AbstractEventHandler<LoginSucceededEvent>*/ {

    @Autowired
    private BaseUserService baseUserService;

    @Autowired
    private LoginLogService loginLogService;

    @EventListener(LoginSucceededEvent.class)
    public void handle(LoginSucceededEvent event) {
        log.info("event:{}", JSONUtil.toJsonStr(event));
        baseUserService.incrementUpdate(BaseUser::getLoginTimes,1)
                .set(BaseUser::getLastLoginIp,event.getIp())
                .set(BaseUser::getLastLoginTime,new Date())
                .eq(BaseUser::getId,event.getUserId())
                .update();

        LoginLog log = new LoginLog();
        log.setUserId(event.getUserId());
        log.setUserName(event.getUserName());
        log.setBrowser(event.getBrowser());
        log.setLoginIp(event.getIp());
        log.setLoginTime(new Date());
        loginLogService.save(log);
    }
}
