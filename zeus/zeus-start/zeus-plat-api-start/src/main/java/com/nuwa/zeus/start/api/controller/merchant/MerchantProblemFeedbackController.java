package com.nuwa.zeus.start.api.controller.merchant;

import com.alibaba.cola.dto.SingleResponse;
import com.nuwa.app.zeus.command.feedback.AddProblemFeedbackCmdExe;
import com.nuwa.app.zeus.command.feedback.qry.MerchantProblemFeedbackPageQryExe;
import com.nuwa.app.zeus.command.feedback.qry.ProblemFeedbackPageQryExe;
import com.nuwa.client.zeus.dto.clientobject.feedback.AddProblemFeedbackCmd;
import com.nuwa.client.zeus.dto.clientobject.feedback.EditProblemFeedbackStatusCmd;
import com.nuwa.client.zeus.dto.clientobject.feedback.qry.ProblemFeedbackPageQry;
import com.nuwa.framework.base.UserAware;
import io.swagger.annotations.Api;

import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("merchant/problem/feedback")
@Api(tags = {"问题反馈"})
public class MerchantProblemFeedbackController {

    @Autowired
    private AddProblemFeedbackCmdExe addProblemFeedbackCmdExe;

    @Autowired
    private MerchantProblemFeedbackPageQryExe merchantProblemFeedbackPageQryExe;

    @ApiOperation(value = "商户新增问题反馈")
    @PostMapping(value = "/addProblemFeedback")
    public SingleResponse addProblemFeedback(@RequestBody AddProblemFeedbackCmd cmd, UserAware userAware) {
        return addProblemFeedbackCmdExe.execute(cmd);
    }

    @ApiOperation(value = "分页查询")
    @PostMapping(value = "/page")
    public SingleResponse page(ProblemFeedbackPageQry cmd, UserAware userAware) {
        return merchantProblemFeedbackPageQryExe.execute(cmd);
    }

}
