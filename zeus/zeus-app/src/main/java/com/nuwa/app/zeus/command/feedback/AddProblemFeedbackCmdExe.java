package com.nuwa.app.zeus.command.feedback;

import com.alibaba.cola.dto.SingleResponse;
import com.nuwa.client.zeus.dto.clientobject.feedback.AddProblemFeedbackCmd;
import com.nuwa.framework.cola.starter.cmd.AbstractCmdExe;
import com.nuwa.infrastructure.zeus.database.feedback.entity.ProblemFeedback;
import com.nuwa.infrastructure.zeus.database.feedback.service.ProblemFeedbackService;
import com.nuwa.infrastructure.zeus.enums.ErrorEnum;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Date;

@Slf4j
@Component
public class AddProblemFeedbackCmdExe extends AbstractCmdExe<AddProblemFeedbackCmd, SingleResponse> {
    @Autowired
    private ProblemFeedbackService problemFeedbackService;
    @Override
    protected SingleResponse handle(AddProblemFeedbackCmd cmd){
        ProblemFeedback problemFeedback = new ProblemFeedback();
        BeanUtils.copyProperties(cmd, problemFeedback);
        problemFeedback.setCreateTime(new Date());
        problemFeedback.setMchId(cmd.getUserAware().getMchId());
        boolean save = problemFeedbackService.save(problemFeedback);
        return save? SingleResponse.of(problemFeedback):SingleResponse.buildFailure(ErrorEnum.DATA_FAIL.getErrCode(), ErrorEnum.DATA_FAIL.getErrDesc());
    }
}
