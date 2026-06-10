package com.nuwa.app.zeus.command.base.qry;

import cn.hutool.core.bean.BeanUtil;
import com.alibaba.cola.dto.SingleResponse;
import com.nuwa.app.zeus.util.TreeUtil;
import com.nuwa.app.zeus.vo.TreeNode;
import com.nuwa.client.zeus.dto.clientobject.base.qry.GettingStartedTreeQry;
import com.nuwa.client.zeus.dto.clientobject.base.qry.PlatUpgradeListQry;
import com.nuwa.framework.cola.starter.cmd.AbstractCmdExe;
import com.nuwa.infrastructure.zeus.database.base.entity.GettingStarted;
import com.nuwa.infrastructure.zeus.database.base.entity.PlatUpgrade;
import com.nuwa.infrastructure.zeus.database.base.entity.PlatUpgradeDetails;
import com.nuwa.infrastructure.zeus.database.base.service.GettingStartedService;
import com.nuwa.infrastructure.zeus.database.base.service.PlatUpgradeDetailsService;
import com.nuwa.infrastructure.zeus.database.base.service.PlatUpgradeService;
import com.nuwa.infrastructure.zeus.enums.DeleteFlagEnum;
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
public class StartLabelTreeQryExe extends AbstractCmdExe<GettingStartedTreeQry, SingleResponse> {

    @Autowired
    private GettingStartedService gettingStartedService;

    @Override
    protected SingleResponse handle(GettingStartedTreeQry cmd) {
        List<StartLabelVO> voList = gettingStartedService.lambdaQuery()
                .eq(GettingStarted::getDeleteFlag, DeleteFlagEnum.NORMAL.getCode())
                .eq(GettingStarted::getType, "path")
                .list().stream().map(x -> {
                    StartLabelVO vo = new StartLabelVO();
                    vo.setId(x.getId().intValue());
                    vo.setParentId(x.getPid().intValue());
                    vo.setLabel(x.getLabel());
                    return vo;
                }).collect(Collectors.toList());
        List<StartLabelVO> tree = TreeUtil.bulid(voList, -1);
        return SingleResponse.of(tree);
    }

    @Data
    public static class StartLabelVO extends TreeNode {
        @ApiModelProperty("标签")
        private String label;
        private boolean show = false;
    }
}
