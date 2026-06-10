package com.nuwa.app.zeus.command.feedback.qry;

import com.alibaba.cola.dto.SingleResponse;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.nuwa.client.zeus.dto.clientobject.feedback.qry.ProblemFeedbackPageQry;
import com.nuwa.framework.cola.starter.cmd.AbstractQryExe;
import com.nuwa.infrastructure.zeus.database.feedback.entity.ProblemFeedback;
import com.nuwa.infrastructure.zeus.database.feedback.param.ProblemFeedbackPageParam;
import com.nuwa.infrastructure.zeus.database.feedback.service.ProblemFeedbackService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class ProblemFeedbackPageQryExe extends AbstractQryExe<ProblemFeedbackPageQry, SingleResponse<IPage<ProblemFeedback>>> {
    @Autowired
    private ProblemFeedbackService problemFeedbackService;
    @Override
    protected SingleResponse<IPage<ProblemFeedback>> handle(ProblemFeedbackPageQry cmd) {
        IPage<ProblemFeedback> problemFeedbackIPage = problemFeedbackService.paginateByParam(new ProblemFeedbackPageParam(cmd));
        return SingleResponse.of(problemFeedbackIPage);
    }
}
