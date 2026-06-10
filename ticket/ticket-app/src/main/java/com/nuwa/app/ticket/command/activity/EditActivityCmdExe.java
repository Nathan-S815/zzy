package com.nuwa.app.ticket.command.activity;

import cn.hutool.core.bean.BeanUtil;
import com.alibaba.cola.dto.SingleResponse;
import com.nuwa.client.ticket.dto.clientobject.activity.EditActivityCmd;
import com.nuwa.framework.cola.starter.cmd.AbstractCmdExe;
import com.nuwa.infrastructure.ticket.database.activity.entity.CultureActivity;
import com.nuwa.infrastructure.ticket.database.activity.service.CultureActivityService;
import com.nuwa.infrastructure.ticket.enums.ErrorEnum;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Slf4j
@Component
public class EditActivityCmdExe extends AbstractCmdExe<EditActivityCmd, SingleResponse> {

    @Autowired
    private CultureActivityService cultureActivityService;

    @Override
    protected SingleResponse handle(EditActivityCmd cmd) {
        if (BeanUtil.isEmpty(cmd.getId())) {
            return ErrorEnum.MISSING_REQUIRED_PARAMS.buildFailure();
        }
        CultureActivity one = cultureActivityService.lambdaQuery().eq(CultureActivity::getMchId, cmd.getUserAware().getMchId()).eq(CultureActivity::getAppId, cmd.getAppId()).eq(CultureActivity::getId, cmd.getId()).one();
        if (Objects.isNull(one)) {
            return ErrorEnum.FAILED.buildFailure();
        }
        CultureActivity entity = new CultureActivity();
        BeanUtil.copyProperties(cmd,entity);
        return cultureActivityService.updateById(entity) ? SingleResponse.of(entity) : ErrorEnum.FAILED.buildFailure();
    }
}
