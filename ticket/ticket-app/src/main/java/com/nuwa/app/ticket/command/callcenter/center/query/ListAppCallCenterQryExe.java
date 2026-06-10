package com.nuwa.app.ticket.command.callcenter.center.query;

import com.alibaba.cola.dto.SingleResponse;
import com.nuwa.client.ticket.dto.clientobject.callcenter.center.ListCallCenterCmd;
import com.nuwa.framework.cola.starter.cmd.AbstractQryExe;
import com.nuwa.infrastructure.ticket.database.callcenter.entity.CallCenter;
import com.nuwa.infrastructure.ticket.database.callcenter.service.CallCenterService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Slf4j
@Component
public class ListAppCallCenterQryExe extends AbstractQryExe<ListCallCenterCmd, SingleResponse> {
    @Autowired
    private CallCenterService callCenterService;
    @Override
    protected SingleResponse handle(ListCallCenterCmd cmd) {
        List<CallCenter> list = callCenterService.lambdaQuery()
                .eq(CallCenter::getDeleteFlag, 0)
//                .eq(CallCenter::getAppId, cmd.getUserAware().getMchAppId())
                .eq(CallCenter::getMchId, cmd.getUserAware().getMchId())
                .list();
        return SingleResponse.of(list);
    }
}
