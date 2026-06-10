package com.nuwa.app.ticket.command.callcenter.center.query;

import com.alibaba.cola.dto.SingleResponse;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.nuwa.client.ticket.dto.clientobject.callcenter.qry.CallCenterPageQry;
import com.nuwa.framework.cola.starter.cmd.AbstractQryExe;
import com.nuwa.infrastructure.ticket.database.callcenter.entity.CallCenter;
import com.nuwa.infrastructure.ticket.database.callcenter.param.CallCenterPageParam;
import com.nuwa.infrastructure.ticket.database.callcenter.service.CallCenterService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class PageCallCenterQryExe extends AbstractQryExe<CallCenterPageQry, SingleResponse> {
    @Autowired
    private CallCenterService callCenterService;

    @Override
    protected SingleResponse handle(CallCenterPageQry cmd) {
        IPage<CallCenter> callCenterIPage = callCenterService.paginateByParam(new CallCenterPageParam(cmd));
        return SingleResponse.of(callCenterIPage);
    }
}
