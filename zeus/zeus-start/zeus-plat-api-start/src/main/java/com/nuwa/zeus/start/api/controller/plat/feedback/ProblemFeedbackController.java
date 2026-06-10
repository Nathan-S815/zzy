package com.nuwa.zeus.start.api.controller.plat.feedback;

import com.alibaba.cola.dto.SingleResponse;
import com.nuwa.app.zeus.command.feedback.EditProblemFeedbackStatusCmdExe;
import com.nuwa.app.zeus.command.feedback.qry.GetProblemFeedbackOneCmdExe;
import com.nuwa.app.zeus.command.feedback.qry.ProblemFeedbackPageQryExe;
import com.nuwa.client.zeus.dto.clientobject.feedback.EditProblemFeedbackStatusCmd;
import com.nuwa.client.zeus.dto.clientobject.feedback.GetProblemFeedbackOneCmd;
import com.nuwa.client.zeus.dto.clientobject.feedback.qry.ProblemFeedbackPageQry;
import com.nuwa.framework.base.UserAware;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/problem/feedback")
@Api(tags = {"平台问题反馈"})
public class ProblemFeedbackController {
    @Autowired
    private ProblemFeedbackPageQryExe problemFeedbackPageQryExe;
    @Autowired
    private GetProblemFeedbackOneCmdExe getProblemFeedbackOneCmdExe;
    @Autowired
    private EditProblemFeedbackStatusCmdExe editProblemFeedbackStatusCmdExe;

    @ApiOperation(value = "获取问题反馈列表")
    @GetMapping(value = "/listProblemFeedbackPage")
    public SingleResponse listProblemFeedbackPage(ProblemFeedbackPageQry cmd, UserAware userAware) {
        return problemFeedbackPageQryExe.execute(cmd);
    }
    @ApiOperation(value = "根据id获取详情")
    @GetMapping(value = "/getProblemFeedbackPage")
    public SingleResponse getProblemFeedbackPage(GetProblemFeedbackOneCmd cmd, UserAware userAware) {
        return getProblemFeedbackOneCmdExe.execute(cmd);
    }
    @ApiOperation(value = "修改问题状态和备注")
    @PostMapping(value = "/editProblemFeedbackStatus")
    public SingleResponse editProblemFeedbackStatus(@RequestBody EditProblemFeedbackStatusCmd cmd, UserAware userAware) {
        return editProblemFeedbackStatusCmdExe.execute(cmd);
    }
}
