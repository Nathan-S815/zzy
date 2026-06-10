package com.nuwa.app.ticket.command.callcenter.center;

import cn.hutool.core.bean.BeanUtil;
import com.alibaba.cola.dto.SingleResponse;
import com.nuwa.client.ticket.dto.clientobject.callcenter.center.CreateCallCenterCmd;
import com.nuwa.client.ticket.dto.clientobject.callcenter.center.co.CreateCallCenterCO;
import com.nuwa.framework.cola.starter.cmd.AbstractCmdExe;
import com.nuwa.infrastructure.ticket.database.callcenter.entity.CallCenter;
import com.nuwa.infrastructure.ticket.database.callcenter.service.CallCenterService;
import com.nuwa.infrastructure.ticket.enums.ErrorEnum;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Date;

@Slf4j
@Component
public class CreateCallCenterCmdExe extends AbstractCmdExe<CreateCallCenterCmd, SingleResponse> {
    @Autowired
    private CallCenterService callCenterService;
    @Override
    protected SingleResponse handle(CreateCallCenterCmd cmd) {
        CreateCallCenterCO createCallCenterCO = cmd.getCreateCallCenterCO();
        CallCenter callCenter = new CallCenter();
        BeanUtil.copyProperties(createCallCenterCO,callCenter);
        callCenter.setCreateTime(new Date());
        callCenter.setAppId(cmd.getAppId());
        callCenter.setMchId(cmd.getUserAware().getMchId());
        boolean save = callCenterService.save(callCenter);
        return save? SingleResponse.buildSuccess():SingleResponse.buildFailure(ErrorEnum.DATA_FAIL.getErrCode(), ErrorEnum.DATA_FAIL.getErrDesc());
    }
}
