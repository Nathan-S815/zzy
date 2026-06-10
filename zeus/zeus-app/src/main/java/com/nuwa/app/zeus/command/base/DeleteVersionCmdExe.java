package com.nuwa.app.zeus.command.base;

import cn.hutool.core.bean.BeanUtil;
import com.alibaba.cola.dto.SingleResponse;
import com.nuwa.client.zeus.dto.clientobject.base.CreateVersionCmd;
import com.nuwa.client.zeus.dto.clientobject.base.DeleteVersionCmd;
import com.nuwa.framework.cola.starter.cmd.AbstractCmdExe;
import com.nuwa.infrastructure.zeus.database.base.entity.PlatUpgrade;
import com.nuwa.infrastructure.zeus.database.base.entity.PlatUpgradeDetails;
import com.nuwa.infrastructure.zeus.database.base.service.PlatUpgradeDetailsService;
import com.nuwa.infrastructure.zeus.database.base.service.PlatUpgradeService;
import com.nuwa.infrastructure.zeus.enums.DeleteFlagEnum;
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
public class DeleteVersionCmdExe extends AbstractCmdExe<DeleteVersionCmd, SingleResponse> {

    @Autowired
    private PlatUpgradeDetailsService platUpgradeDetailsService;

    @Autowired
    private PlatUpgradeService platUpgradeService;

    @Override
    protected SingleResponse handle(DeleteVersionCmd cmd) {
        platUpgradeService.lambdaUpdate().set(PlatUpgrade::getDeleteFlag, DeleteFlagEnum.DELETE.getCode())
                .in(PlatUpgrade::getId,cmd.getId())
                .update();
        platUpgradeDetailsService.lambdaUpdate().set(PlatUpgradeDetails::getDeleteFlag, DeleteFlagEnum.DELETE.getCode())
                .in(PlatUpgradeDetails::getPlatUpgradeId,cmd.getId())
                .update();
        return SingleResponse.buildSuccess();
    }
}
