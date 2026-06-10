package com.nuwa.app.ticket.command.callcenter.problem.query;

import com.alibaba.cola.dto.SingleResponse;
import com.nuwa.client.ticket.dto.clientobject.callcenter.problem.GetOnlineProblemCmd;
import com.nuwa.client.ticket.dto.clientobject.callcenter.problem.co.GetOnlineProblemCO;
import com.nuwa.framework.cola.starter.cmd.AbstractQryExe;
import com.nuwa.infrastructure.ticket.database.callcenter.entity.OnlineProblem;
import com.nuwa.infrastructure.ticket.database.callcenter.service.OnlineProblemService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class GetOnlineProblemQryExe extends AbstractQryExe<GetOnlineProblemCmd, SingleResponse> {
    @Autowired
    private OnlineProblemService onlineProblemService;
    @Override
    protected SingleResponse handle(GetOnlineProblemCmd cmd) {
        GetOnlineProblemCO getOnlineProblemCO = cmd.getGetOnlineProblemCO();
        OnlineProblem one = onlineProblemService.lambdaQuery().eq(OnlineProblem::getId, getOnlineProblemCO.getId()).one();
        return SingleResponse.of(one);
    }
}
