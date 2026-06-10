package com.nuwa.app.ticket.command.callcenter.center;

import com.alibaba.cola.dto.SingleResponse;
import com.baomidou.mybatisplus.extension.service.additional.update.impl.LambdaUpdateChainWrapper;
import com.nuwa.client.ticket.dto.clientobject.callcenter.center.DelCallCenterCmd;
import com.nuwa.framework.cola.starter.cmd.AbstractCmdExe;
import com.nuwa.infrastructure.ticket.database.callcenter.entity.CallCenter;
import com.nuwa.infrastructure.ticket.database.callcenter.service.CallCenterService;
import com.nuwa.infrastructure.ticket.enums.ErrorEnum;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class DelCallCenterCmdExe extends AbstractCmdExe<DelCallCenterCmd, SingleResponse> {
    @Autowired
    private CallCenterService callCenterService;
    @Override
    protected SingleResponse handle(DelCallCenterCmd cmd) {
        LambdaUpdateChainWrapper<CallCenter> lambdaUpdate = callCenterService.lambdaUpdate().set(CallCenter::getDeleteFlag, 1).eq(CallCenter::getId, cmd.getId());
        return lambdaUpdate.update() ? SingleResponse.buildSuccess():SingleResponse.buildFailure(ErrorEnum.DATA_FAIL.getErrCode(), ErrorEnum.DATA_FAIL.getErrDesc());
    }
}
