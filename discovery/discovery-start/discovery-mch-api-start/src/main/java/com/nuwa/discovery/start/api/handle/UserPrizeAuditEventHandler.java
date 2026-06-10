package com.nuwa.discovery.start.api.handle;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.StrUtil;
import com.nuwa.client.discovery.dto.domainevent.NewTaskOnLineEvent;
import com.nuwa.client.discovery.dto.domainevent.UserPrizeAuditEvent;
import com.nuwa.client.discovery.dto.domainevent.UserPrizeSubmitEvent;
import com.nuwa.discovery.start.api.constants.SmsBizConstant;
import com.nuwa.discovery.start.api.constants.SmsStatusEnum;
import com.nuwa.discovery.start.api.dingtalk.DingtalkService;
import com.nuwa.discovery.start.api.third.ChannelResult;
import com.nuwa.discovery.start.api.third.sms.ZhuTongSmsHttpSender;
import com.nuwa.discovery.start.api.third.sms.req.SendSmsCodeReq;
import com.nuwa.discovery.start.api.third.sms.resp.SendSmsCodeResp;
import com.nuwa.framework.cola.starter.event.AbstractEventHandler;
import com.nuwa.infrastructure.discovery.database.sms.entity.SmsTemplate;
import com.nuwa.infrastructure.discovery.database.sms.service.SmsTemplateService;
import com.nuwa.infrastructure.discovery.database.task.entity.ScenicTask;
import com.nuwa.infrastructure.discovery.database.task.entity.TaskMessageSubscribe;
import com.nuwa.infrastructure.discovery.database.task.entity.TaskPrize;
import com.nuwa.infrastructure.discovery.database.task.service.ScenicTaskService;
import com.nuwa.infrastructure.discovery.database.task.service.TaskMessageSubscribeService;
import com.nuwa.infrastructure.discovery.database.task.service.TaskPrizeService;
import com.nuwa.infrastructure.discovery.database.user.entity.Member;
import com.nuwa.infrastructure.discovery.database.user.entity.MemberTaskPrize;
import com.nuwa.infrastructure.discovery.database.user.entity.SmsCode;
import com.nuwa.infrastructure.discovery.database.user.service.MemberAccountBindService;
import com.nuwa.infrastructure.discovery.database.user.service.MemberService;
import com.nuwa.infrastructure.discovery.database.user.service.MemberTaskPrizeService;
import com.nuwa.infrastructure.discovery.database.user.service.SmsCodeService;
import com.nuwa.infrastructure.discovery.enums.AlertSmsEnum;
import com.nuwa.infrastructure.discovery.util.TemplateUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * UserPrizeAuditEventHandler 达人报名审核事件处理
 *
 * @author hy
 * @date 2021/5/2 15:16
 * @since 1.0.0
 */
@Slf4j
@Component
public class UserPrizeAuditEventHandler/* extends AbstractEventHandler<UserPrizeAuditEvent> */{

    @Autowired
    private TaskMessageSubscribeService taskMessageSubscribeService;

    @Autowired
    private MemberTaskPrizeService memberTaskPrizeService;

    @Autowired
    private ScenicTaskService scenicTaskService;

    @Autowired
    private SmsTemplateService smsTemplateService;

    @Autowired
    private SmsCodeService smsCodeService;

    @Autowired
    private DingtalkService dingtalkService;

    @Autowired
    private MemberAccountBindService memberAccountBindService;

    @Autowired
    private MemberService memberService;

    @Autowired
    private TaskPrizeService taskPrizeService;

