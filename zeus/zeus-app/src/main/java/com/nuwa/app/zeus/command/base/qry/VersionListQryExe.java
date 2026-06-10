package com.nuwa.app.zeus.command.base.qry;

import cn.hutool.core.bean.BeanUtil;
import com.alibaba.cola.dto.SingleResponse;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.Version;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.nuwa.client.zeus.dto.clientobject.base.qry.PlatUpgradeListQry;
import com.nuwa.client.zeus.dto.clientobject.base.qry.PlatUpgradePageQry;
import com.nuwa.framework.cola.starter.cmd.AbstractCmdExe;
import com.nuwa.infrastructure.zeus.database.base.entity.PlatUpgrade;
import com.nuwa.infrastructure.zeus.database.base.entity.PlatUpgradeDetails;
import com.nuwa.infrastructure.zeus.database.base.param.PlatUpgradePageParam;
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
public class VersionListQryExe extends AbstractCmdExe<PlatUpgradeListQry, SingleResponse<List<VersionListQryExe.PlatUpgradeVO>>> {

    @Autowired
    private PlatUpgradeService platUpgradeService;

    @Autowired
    private PlatUpgradeDetailsService platUpgradeDetailsService;

    @Override
    protected SingleResponse<List<PlatUpgradeVO>> handle(PlatUpgradeListQry cmd) {
        List<PlatUpgradeVO> result = platUpgradeService.lambdaQuery()
                .eq(PlatUpgrade::getDeleteFlag, DeleteFlagEnum.NORMAL.getCode())
                .orderByDesc(PlatUpgrade::getCreateTime)
                .list().stream().map(x -> {
                    PlatUpgradeVO vo = new PlatUpgradeVO();
                    BeanUtil.copyProperties(x, vo);
                    List<String> items = platUpgradeDetailsService.lambdaQuery()
                            .eq(PlatUpgradeDetails::getPlatUpgradeId, x.getId())
                            .eq(PlatUpgradeDetails::getDeleteFlag, DeleteFlagEnum.NORMAL.getCode())
                            .orderByAsc(PlatUpgradeDetails::getSortNum)
                            .list().stream().map(PlatUpgradeDetails::getItem).collect(Collectors.toList());
                    vo.setItems(items);
                    return vo;
                }).collect(Collectors.toList());
        return SingleResponse.of(result);
    }

    @Data
    public static class PlatUpgradeVO{
        @ApiModelProperty("主键")
        private Long id;
        @ApiModelProperty("标题")
        private String title;
        @ApiModelProperty("升级时间")
        private String upgradeDate;
        @ApiModelProperty("版本号")
        private String version;
        @ApiModelProperty("详情")
        private List<String> items;

    }
}
