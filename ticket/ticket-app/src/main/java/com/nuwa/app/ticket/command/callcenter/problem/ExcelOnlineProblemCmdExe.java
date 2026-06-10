package com.nuwa.app.ticket.command.callcenter.problem;

import com.alibaba.cola.dto.SingleResponse;
import com.nuwa.client.ticket.dto.clientobject.callcenter.problem.ExcelOnlineProblemCmd;
import com.nuwa.framework.cola.starter.cmd.AbstractQryExe;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class ExcelOnlineProblemCmdExe extends AbstractQryExe<ExcelOnlineProblemCmd, SingleResponse> {
    @Override
    protected SingleResponse handle(ExcelOnlineProblemCmd cmd) {
        return null;
    }
}
