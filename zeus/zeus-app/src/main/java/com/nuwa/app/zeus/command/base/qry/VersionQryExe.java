package com.nuwa.app.zeus.command.base.qry;

import cn.hutool.core.bean.BeanUtil;
import com.alibaba.cola.dto.SingleResponse;
import com.nuwa.client.zeus.dto.clientobject.base.qry.PlatUpgradeQry;
import com.nuwa.framework.cola.starter.cmd.AbstractCmdExe;
import com.nuwa.infrastructure.zeus.database.base.entity.PlatUpgrade;
import com.nuwa.infrastructure.zeus.database.base.entity.PlatUpgradeDetails;
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
public class VersionQryExe extends AbstractCmdExe<PlatUpgradeQry, SingleResponse<VersionQryExe.PlatUpgradeVO>> {

    @Autowired
    private PlatUpgradeDetailsService platUpgradeDetailsService;

    @Autowired
    private PlatUpgradeService platUpgradeService;

    @Override
    protected SingleResponse<PlatUpgradeVO> handle(PlatUpgradeQry cmd) {
        PlatUpgradeVO vo = new PlatUpgradeVO();
        PlatUpgrade platUpgrade = platUpgradeService.getById(cmd);
        BeanUtil.copyProperties(platUpgrade,vo);
        List<String> items = platUpgradeDetailsService.lambdaQuery()
                .eq(PlatUpgradeDetails::getPlatUpgradeId, cmd.getId())
                .orderByAsc(PlatUpgradeDetails::getSortNum)
                .list().stream().map(PlatUpgradeDetails::getItem).collect(Collectors.toList());
        vo.setItems(items);
        return SingleResponse.of(vo);
    }

    @Data
    public static class PlatUpgradeVO {
        @ApiModelProperty(value = "id")
        private Long id;
        @ApiModelProperty(value = "标题")
        private String title;
        @ApiModelProperty(value = "升级时间")
        private String upgradeDate;
        @ApiModelProperty(value = "版本号")
        private String version;
        @ApiModelProperty(value = "详情")
        private List<String> items;
    }
}
