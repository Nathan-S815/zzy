package com.nuwa.app.ticket.command.activity.query;

import com.alibaba.cola.dto.SingleResponse;
import com.nuwa.client.ticket.dto.clientobject.activity.qry.CultureActivityCategoryQry;
import com.nuwa.framework.cola.starter.cmd.AbstractQryExe;
import com.nuwa.infrastructure.ticket.database.activity.service.CultureActivityCategoryService;
import com.nuwa.infrastructure.ticket.enums.ErrorEnum;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Slf4j
@Component
public class ActivityCategoryQryExe extends AbstractQryExe<CultureActivityCategoryQry, SingleResponse> {

    @Autowired
    private CultureActivityCategoryService cultureActivityCategoryService;

    @Override
    protected SingleResponse handle(CultureActivityCategoryQry cmd) {
        if(Objects.isNull(cmd.getId())){
            return ErrorEnum.MISSING_REQUIRED_PARAMS.buildFailure();
        }
        return SingleResponse.of(cultureActivityCategoryService.getById(cmd.getId()));
    }

}
