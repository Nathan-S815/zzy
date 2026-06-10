package com.nuwa.app.ticket.command.callcenter.problem;

import cn.hutool.core.bean.BeanUtil;
import com.alibaba.cola.dto.SingleResponse;
import com.nuwa.client.ticket.dto.clientobject.callcenter.problem.CreateOnlineProblemCmd;
import com.nuwa.client.ticket.dto.clientobject.callcenter.problem.co.CreateOnlineProblemCO;
import com.nuwa.framework.cola.starter.cmd.AbstractCmdExe;
import com.nuwa.infrastructure.ticket.database.callcenter.entity.OnlineProblem;
import com.nuwa.infrastructure.ticket.database.callcenter.service.OnlineProblemService;
import com.nuwa.infrastructure.ticket.enums.ErrorEnum;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Date;

@Slf4j
@Component
public class CreateOnlineProblemCmdExe  extends AbstractCmdExe<CreateOnlineProblemCmd, SingleResponse> {
    @Autowired
    private OnlineProblemService onlineProblemService;
    @Override
    protected SingleResponse handle(CreateOnlineProblemCmd cmd) {
        CreateOnlineProblemCO createOnlineProblemCO = cmd.getCreateOnlineProblemCO();
        OnlineProblem onlineProblem = new OnlineProblem();
        BeanUtil.copyProperties(createOnlineProblemCO,onlineProblem);
        onlineProblem.setCreateTime(new Date());
        onlineProblem.setAppId(cmd.getAppId());
        onlineProblem.setMchId(cmd.getUserAware().getMchId());
        boolean save = onlineProblemService.save(onlineProblem);
        return save? SingleResponse.buildSuccess():SingleResponse.buildFailure(ErrorEnum.DATA_FAIL.getErrCode(), ErrorEnum.DATA_FAIL.getErrDesc());
    }
}
