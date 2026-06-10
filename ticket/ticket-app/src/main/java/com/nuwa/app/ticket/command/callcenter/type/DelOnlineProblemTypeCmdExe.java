package com.nuwa.app.ticket.command.callcenter.type;

import com.alibaba.cola.dto.SingleResponse;
import com.nuwa.client.ticket.dto.clientobject.callcenter.type.DelOnlineProblemTypeCmd;
import com.nuwa.framework.cola.starter.cmd.AbstractCmdExe;
import com.nuwa.infrastructure.ticket.database.callcenter.entity.OnlineProblemType;
import com.nuwa.infrastructure.ticket.database.callcenter.service.OnlineProblemTypeService;
import com.nuwa.infrastructure.ticket.enums.ErrorEnum;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Slf4j
@Component
public class DelOnlineProblemTypeCmdExe extends AbstractCmdExe<DelOnlineProblemTypeCmd, SingleResponse> {

    @Autowired
    private OnlineProblemTypeService onlineProblemTypeService;
    @Override
    protected SingleResponse handle(DelOnlineProblemTypeCmd cmd) {
        OnlineProblemType one = onlineProblemTypeService.lambdaQuery().eq(OnlineProblemType::getId, cmd.getId()).one();
        if(one.getParentId().toString().equals("0")){
            List<OnlineProblemType> list = onlineProblemTypeService.lambdaQuery().eq(OnlineProblemType::getParentId, cmd.getId()).list();
            if(list.size()>0){
                return SingleResponse.buildFailure(ErrorEnum.DEL_ONLINETYPE_FAILED.getErrCode(), ErrorEnum.DEL_ONLINETYPE_FAILED.getErrDesc());
            }
        }
        boolean b = one.deleteById();
        return b? SingleResponse.buildSuccess():SingleResponse.buildFailure(ErrorEnum.DATA_FAIL.getErrCode(), ErrorEnum.DATA_FAIL.getErrDesc());
    }
}
