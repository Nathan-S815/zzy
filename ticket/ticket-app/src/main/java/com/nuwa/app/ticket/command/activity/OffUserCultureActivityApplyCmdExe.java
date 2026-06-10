package com.nuwa.app.ticket.command.activity;

import com.alibaba.cola.dto.SingleResponse;
import com.nuwa.client.ticket.dto.clientobject.activity.OffUserCultureActivityApplyCmd;
import com.nuwa.framework.cola.starter.cmd.AbstractCmdExe;
import com.nuwa.infrastructure.ticket.database.activity.entity.CultureActivityApply;
import com.nuwa.infrastructure.ticket.database.activity.service.CultureActivityApplyService;
import com.nuwa.infrastructure.ticket.enums.DeleteFlagEnum;
import com.nuwa.infrastructure.ticket.enums.ErrorEnum;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class OffUserCultureActivityApplyCmdExe extends AbstractCmdExe<OffUserCultureActivityApplyCmd, SingleResponse> {
    @Autowired
    private CultureActivityApplyService cultureActivityApplyService;
    @Override
    protected SingleResponse handle(OffUserCultureActivityApplyCmd cmd) {
        boolean update = cultureActivityApplyService.lambdaUpdate()
                .set(CultureActivityApply::getDeleteFlag, DeleteFlagEnum.DELETE.getCode())
                .eq(CultureActivityApply::getId, cmd.getId()).update();
        return update ? SingleResponse.buildSuccess() : ErrorEnum.FAILED.buildFailure();
    }
}
