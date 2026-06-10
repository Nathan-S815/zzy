package com.nuwa.app.ticket.command.activity.query;

import com.alibaba.cola.dto.SingleResponse;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.nuwa.client.ticket.dto.clientobject.activity.qry.UserCultureActivityV2PageQry;
import com.nuwa.framework.cola.starter.cmd.AbstractQryExe;
import com.nuwa.infrastructure.ticket.database.activity.entity.CultureActivity;
import com.nuwa.infrastructure.ticket.database.activity.param.UserCultureActivityV2PageParam;
import com.nuwa.infrastructure.ticket.database.activity.service.CultureActivityService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class ActivityUserV2PageQryExe extends AbstractQryExe<UserCultureActivityV2PageQry, SingleResponse> {

    @Autowired
    private CultureActivityService cultureActivityService;

    @Override
    protected SingleResponse handle(UserCultureActivityV2PageQry cmd) {
        IPage<CultureActivity> cultureActivityIPage = cultureActivityService.paginateByParam(new UserCultureActivityV2PageParam(cmd));
        return SingleResponse.of(cultureActivityIPage);
    }
}
