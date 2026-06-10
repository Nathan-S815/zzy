package com.nuwa.app.zeus.command.feedback;

import com.alibaba.cola.dto.SingleResponse;
import com.nuwa.client.zeus.dto.clientobject.feedback.EditProblemFeedbackStatusCmd;
import com.nuwa.framework.cola.starter.cmd.AbstractCmdExe;
import com.nuwa.infrastructure.zeus.database.feedback.entity.ProblemFeedback;
import com.nuwa.infrastructure.zeus.database.feedback.service.ProblemFeedbackService;
import com.nuwa.infrastructure.zeus.enums.ErrorEnum;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Date;

@Slf4j
@Component
public class EditProblemFeedbackStatusCmdExe extends AbstractCmdExe<EditProblemFeedbackStatusCmd, SingleResponse> {
    @Autowired
    private ProblemFeedbackService problemFeedbackService;
    @Override
    protected SingleResponse handle(EditProblemFeedbackStatusCmd cmd){
        ProblemFeedback one = problemFeedbackService.lambdaQuery().eq(ProblemFeedback::getId, cmd.getId()).one();
        one.setStatus(cmd.getStatus());
        one.setRemark(cmd.getRemark());
        one.setUpdateTime(new Date());
        boolean updateById = one.updateById();
        if (updateById) {
            return ErrorEnum.DATA_SUCCESS.buildSuccess(one);
        }
        return ErrorEnum.DATA_FAIL.buildFailure();
    }
}
