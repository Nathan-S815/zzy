package com.nuwa.app.ticket.command.activity;

import cn.hutool.core.bean.BeanUtil;
import com.alibaba.cola.dto.SingleResponse;
import com.nuwa.client.ticket.dto.clientobject.activity.CreateActivityApplyCmd;
import com.nuwa.framework.cola.starter.cmd.AbstractCmdExe;
import com.nuwa.infrastructure.ticket.database.activity.entity.CultureActivity;
import com.nuwa.infrastructure.ticket.database.activity.entity.CultureActivityApply;
import com.nuwa.infrastructure.ticket.database.activity.service.CultureActivityApplyService;
import com.nuwa.infrastructure.ticket.database.activity.service.CultureActivityService;
import com.nuwa.infrastructure.ticket.enums.ErrorEnum;
import com.nuwa.infrastructure.ticket.enums.ErrorEnum;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.Objects;

@Slf4j
@Component
public class CreateActivityApplyCmdExe extends AbstractCmdExe<CreateActivityApplyCmd, SingleResponse> {

    @Autowired
    private CultureActivityService cultureActivityService;
    @Autowired
    private CultureActivityApplyService cultureActivityApplyService;

    @Override
    protected SingleResponse handle(CreateActivityApplyCmd cmd) {
        CultureActivity activity = cultureActivityService.getById(cmd.getActivityId());
        if (Objects.isNull(activity)) {
            return ErrorEnum.ACTIVITY_NOT_EXIST.buildFailure();
        }
        Date holdTimeEnd = activity.getHoldTimeEnd();
        if(holdTimeEnd.before(new Date())){
            return ErrorEnum.APPLY_FAILED.buildFailure();
        }
        Integer count = cultureActivityApplyService.lambdaQuery()
                .eq(CultureActivityApply::getContactsMobile, cmd.getContactsMobile())
                .eq(CultureActivityApply::getActivityId, cmd.getActivityId())
                .count();
        if (count > 0){
            return ErrorEnum.REPEAT_APPLY.buildFailure();
        }

        try {
            boolean ticketsNumUp = cultureActivityService.decreaseUpdate(CultureActivity::getTicketsNum, cmd.getPeopleNum())
                    .eq(CultureActivity::getId, cmd.getActivityId())
                    .update();
            if (!ticketsNumUp) {
                log.error("[ActivityId:{}]余票不足", cmd.getActivityId());
                return ErrorEnum.ACTIVITY_TICKETS_NOT_ENOUGH.buildFailure();
            }
        } catch (Exception ex) {
            log.error("[ActivityId:{}]余票不足", cmd.getActivityId(), ex);
            return ErrorEnum.ACTIVITY_TICKETS_NOT_ENOUGH.buildFailure();
        }

        boolean applyNumUp = cultureActivityService.incrementUpdate(CultureActivity::getApplyNum, cmd.getPeopleNum())
                .eq(CultureActivity::getId, cmd.getActivityId())
                .update();
        if (applyNumUp) {
            log.info("更新[ActivityId:{}]已购票数成功", cmd.getActivityId());
        }

        CultureActivityApply entity = new CultureActivityApply();
        BeanUtil.copyProperties(cmd, entity);
        entity.setMchId(cmd.getUserAware().getMchId());
        entity.setAppId(cmd.getUserAware().getMchAppId());
        entity.setUserId(cmd.getUserAware().getUserId());
        return cultureActivityApplyService.save(entity) ? SingleResponse.of(entity) : ErrorEnum.APPLY_FAILED.buildFailure();
    }

}
