package com.nuwa.app.ticket.command.activity.query;

import com.alibaba.cola.dto.SingleResponse;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.nuwa.client.ticket.dto.clientobject.activity.qry.CultureActivityApplyPageQry;
import com.nuwa.framework.cola.starter.cmd.AbstractQryExe;
import com.nuwa.infrastructure.ticket.database.activity.entity.CultureActivity;
import com.nuwa.infrastructure.ticket.database.activity.entity.CultureActivityApply;
import com.nuwa.infrastructure.ticket.database.activity.param.CultureActivityApplyPageParam;
import com.nuwa.infrastructure.ticket.database.activity.service.CultureActivityApplyService;
import com.nuwa.infrastructure.ticket.database.activity.service.CultureActivityService;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class ActivityApplyPageQryExe extends AbstractQryExe<CultureActivityApplyPageQry, SingleResponse> {

    @Autowired
    private CultureActivityApplyService cultureActivityApplyService;

    @Autowired
    private CultureActivityService cultureActivityService;

    @Override
    protected SingleResponse handle(CultureActivityApplyPageQry cmd) {
        IPage<CultureActivityApplyVO> cultureActivityApplyVOIPage = cultureActivityApplyService.paginateAndConvert(new CultureActivityApplyPageParam(cmd), CultureActivityApplyVO::toVO);
        cultureActivityApplyVOIPage.getRecords().stream().forEach(x->{
            CultureActivity activity = cultureActivityService.getById(x.getActivityApply().getActivityId());
            x.setActivity(activity);
        });
        return SingleResponse.of(cultureActivityApplyVOIPage);
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
