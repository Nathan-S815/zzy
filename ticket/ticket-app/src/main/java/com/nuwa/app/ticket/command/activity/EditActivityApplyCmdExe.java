package com.nuwa.app.ticket.command.activity;

import cn.hutool.core.bean.BeanUtil;
import com.alibaba.cola.dto.SingleResponse;
import com.nuwa.client.ticket.dto.clientobject.activity.EditActivityApplyCmd;
import com.nuwa.framework.cola.starter.cmd.AbstractCmdExe;
import com.nuwa.infrastructure.ticket.database.activity.entity.CultureActivityApply;
import com.nuwa.infrastructure.ticket.database.activity.service.CultureActivityApplyService;
import com.nuwa.infrastructure.ticket.enums.ErrorEnum;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Slf4j
@Component
public class EditActivityApplyCmdExe extends AbstractCmdExe<EditActivityApplyCmd, SingleResponse> {

    @Autowired
    private CultureActivityApplyService cultureActivityApplyService;

    @Override
    protected SingleResponse handle(EditActivityApplyCmd cmd) {
        if(Objects.isNull(cmd.getId())){
            return ErrorEnum.MISSING_REQUIRED_PARAMS.buildFailure();
        }
        CultureActivityApply one = cultureActivityApplyService.lambdaQuery().eq(CultureActivityApply::getMchId, cmd.getUserAware().getMchId()).eq(CultureActivityApply::getId, cmd.getId()).one();
        if (Objects.isNull(one)) {
            return ErrorEnum.FAILED.buildFailure();
        }
        CultureActivityApply entity = new CultureActivityApply();
        BeanUtil.copyProperties(cmd,entity);
        return cultureActivityApplyService.updateById(entity) ? SingleResponse.of(entity) : ErrorEnum.FAILED.buildFailure();
    }
}
