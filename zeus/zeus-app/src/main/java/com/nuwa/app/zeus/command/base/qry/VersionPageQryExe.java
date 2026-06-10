package com.nuwa.app.zeus.command.base.qry;

import cn.hutool.core.bean.BeanUtil;
import com.alibaba.cola.dto.SingleResponse;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.nuwa.client.zeus.dto.clientobject.base.qry.PlatUpgradePageQry;
import com.nuwa.client.zeus.dto.clientobject.base.qry.PlatUpgradeQry;
import com.nuwa.framework.cola.starter.cmd.AbstractCmdExe;
import com.nuwa.infrastructure.zeus.database.base.entity.PlatUpgrade;
import com.nuwa.infrastructure.zeus.database.base.entity.PlatUpgradeDetails;
import com.nuwa.infrastructure.zeus.database.base.param.PlatUpgradePageParam;
import com.nuwa.infrastructure.zeus.database.base.service.PlatUpgradeDetailsService;
import com.nuwa.infrastructure.zeus.database.base.service.PlatUpgradeService;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

/**
 * GetBaseGroupByIdQryExe
 *
 * @author hy
 * @date 2021/5/25 16:32
 * @since 1.0.0
 */
@Slf4j
@Component
public class VersionPageQryExe extends AbstractCmdExe<PlatUpgradePageQry, SingleResponse> {

    @Autowired
    private PlatUpgradeService platUpgradeService;

    @Override
    protected SingleResponse handle(PlatUpgradePageQry cmd) {
        IPage<PlatUpgrade> platUpgradeIPage = platUpgradeService.paginateByParam(new PlatUpgradePageParam(cmd));
        return SingleResponse.of(platUpgradeIPage);
    }
}
