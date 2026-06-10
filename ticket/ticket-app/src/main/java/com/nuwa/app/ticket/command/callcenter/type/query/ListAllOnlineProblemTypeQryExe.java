package com.nuwa.app.ticket.command.callcenter.type.query;

import com.alibaba.cola.dto.SingleResponse;
import com.nuwa.client.ticket.dto.clientobject.callcenter.type.ListAllOnlineProblemTypeCmd;
import com.nuwa.framework.cola.starter.cmd.AbstractQryExe;
import com.nuwa.infrastructure.ticket.database.callcenter.entity.OnlineProblemType;
import com.nuwa.infrastructure.ticket.database.callcenter.service.OnlineProblemTypeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author hy
 */
@Slf4j
@Component
public class ListAllOnlineProblemTypeQryExe extends AbstractQryExe<ListAllOnlineProblemTypeCmd, SingleResponse> {
    @Autowired
    private OnlineProblemTypeService onlineProblemTypeService;
    @Override
    protected SingleResponse handle(ListAllOnlineProblemTypeCmd cmd) {
        List<OnlineProblemType> list = onlineProblemTypeService.lambdaQuery().eq(OnlineProblemType::getDeleteFlag, 0)
//                .eq(OnlineProblemType::getAppId,cmd.getAppId())
                .eq(OnlineProblemType::getMchId,cmd.getUserAware().getMchId())
                .eq(OnlineProblemType::getParentId,0)
                .list();
        return SingleResponse.of(list);
    }
}