    @EventListener(UserPrizeAuditEvent.class)
    @Async
    public void handle(UserPrizeAuditEvent event) {
        log.info("event:{}", event);
        MemberTaskPrize memberTaskPrize = memberTaskPrizeService.getById(event.getUserPrizeId());
        ScenicTask scenicTask = scenicTaskService.getById(memberTaskPrize.getTaskId());
        TaskPrize taskPrize = taskPrizeService.getById(memberTaskPrize.getTaskPrizeId());
        TaskMessageSubscribe messageSubscribe = taskMessageSubscribeService.lambdaQuery()
                .eq(TaskMessageSubscribe::getTaskId, scenicTask.getId())
                .eq(TaskMessageSubscribe::getBizCode, AlertSmsEnum.PRIZE_APPLY_PROCESS.getBizCode())
                .eq(TaskMessageSubscribe::getStatus, 1)
                .one();
        if (Objects.nonNull(messageSubscribe)) {
            SmsTemplate smsTemplate = smsTemplateService.getById(messageSubscribe.getTemplateId());
            //任务id:${taskId};任务标题:${taskName};申领权益类型:${prizeTypeName};申领权益标题:${prizeName};申领时间:${applyTime};申领状态:${applyStatus}
            Map<String, Object> map = new HashMap<>();
            map.put("taskId", scenicTask.getId());
            map.put("taskName", scenicTask.getName());
            map.put("linkName", scenicTask.getLinkman());
            map.put("linkMobile", scenicTask.getLinkmanTelephone());
            map.put("prizeTypeName", taskPrize.getPrizeTitle());
            map.put("prizeName", memberTaskPrize.getName());
            map.put("applyTime", DateUtil.format(memberTaskPrize.getSubmitTime(), "yyy-MM-dd HH:mm:ss"));
            //1:待认领 2:已认领，待发放 3:已发放 4:审核失败
            Integer status = memberTaskPrize.getStatus();
            if (status.equals(3)) {
                map.put("applyStatus", "已发放");
            } else {
                map.put("applyStatus", "审核失败");
            }
            String content = TemplateUtil.processTemplate(smsTemplate.getContent(), map);
            memberService.lambdaQuery().eq(Member::getUserId, memberTaskPrize.getUserId()).list().forEach(x -> {
                if (StrUtil.isBlank(x.getUserPhone())) {
                    return;
                }
                try {
                    if ("dd".equals(messageSubscribe.getAlertType())) {
                        SmsCode sms = new SmsCode();
                        sms.setTitle("达人权益状态变更通知");
                        sms.setCode("");
                        sms.setContent(content);
                        sms.setCreateDate(new Date());
                        sms.setMobile(x.getUserPhone());
                        sms.setSendTime(new Date());
                        sms.setDeadTime(DateUtil.offsetMinute(new Date(), 10));
                        sms.setStatus(SmsStatusEnum.UNSENT.getValue());
                        sms.setSmsId(StrUtil.EMPTY);
                        sms.setBizCode(SmsBizConstant.USER_PRIZE_AUDIT);
                        sms.setSendType(20);
                        sms.setCheckStatus(10);
                        boolean insert = sms.insert();

                        boolean b = dingtalkService.sendTextMessage(sms.getMobile(), content);
                        if (b) {
                            smsCodeService.lambdaUpdate()
                                    .set(SmsCode::getStatus, SmsStatusEnum.SENT.getValue())
                                    .eq(SmsCode::getId, sms.getId())
                                    .update();
                        } else {
                            smsCodeService.lambdaUpdate()
                                    .set(SmsCode::getStatus, SmsStatusEnum.FAILURE.getValue())
                                    .eq(SmsCode::getId, sms.getId())
                                    .update();
                        }

                    } else if ("sms".equals(messageSubscribe.getAlertType())) {
                        SmsCode sms = new SmsCode();
                        sms.setTitle("达人权益状态变更通知");
                        sms.setCode("");
                        sms.setContent("【中智游】" + content);
                        sms.setCreateDate(new Date());
                        sms.setMobile(x.getUserPhone());
                        sms.setSendTime(new Date());
                        sms.setDeadTime(DateUtil.offsetMinute(new Date(), 10));
                        sms.setStatus(SmsStatusEnum.UNSENT.getValue());
                        sms.setSmsId(StrUtil.EMPTY);
                        sms.setBizCode(SmsBizConstant.USER_PRIZE_AUDIT);
                        sms.setSendType(10);
                        sms.setCheckStatus(10);
                        boolean insert = sms.insert();

                        SendSmsCodeReq req = new SendSmsCodeReq();
                        req.setContent(sms.getContent());
                        req.setMobile(sms.getMobile());
                        ChannelResult<SendSmsCodeResp> sendResult = ZhuTongSmsHttpSender.sendSmsCode(req);
                        if (sendResult.isSuccessful()) {
                            smsCodeService.lambdaUpdate()
                                    .set(SmsCode::getSmsId, sendResult.getData().getMsgId())
                                    .set(SmsCode::getStatus, SmsStatusEnum.SENT.getValue())
                                    .eq(SmsCode::getId, sms.getId())
                                    .update();
                        } else {
                            smsCodeService.lambdaUpdate()
                                    .set(SmsCode::getSmsId, sendResult.getData().getMsgId())
                                    .set(SmsCode::getErrMsg, sendResult.getData().getMsg())
                                    .set(SmsCode::getStatus, SmsStatusEnum.FAILURE.getValue())
                                    .eq(SmsCode::getId, sms.getId())
                                    .update();
                        }
                    }
                } catch (Exception ex) {
                    log.error("发送通知失败", ex);
                }
            });
        }
    }
}
