package com.nuwa.app.ticket.command.activity.query;

import com.alibaba.cola.dto.SingleResponse;
import com.nuwa.client.ticket.dto.clientobject.activity.qry.GetUserCultureActivityApplyCmd;
import com.nuwa.framework.cola.starter.cmd.AbstractQryExe;
import com.nuwa.infrastructure.ticket.database.activity.entity.CultureActivity;
import com.nuwa.infrastructure.ticket.database.activity.entity.CultureActivityApply;
import com.nuwa.infrastructure.ticket.database.activity.service.CultureActivityApplyService;
import com.nuwa.infrastructure.ticket.database.activity.service.CultureActivityService;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Slf4j
@Component
public class GetUserCultureActivityApplyCmdExe extends AbstractQryExe<GetUserCultureActivityApplyCmd, SingleResponse> {
    @Autowired
    private CultureActivityApplyService cultureActivityApplyService;

    @Autowired
    private CultureActivityService cultureActivityService;
    @Override
    protected SingleResponse handle(GetUserCultureActivityApplyCmd cmd) {
        CultureActivityApply cultureActivityApply = cultureActivityApplyService.lambdaQuery()
                .eq(Objects.nonNull(cmd.getId()),CultureActivityApply::getId, cmd.getId()).one();
        CultureActivity cultureActivity = cultureActivityService.lambdaQuery()
                .eq(Objects.nonNull(cultureActivityApply.getActivityId()), CultureActivity::getId, cultureActivityApply.getActivityId()).one();
        CultureActivityApplyVO cultureActivityApplyVO = new CultureActivityApplyVO();
        cultureActivityApplyVO.setActivity(cultureActivity);
        cultureActivityApplyVO.setActivityApply(cultureActivityApply);
        return SingleResponse.of(cultureActivityApplyVO);
    }

    @Data
    public static class CultureActivityApplyVO {
        private CultureActivityApply activityApply;
        private CultureActivity activity;

        public static CultureActivityApplyVO toVO(CultureActivityApply activityApply) {
            CultureActivityApplyVO vo = new CultureActivityApplyVO();
            vo.setActivityApply(activityApply);
            return vo;
        }
    }
}
