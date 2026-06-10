package com.nuwa.app.ticket.command.activity.query;

import com.alibaba.cola.dto.SingleResponse;
import com.nuwa.client.ticket.dto.clientobject.activity.qry.GetCultureActivityCategoryQry;
import com.nuwa.framework.cola.starter.cmd.AbstractQryExe;
import com.nuwa.infrastructure.ticket.database.activity.entity.CultureActivityCategory;
import com.nuwa.infrastructure.ticket.database.activity.service.CultureActivityCategoryService;
import com.nuwa.infrastructure.ticket.enums.DeleteFlagEnum;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Slf4j
@Component
public class GetCultureActivityCategoryQryExe extends AbstractQryExe<GetCultureActivityCategoryQry, SingleResponse> {

    @Autowired
    private CultureActivityCategoryService cultureActivityCategoryService;

    @Override
    protected SingleResponse handle(GetCultureActivityCategoryQry cmd) {
        List<CultureActivityCategory> list = cultureActivityCategoryService.lambdaQuery()
                .eq(CultureActivityCategory::getMchId, cmd.getUserAware().getMchId())
                .eq(CultureActivityCategory::getAppId, cmd.getUserAware().getMchAppId())
                .eq(CultureActivityCategory::getDeleteFlag, DeleteFlagEnum.NORMAL.getCode())
                .list();
        return SingleResponse.of(list);
    }

}
