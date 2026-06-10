package com.nuwa.discovery.start.api.handle;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.StrUtil;
import com.nuwa.client.discovery.dto.domainevent.UserPrizeSubmitEvent;
import com.nuwa.discovery.start.api.constants.SmsBizConstant;
import com.nuwa.discovery.start.api.constants.SmsStatusEnum;
import com.nuwa.discovery.start.api.dingtalk.DingtalkService;
import com.nuwa.discovery.start.api.third.ChannelResult;
import com.nuwa.discovery.start.api.third.sms.ZhuTongSmsHttpSender;
import com.nuwa.discovery.start.api.third.sms.req.SendSmsCodeReq;
import com.nuwa.discovery.start.api.third.sms.resp.SendSmsCodeResp;
import com.nuwa.infrastructure.discovery.database.sms.entity.SmsTemplate;
import com.nuwa.infrastructure.discovery.database.sms.service.SmsTemplateService;
import com.nuwa.infrastructure.discovery.database.task.entity.ScenicTask;
import com.nuwa.infrastructure.discovery.database.task.entity.TaskMessageSubscribe;
import com.nuwa.infrastructure.discovery.database.task.entity.TaskPrizeType;
import com.nuwa.infrastructure.discovery.database.task.service.ScenicTaskService;
import com.nuwa.infrastructure.discovery.database.task.service.TaskMessageSubscribeService;
import com.nuwa.infrastructure.discovery.database.task.service.TaskPrizeTypeService;
import com.nuwa.infrastructure.discovery.database.user.entity.Member;
import com.nuwa.infrastructure.discovery.database.user.entity.MemberAccountBind;
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

import java.util.*;

/**
 * UserPrizeSubmitEventHandler 达人权益认领成功事件处理
 *
 * @author hy
 * @date 2021/5/2 15:16
 * @since 1.0.0
 */
@Slf4j
@Component
public class UserPrizeSubmitEventHandler/* extends AbstractEventHandler<UserPrizeSubmitEvent>*/ {

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
    private TaskPrizeTypeService taskPrizeTypeService;

    @EventListener(UserPrizeSubmitEvent.class)
    @Async
    public void handle(UserPrizeSubmitEvent event) {
        log.info("event:{}", event);
        MemberTaskPrize memberTaskPrize = memberTaskPrizeService.getById(event.getUserPrizeId());
        ScenicTask scenicTask = scenicTaskService.getById(memberTaskPrize.getTaskId());

        TaskMessageSubscribe messageSubscribe = taskMessageSubscribeService.lambdaQuery()
                .eq(TaskMessageSubscribe::getTaskId, scenicTask.getId())
                .eq(TaskMessageSubscribe::getBizCode, AlertSmsEnum.MEMBER_PRIZE_APPLY.getBizCode())
                .eq(TaskMessageSubscribe::getStatus, 1)
                .one();
        if (Objects.nonNull(messageSubscribe)) {

            SmsTemplate smsTemplate = smsTemplateService.getById(messageSubscribe.getTemplateId());

            MemberAccountBind memberAccountBind = memberAccountBindService.lambdaQuery()
                    .eq(MemberAccountBind::getUserId, memberTaskPrize.getUserId())
                    .eq(MemberAccountBind::getChannelCode, memberTaskPrize.getPlatformCode())
                    .one();

            Member member = memberService.getById(memberTaskPrize.getUserId());

            TaskPrizeType taskPrizeType = taskPrizeTypeService.getById(memberTaskPrize.getPrizeTypeId());

            //任务id:${taskId};任务标题:${taskName};申领权益类型:${prizeTypeName};申领权益标题:${prizeName};达人昵称:${userNick};达人手机号码:${userMobile}
            Map<String, Object> map = new HashMap<>();
            map.put("taskId", scenicTask.getId());
            map.put("taskName", scenicTask.getName());
            map.put("prizeTypeName", taskPrizeType.getPrizeTypeName());
            map.put("prizeName", memberTaskPrize.getName());
            map.put("userNick", memberAccountBind.getNick());
            map.put("userMobile", member.getUserPhone());
            String content = TemplateUtil.processTemplate(smsTemplate.getContent(), map);

            Arrays.stream(messageSubscribe.getAccountList().split(";")).forEach(x -> {

                try {
                    if (messageSubscribe.getAlertType().equals("dd")) {
                        SmsCode sms = new SmsCode();
                        sms.setTitle("达人权益认领");
                        sms.setCode("");
                        sms.setContent(content);
                        sms.setCreateDate(new Date());
                        sms.setMobile(x);
                        sms.setSendTime(new Date());
                        sms.setDeadTime(DateUtil.offsetMinute(new Date(), 10));
                        sms.setStatus(SmsStatusEnum.UNSENT.getValue());
                        sms.setSmsId(StrUtil.EMPTY);
                        sms.setBizCode(SmsBizConstant.USER_PRIZE_APPLY);
                        sms.setSendType(20);
                        sms.setCheckStatus(10);
                        boolean insert = sms.insert();

                        boolean b = dingtalkService.sendTextMessage(sms.getMobile(), content);
                        if(b){
                            smsCodeService.lambdaUpdate()
                                    .set(SmsCode::getStatus, SmsStatusEnum.SENT.getValue())
                                    .eq(SmsCode::getId, sms.getId())
                                    .update();
                        }else{
                            smsCodeService.lambdaUpdate()
                                    .set(SmsCode::getStatus, SmsStatusEnum.FAILURE.getValue())
                                    .eq(SmsCode::getId, sms.getId())
                                    .update();
                        }

                    } else if (messageSubscribe.getAlertType().equals("sms")) {

                        SmsCode sms = new SmsCode();
                        sms.setTitle("达人权益认领");
                        sms.setCode("");
                        sms.setContent("【中智游】" + content);
                        sms.setCreateDate(new Date());
                        sms.setMobile(x);
                        sms.setSendTime(new Date());
                        sms.setDeadTime(DateUtil.offsetMinute(new Date(), 10));
                        sms.setStatus(SmsStatusEnum.UNSENT.getValue());
                        sms.setSmsId(StrUtil.EMPTY);
                        sms.setBizCode(SmsBizConstant.USER_PRIZE_APPLY);
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
