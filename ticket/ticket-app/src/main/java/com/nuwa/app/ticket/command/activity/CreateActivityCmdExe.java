package com.nuwa.app.ticket.command.activity;

import cn.hutool.core.bean.BeanUtil;
import com.alibaba.cola.dto.SingleResponse;
import com.nuwa.client.ticket.dto.clientobject.activity.CreateActivityCmd;
import com.nuwa.framework.cola.starter.cmd.AbstractCmdExe;
import com.nuwa.infrastructure.ticket.database.activity.entity.CultureActivity;
import com.nuwa.infrastructure.ticket.database.activity.service.CultureActivityService;
import com.nuwa.infrastructure.ticket.enums.ErrorEnum;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class CreateActivityCmdExe extends AbstractCmdExe<CreateActivityCmd, SingleResponse> {

    @Autowired
    private CultureActivityService cultureActivityService;

    @Override
    protected SingleResponse handle(CreateActivityCmd cmd) {
        CultureActivity entity = new CultureActivity();
        BeanUtil.copyProperties(cmd,entity);
        entity.setMchId(cmd.getUserAware().getMchId());
        entity.setAppId(cmd.getAppId());
        return cultureActivityService.save(entity) ? SingleResponse.of(entity) : ErrorEnum.ACTIVITY_ADD_FAILED.buildFailure();
    }
}
