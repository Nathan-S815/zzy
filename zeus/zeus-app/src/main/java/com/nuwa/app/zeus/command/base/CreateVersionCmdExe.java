package com.nuwa.app.zeus.command.base;

import cn.hutool.core.bean.BeanUtil;
import com.alibaba.cola.dto.SingleResponse;
import com.nuwa.app.zeus.service.GroupBiz;
import com.nuwa.client.zeus.dto.clientobject.base.CreateBaseGroupCmd;
import com.nuwa.client.zeus.dto.clientobject.base.CreateVersionCmd;
import com.nuwa.framework.cola.starter.cmd.AbstractCmdExe;
import com.nuwa.infrastructure.zeus.database.base.entity.BaseGroup;
import com.nuwa.infrastructure.zeus.database.base.entity.PlatUpgrade;
import com.nuwa.infrastructure.zeus.database.base.entity.PlatUpgradeDetails;
import com.nuwa.infrastructure.zeus.database.base.service.PlatUpgradeDetailsService;
import com.nuwa.infrastructure.zeus.database.base.service.PlatUpgradeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Date;
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
public class CreateVersionCmdExe extends AbstractCmdExe<CreateVersionCmd, SingleResponse> {

    @Autowired
    private PlatUpgradeDetailsService platUpgradeDetailsService;

    @Autowired
    private PlatUpgradeService platUpgradeService;

    @Override
    protected SingleResponse handle(CreateVersionCmd cmd) {
        PlatUpgrade platUpgrade = new PlatUpgrade();
        BeanUtil.copyProperties(cmd,platUpgrade);
        platUpgradeService.save(platUpgrade);
        AtomicInteger index=new AtomicInteger(1);
        List<PlatUpgradeDetails> details = cmd.getItems().stream().map(x -> {
            PlatUpgradeDetails platUpgradeDetails = new PlatUpgradeDetails();
            platUpgradeDetails.setPlatUpgradeId(platUpgrade.getId());
            platUpgradeDetails.setSortNum(index.getAndIncrement());
            platUpgradeDetails.setItem(x);
            return platUpgradeDetails;
        }).collect(Collectors.toList());
        platUpgradeDetailsService.saveBatch(details);
        return SingleResponse.buildSuccess();
    }
}
