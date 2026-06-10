package com.nuwa.app.ticket.command.callcenter.type.query;

import com.alibaba.cola.dto.SingleResponse;
import com.nuwa.client.ticket.dto.clientobject.callcenter.type.ListOnlineProblemTypeByParentIdCmd;
import com.nuwa.framework.cola.starter.cmd.AbstractQryExe;
import com.nuwa.infrastructure.ticket.database.callcenter.entity.OnlineProblemType;
import com.nuwa.infrastructure.ticket.database.callcenter.service.OnlineProblemTypeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Slf4j
@Component
public class ListOnlineProblemTypeByParentIdQryExe extends AbstractQryExe<ListOnlineProblemTypeByParentIdCmd, SingleResponse> {
    @Autowired
    private OnlineProblemTypeService onlineProblemTypeService;
    @Override
    protected SingleResponse handle(ListOnlineProblemTypeByParentIdCmd cmd) {
        List<OnlineProblemType> list = onlineProblemTypeService.lambdaQuery()
                                        .eq(OnlineProblemType::getDeleteFlag, 0)
                                        .eq(OnlineProblemType::getParentId, cmd.getParentId()).list();
        return SingleResponse.of(list);
    }
}
