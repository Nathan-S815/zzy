package com.nuwa.app.ticket.command.activity.query;

import com.alibaba.cola.dto.SingleResponse;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.nuwa.client.ticket.dto.clientobject.activity.qry.CultureActivityPageQry;
import com.nuwa.framework.cola.starter.cmd.AbstractQryExe;
import com.nuwa.infrastructure.ticket.database.activity.entity.CultureActivity;
import com.nuwa.infrastructure.ticket.database.activity.param.CultureActivityPageParam;
import com.nuwa.infrastructure.ticket.database.activity.service.CultureActivityService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class ActivityPageQryExe extends AbstractQryExe<CultureActivityPageQry, SingleResponse> {

    @Autowired
    private CultureActivityService cultureActivityService;

    @Override
    protected SingleResponse handle(CultureActivityPageQry cmd) {
        IPage<CultureActivity> cultureActivityIPage = cultureActivityService.paginateByParam(new CultureActivityPageParam(cmd));
        return SingleResponse.of(cultureActivityIPage);
    }
}
