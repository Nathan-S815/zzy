package com.nuwa.app.ticket.command.callcenter.problem;

import com.alibaba.cola.dto.SingleResponse;
import com.alibaba.cola.exception.Assert;
import com.nuwa.client.ticket.dto.clientobject.callcenter.problem.UpdateOnlineProblemCmd;
import com.nuwa.client.ticket.dto.clientobject.callcenter.problem.co.UpdateOnlineProblemCO;
import com.nuwa.framework.cola.starter.cmd.AbstractCmdExe;
import com.nuwa.infrastructure.ticket.database.callcenter.entity.OnlineProblem;
import com.nuwa.infrastructure.ticket.database.callcenter.service.OnlineProblemService;
import com.nuwa.infrastructure.ticket.enums.ErrorEnum;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class UpdateOnlineProblemCmdExe extends AbstractCmdExe<UpdateOnlineProblemCmd, SingleResponse> {
    @Autowired
    private OnlineProblemService onlineProblemService;
    @Override
    protected SingleResponse handle(UpdateOnlineProblemCmd cmd) {
        UpdateOnlineProblemCO updateOnlineProblemCO = cmd.getUpdateOnlineProblemCO();
        OnlineProblem one = onlineProblemService.lambdaQuery()
                .eq(OnlineProblem::getId, updateOnlineProblemCO.getId()).one();
        Assert.notNull(one, ErrorEnum.DATA_NON, "问题(id:" + cmd.getUpdateOnlineProblemCO().getId() + ")不存在");
        BeanUtils.copyProperties(updateOnlineProblemCO, one);
        boolean updateById = one.updateById();
        if (updateById) {
            return ErrorEnum.DATA_SUCCESS.buildSuccess(one.getId());
        }
        return ErrorEnum.DATA_FAIL.buildFailure();
    }
}
