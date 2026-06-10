package com.nuwa.app.ticket.command.callcenter.type;

import cn.hutool.core.bean.BeanUtil;
import com.alibaba.cola.dto.SingleResponse;
import com.nuwa.client.ticket.dto.clientobject.callcenter.type.CreateOnlineProblemTypeCmd;
import com.nuwa.client.ticket.dto.clientobject.callcenter.type.co.CreateOnlineProblemTypeCO;
import com.nuwa.framework.cola.starter.cmd.AbstractCmdExe;
import com.nuwa.infrastructure.ticket.database.callcenter.entity.OnlineProblemType;
import com.nuwa.infrastructure.ticket.database.callcenter.service.OnlineProblemTypeService;
import com.nuwa.infrastructure.ticket.enums.ErrorEnum;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Date;

@Slf4j
@Component
public class CreateOnlineProblemTypeCmdExe  extends AbstractCmdExe<CreateOnlineProblemTypeCmd, SingleResponse> {
    @Autowired
    private OnlineProblemTypeService onlineProblemTypeService;
    @Override
    protected SingleResponse handle(CreateOnlineProblemTypeCmd cmd) {
        CreateOnlineProblemTypeCO createOnlineProblemTypeCO = cmd.getCreateOnlineProblemTypeCO();
        OnlineProblemType onlineProblemType = new OnlineProblemType();
        BeanUtil.copyProperties(createOnlineProblemTypeCO,onlineProblemType);
        onlineProblemType.setCreateTime(new Date());
        onlineProblemType.setAppId(cmd.getAppId());
        onlineProblemType.setMchId(cmd.getUserAware().getMchId());
        boolean save;
        if(onlineProblemType.getId() != null){
            save = onlineProblemTypeService.updateById(onlineProblemType);
        }else{
            save = onlineProblemTypeService.save(onlineProblemType);
        }
        return save? SingleResponse.buildSuccess():SingleResponse.buildFailure(ErrorEnum.DATA_FAIL.getErrCode(), ErrorEnum.DATA_FAIL.getErrDesc());
    }
}
