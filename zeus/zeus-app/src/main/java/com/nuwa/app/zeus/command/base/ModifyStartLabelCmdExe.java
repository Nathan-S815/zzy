package com.nuwa.app.zeus.command.base;

import com.alibaba.cola.dto.SingleResponse;
import com.nuwa.client.zeus.dto.clientobject.base.ModifyStartContentCmd;
import com.nuwa.client.zeus.dto.clientobject.base.ModifyStartLabelCmd;
import com.nuwa.framework.cola.starter.cmd.AbstractCmdExe;
import com.nuwa.infrastructure.zeus.database.base.entity.GettingStarted;
import com.nuwa.infrastructure.zeus.database.base.service.GettingStartedService;
import com.nuwa.infrastructure.zeus.enums.ErrorEnum;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * CreateBaseGroupCmdExe
 *
 * @author hy
 * @date 2021/5/25 17:19
 * @since 1.0.0
 */
@Slf4j
@Component
public class ModifyStartLabelCmdExe extends AbstractCmdExe<ModifyStartLabelCmd, SingleResponse> {

    @Autowired
    private GettingStartedService gettingStartedService;

    @Override
    protected SingleResponse handle(ModifyStartLabelCmd cmd) {
        boolean update = gettingStartedService.lambdaUpdate().eq(GettingStarted::getId, cmd.getId())
                .set(GettingStarted::getLabel, cmd.getLabel())
                .update();
        if(update){
            return SingleResponse.buildSuccess();
        }
        return ErrorEnum.DATA_FAIL.buildFailure();
    }
}
