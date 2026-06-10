package com.nuwa.app.ticket.command.callcenter.problem.query;

import com.alibaba.cola.dto.SingleResponse;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.nuwa.client.ticket.dto.clientobject.callcenter.qry.OnlineProblemPageQry;
import com.nuwa.framework.cola.starter.cmd.AbstractQryExe;
import com.nuwa.infrastructure.ticket.database.callcenter.entity.OnlineProblem;
import com.nuwa.infrastructure.ticket.database.callcenter.param.OnlineProblemPageParam;
import com.nuwa.infrastructure.ticket.database.callcenter.service.OnlineProblemService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class PageOnlineProblemQryExe extends AbstractQryExe<OnlineProblemPageQry, SingleResponse> {
    @Autowired
    private OnlineProblemService onlineProblemService;
    @Override
    protected SingleResponse handle(OnlineProblemPageQry cmd) {
        IPage<OnlineProblem> onlineProblemIPage = onlineProblemService.paginateByParam(new OnlineProblemPageParam(cmd));
        return  SingleResponse.of(onlineProblemIPage);
    }
}
