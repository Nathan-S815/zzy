package com.nuwa.app.ticket.command.callcenter.center;

import com.alibaba.cola.dto.SingleResponse;
import com.alibaba.cola.exception.Assert;
import com.nuwa.client.ticket.dto.clientobject.callcenter.center.UpdateCallCenterCmd;
import com.nuwa.client.ticket.dto.clientobject.callcenter.center.co.UpdateCallCenterCO;
import com.nuwa.framework.cola.starter.cmd.AbstractCmdExe;
import com.nuwa.infrastructure.ticket.database.callcenter.entity.CallCenter;
import com.nuwa.infrastructure.ticket.database.callcenter.service.CallCenterService;
import com.nuwa.infrastructure.ticket.enums.ErrorEnum;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class UpdateCallCenterCmdExe extends AbstractCmdExe<UpdateCallCenterCmd, SingleResponse> {
    @Autowired
    private CallCenterService callCenterService;
    @Override
    protected SingleResponse handle(UpdateCallCenterCmd cmd) {
        UpdateCallCenterCO updateCallCenterCO = cmd.getUpdateCallCenterCO();
        CallCenter one = callCenterService.lambdaQuery()
                .eq(CallCenter::getId, updateCallCenterCO.getId()).one();
        Assert.notNull(one, ErrorEnum.DATA_NON, "呼叫中心(id:" + cmd.getUpdateCallCenterCO().getId() + ")不存在");
        BeanUtils.copyProperties(updateCallCenterCO, one);
        boolean updateById = one.updateById();
        if (updateById) {
            return ErrorEnum.DATA_SUCCESS.buildSuccess(one.getId());
        }
        return ErrorEnum.DATA_FAIL.buildFailure();
    }
}
