package com.nuwa.app.zeus.command.feedback.qry;

import com.alibaba.cola.dto.SingleResponse;
import com.nuwa.client.zeus.dto.clientobject.feedback.GetProblemFeedbackOneCmd;
import com.nuwa.framework.cola.starter.cmd.AbstractQryExe;
import com.nuwa.infrastructure.zeus.database.feedback.entity.ProblemFeedback;
import com.nuwa.infrastructure.zeus.database.feedback.service.ProblemFeedbackService;
import com.nuwa.infrastructure.zeus.enums.ErrorEnum;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class GetProblemFeedbackOneCmdExe extends AbstractQryExe<GetProblemFeedbackOneCmd, SingleResponse> {
    @Autowired
    private ProblemFeedbackService problemFeedbackService;
    @Override
    protected SingleResponse handle(GetProblemFeedbackOneCmd cmd) {
        ProblemFeedback one = problemFeedbackService.lambdaQuery().eq(ProblemFeedback::getId, cmd.getId()).one();
        if(one!=null) {
            return SingleResponse.of(one);
        }
        return SingleResponse.buildFailure(ErrorEnum.DATA_FAIL.getErrCode(), ErrorEnum.DATA_FAIL.getErrDesc());
    }
}
