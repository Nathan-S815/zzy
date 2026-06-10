package com.nuwa.app.ticket.command.activity.query;

import cn.hutool.core.bean.BeanUtil;
import com.alibaba.cola.dto.SingleResponse;
import com.nuwa.client.ticket.dto.clientobject.activity.qry.CultureActivityApplyQry;
import com.nuwa.framework.cola.starter.cmd.AbstractQryExe;
import com.nuwa.infrastructure.ticket.database.activity.entity.CultureActivity;
import com.nuwa.infrastructure.ticket.database.activity.entity.CultureActivityApply;
import com.nuwa.infrastructure.ticket.database.activity.service.CultureActivityApplyService;
import com.nuwa.infrastructure.ticket.database.activity.service.CultureActivityService;
import com.nuwa.infrastructure.ticket.enums.ErrorEnum;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.Objects;

@Slf4j
@Component
public class ActivityApplyQryExe extends AbstractQryExe<CultureActivityApplyQry, SingleResponse> {

    @Autowired
    private CultureActivityApplyService cultureActivityApplyService;

    @Autowired
    private CultureActivityService cultureActivityService;

    @Override
    protected SingleResponse handle(CultureActivityApplyQry cmd) {
        if(Objects.isNull(cmd.getId())){
            return ErrorEnum.MISSING_REQUIRED_PARAMS.buildFailure();
        }

        CultureActivityApply apply = cultureActivityApplyService.getById(cmd.getId());
        CultureActivity activity = cultureActivityService.getById(apply.getActivityId());
        ApplyVO vo = new ApplyVO();
        BeanUtil.copyProperties(apply,vo);
        vo.setActivityTitle(activity.getActivityTitle());
        vo.setHoldTimeStart(activity.getHoldTimeStart());
        vo.setHoldTimeEnd(activity.getHoldTimeEnd());
        vo.setAddress(activity.getAddress());
        return SingleResponse.of(vo);
    }

    @Data
    public static class ApplyVO{
        private Integer peopleNum;
        private String contactsName;
        private String idCard;
        private String contactsMobile;
        private Date createTime;
        private String activityTitle;
        private Date holdTimeStart;
        private Date holdTimeEnd;
        private String address;
    }

}
