package com.nuwa.app.ticket.command.activity.query;

import com.alibaba.cola.dto.SingleResponse;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.nuwa.client.ticket.dto.clientobject.activity.qry.UserCultureActivityPageQry;
import com.nuwa.framework.cola.starter.cmd.AbstractQryExe;
import com.nuwa.infrastructure.ticket.database.activity.entity.CultureActivity;
import com.nuwa.infrastructure.ticket.database.activity.param.UserCultureActivityPageParam;
import com.nuwa.infrastructure.ticket.database.activity.service.CultureActivityService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class ActivityUserPageQryExe extends AbstractQryExe<UserCultureActivityPageQry, SingleResponse> {

    @Autowired
    private CultureActivityService cultureActivityService;

    @Override
    protected SingleResponse handle(UserCultureActivityPageQry cmd) {
        IPage<CultureActivity> cultureActivityIPage = cultureActivityService.paginateByParam(new UserCultureActivityPageParam(cmd));
        return SingleResponse.of(cultureActivityIPage);
    }
}
