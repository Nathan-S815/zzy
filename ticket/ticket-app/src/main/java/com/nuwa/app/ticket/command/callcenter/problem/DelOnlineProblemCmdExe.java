package com.nuwa.app.ticket.command.callcenter.problem;

import com.alibaba.cola.dto.SingleResponse;
import com.nuwa.client.ticket.dto.clientobject.callcenter.problem.DelOnlineProblemCmd;
import com.nuwa.framework.cola.starter.cmd.AbstractCmdExe;
import com.nuwa.infrastructure.ticket.database.callcenter.entity.OnlineProblem;
import com.nuwa.infrastructure.ticket.database.callcenter.service.OnlineProblemService;
import com.nuwa.infrastructure.ticket.enums.ErrorEnum;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Slf4j
@Component
public class DelOnlineProblemCmdExe extends AbstractCmdExe<DelOnlineProblemCmd, SingleResponse> {
    @Autowired
    private OnlineProblemService onlineProblemService;
    @Override
    protected SingleResponse handle(DelOnlineProblemCmd cmd) {
        List<Long> ids = cmd.getIds();
        boolean del=false;
        for (Long id : ids) {
            del= onlineProblemService.lambdaUpdate()
                    .set(OnlineProblem::getDeleteFlag, 1)
                    .eq(OnlineProblem::getId, id).update();
        }
        if(del) {
            return SingleResponse.buildSuccess();
        }
        return SingleResponse.buildFailure(ErrorEnum.DATA_FAIL.getErrCode(), ErrorEnum.DATA_FAIL.getErrDesc());
    }
}
