package com.nuwa.app.ticket.command.callcenter.problem.query;

import cn.hutool.core.util.StrUtil;
import com.alibaba.cola.dto.SingleResponse;
import com.nuwa.client.ticket.dto.clientobject.callcenter.problem.GetOnlineProblemAppCmd;
import com.nuwa.framework.cola.starter.cmd.AbstractQryExe;
import com.nuwa.infrastructure.ticket.database.callcenter.entity.CallCenter;
import com.nuwa.infrastructure.ticket.database.callcenter.entity.OnlineProblem;
import com.nuwa.infrastructure.ticket.database.callcenter.service.CallCenterService;
import com.nuwa.infrastructure.ticket.database.callcenter.service.OnlineProblemService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Component
public class GetOnlineProblemAppByNameExe extends AbstractQryExe<GetOnlineProblemAppCmd, SingleResponse> {
    @Autowired
    private OnlineProblemService onlineProblemService;
    @Autowired
    private CallCenterService callCenterService;
    @Override
    protected SingleResponse handle(GetOnlineProblemAppCmd cmd) {
        List<OnlineProblem> list = onlineProblemService.lambdaQuery()
                .eq(OnlineProblem::getDeleteFlag, 0)
//                .eq(OnlineProblem::getAppId,cmd.getUserAware().getMchAppId())
                .eq(OnlineProblem::getMchId,cmd.getUserAware().getMchId())
                .like(!StrUtil.isBlankOrUndefined(cmd.getProblem()),OnlineProblem::getProblem, cmd.getProblem()).list();
        if(list.size()>0) {
            return SingleResponse.of(list);
        }else {
            List<CallCenter> callCenters = callCenterService.lambdaQuery()
                    .eq(CallCenter::getDeleteFlag, 0)
//                    .eq(CallCenter::getAppId, cmd.getUserAware().getMchAppId())
                    .eq(CallCenter::getMchId, cmd.getUserAware().getMchId()).list();
            List<OnlineProblem> defaultProblem=new ArrayList<>();
            OnlineProblem one = new OnlineProblem();
            if(callCenters.size()>0) {
                one.setResult("请联系客服" + callCenters.get(0).getPhone() + "(工作时间)");
            }
            defaultProblem.add(one);
            return SingleResponse.of(defaultProblem);
        }
    }
}
