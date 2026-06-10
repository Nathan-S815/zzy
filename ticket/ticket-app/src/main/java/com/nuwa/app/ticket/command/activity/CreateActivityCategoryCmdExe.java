package com.nuwa.app.ticket.command.activity;

import com.alibaba.cola.dto.SingleResponse;
import com.nuwa.client.ticket.dto.clientobject.activity.CreateActivityCategoryCmd;
import com.nuwa.framework.cola.starter.cmd.AbstractCmdExe;
import com.nuwa.infrastructure.ticket.database.activity.entity.CultureActivityCategory;
import com.nuwa.infrastructure.ticket.database.activity.service.CultureActivityCategoryService;
import com.nuwa.infrastructure.ticket.enums.ErrorEnum;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class CreateActivityCategoryCmdExe extends AbstractCmdExe<CreateActivityCategoryCmd, SingleResponse> {

    @Autowired
    private CultureActivityCategoryService cultureActivityCategoryService;

    @Override
    protected SingleResponse handle(CreateActivityCategoryCmd cmd) {
        CultureActivityCategory entity = new CultureActivityCategory();
        entity.setCategoryName(cmd.getCategoryName());
        entity.setMchId(cmd.getUserAware().getMchId());
        entity.setAppId(cmd.getAppId());
        return cultureActivityCategoryService.save(entity) ? SingleResponse.of(entity) : ErrorEnum.ACTIVITY_ADD_FAILED.buildFailure();
    }
}
