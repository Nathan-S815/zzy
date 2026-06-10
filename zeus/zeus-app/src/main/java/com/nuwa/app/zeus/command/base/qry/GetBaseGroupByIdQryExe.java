package com.nuwa.app.zeus.command.base.qry;

import com.alibaba.cola.dto.SingleResponse;
import com.nuwa.client.zeus.dto.clientobject.auth.qry.GetBaseGroupByIdQry;
import com.nuwa.framework.cola.starter.cmd.AbstractCmdExe;
import com.nuwa.infrastructure.zeus.database.base.entity.BaseGroup;
import com.nuwa.infrastructure.zeus.database.base.service.BaseGroupService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * GetBaseGroupByIdQryExe
 *
 * @author hy
 * @date 2021/5/25 16:32
 * @since 1.0.0
 */
@Slf4j
@Component
public class GetBaseGroupByIdQryExe extends AbstractCmdExe<GetBaseGroupByIdQry, SingleResponse<BaseGroup>> {

    @Autowired
    private BaseGroupService baseGroupService;

    @Override
    protected SingleResponse<BaseGroup> handle(GetBaseGroupByIdQry cmd) {
        return SingleResponse.of(baseGroupService.getById(cmd.getGroupId()));
    }
}
