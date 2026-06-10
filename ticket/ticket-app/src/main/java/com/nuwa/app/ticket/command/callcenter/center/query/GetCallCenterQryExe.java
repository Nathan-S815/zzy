package com.nuwa.app.ticket.command.callcenter.center.query;

import com.alibaba.cola.dto.SingleResponse;
import com.nuwa.client.ticket.dto.clientobject.callcenter.center.GetCallCenterCmd;
import com.nuwa.framework.cola.starter.cmd.AbstractQryExe;
import com.nuwa.infrastructure.ticket.database.callcenter.entity.CallCenter;
import com.nuwa.infrastructure.ticket.database.callcenter.service.CallCenterService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class GetCallCenterQryExe extends AbstractQryExe<GetCallCenterCmd, SingleResponse> {
    @Autowired
    private CallCenterService callCenterService;
    @Override
    protected SingleResponse handle(GetCallCenterCmd cmd) {
        CallCenter one = callCenterService.lambdaQuery().eq(CallCenter::getId, cmd.getId()).one();
        return SingleResponse.of(one);
    }
}
