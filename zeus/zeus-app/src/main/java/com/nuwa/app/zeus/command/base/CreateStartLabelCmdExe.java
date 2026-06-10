package com.nuwa.app.zeus.command.base;

import cn.hutool.core.bean.BeanUtil;
import com.alibaba.cola.dto.SingleResponse;
import com.nuwa.client.zeus.dto.clientobject.base.CreateStartLabelCmd;
import com.nuwa.client.zeus.dto.clientobject.base.CreateVersionCmd;
import com.nuwa.framework.cola.starter.cmd.AbstractCmdExe;
import com.nuwa.infrastructure.zeus.database.base.entity.GettingStarted;
import com.nuwa.infrastructure.zeus.database.base.entity.PlatUpgrade;
import com.nuwa.infrastructure.zeus.database.base.entity.PlatUpgradeDetails;
import com.nuwa.infrastructure.zeus.database.base.service.GettingStartedService;
import com.nuwa.infrastructure.zeus.database.base.service.PlatUpgradeDetailsService;
import com.nuwa.infrastructure.zeus.database.base.service.PlatUpgradeService;
import com.nuwa.infrastructure.zeus.enums.ErrorEnum;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

/**
 * CreateBaseGroupCmdExe
 *
 * @author hy
 * @date 2021/5/25 17:19
 * @since 1.0.0
 */
@Slf4j
@Component
public class CreateStartLabelCmdExe extends AbstractCmdExe<CreateStartLabelCmd, SingleResponse> {

    @Autowired
    private GettingStartedService gettingStartedService;

    @Override
    protected SingleResponse handle(CreateStartLabelCmd cmd) {
        GettingStarted gettingStarted = new GettingStarted();
        gettingStarted.setLabel(cmd.getLabel());
        gettingStarted.setPid(cmd.getPid());
        gettingStarted.setType("path");
        boolean save = gettingStartedService.save(gettingStarted);
        if(save){
            return SingleResponse.of(gettingStarted);
        }
        return ErrorEnum.DATA_FAIL.buildFailure();
    }
}
