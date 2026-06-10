package com.nuwa.app.ticket.command.activity.query;

import com.alibaba.cola.dto.SingleResponse;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.nuwa.client.ticket.dto.clientobject.activity.qry.CultureActivityCategoryPageQry;
import com.nuwa.framework.cola.starter.cmd.AbstractQryExe;
import com.nuwa.infrastructure.ticket.database.activity.entity.CultureActivityCategory;
import com.nuwa.infrastructure.ticket.database.activity.param.CultureActivityCategoryPageParam;
import com.nuwa.infrastructure.ticket.database.activity.service.CultureActivityCategoryService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class ActivityCategoryPageQryExe extends AbstractQryExe<CultureActivityCategoryPageQry, SingleResponse> {

    @Autowired
    private CultureActivityCategoryService cultureActivityCategoryService;

    @Override
    protected SingleResponse handle(CultureActivityCategoryPageQry cmd) {
        IPage<CultureActivityCategory> cultureActivityCategoryIPage = cultureActivityCategoryService.paginateByParam(new CultureActivityCategoryPageParam(cmd));
        return SingleResponse.of(cultureActivityCategoryIPage);
    }
}
