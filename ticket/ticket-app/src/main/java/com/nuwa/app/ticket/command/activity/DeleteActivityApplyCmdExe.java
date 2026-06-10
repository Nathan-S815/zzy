package com.nuwa.app.ticket.command.activity;

import cn.hutool.core.bean.BeanUtil;
import com.alibaba.cola.dto.SingleResponse;
import com.baomidou.mybatisplus.extension.service.additional.update.impl.LambdaUpdateChainWrapper;
import com.nuwa.client.ticket.dto.clientobject.activity.DeleteActivityApplyCmd;
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
public class DeleteActivityApplyCmdExe extends AbstractCmdExe<DeleteActivityApplyCmd, SingleResponse> {

    @Autowired
    private CultureActivityApplyService cultureActivityApplyService;

    @Override
    protected SingleResponse handle(DeleteActivityApplyCmd cmd) {
        if (BeanUtil.isNotEmpty(cmd.getId())) {
            return ErrorEnum.MISSING_REQUIRED_PARAMS.buildFailure();
        }
        LambdaUpdateChainWrapper<CultureActivityApply> lambdaUpdate = cultureActivityApplyService.lambdaUpdate()
                .set(CultureActivityApply::getDeleteFlag, DeleteFlagEnum.DELETE.getCode())
                .in(true,CultureActivityApply::getId, cmd.getId());
        return lambdaUpdate.update() ? SingleResponse.buildSuccess() : ErrorEnum.FAILED.buildFailure();
    }
}
