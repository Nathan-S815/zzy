package com.nuwa.app.ticket.command.activity;

import cn.hutool.core.bean.BeanUtil;
import com.alibaba.cola.dto.SingleResponse;
import com.nuwa.client.ticket.dto.clientobject.activity.EditActivityCategoryCmd;
import com.nuwa.framework.cola.starter.cmd.AbstractCmdExe;
import com.nuwa.infrastructure.ticket.database.activity.entity.CultureActivityCategory;
import com.nuwa.infrastructure.ticket.database.activity.service.CultureActivityCategoryService;
import com.nuwa.infrastructure.ticket.enums.ErrorEnum;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Slf4j
@Component
public class EditActivityCategoryCmdExe extends AbstractCmdExe<EditActivityCategoryCmd, SingleResponse> {

    @Autowired
    private CultureActivityCategoryService cultureActivityCategoryService;

    @Override
    protected SingleResponse handle(EditActivityCategoryCmd cmd) {
        if (BeanUtil.isEmpty(cmd.getId())) {
            return ErrorEnum.MISSING_REQUIRED_PARAMS.buildFailure();
        }
        CultureActivityCategory one = cultureActivityCategoryService.lambdaQuery().eq(CultureActivityCategory::getMchId, cmd.getUserAware().getMchId()).eq(CultureActivityCategory::getAppId, cmd.getAppId()).eq(CultureActivityCategory::getId, cmd.getId()).one();
        if (Objects.isNull(one)) {
            return ErrorEnum.FAILED.buildFailure();
        }
        CultureActivityCategory entity = new CultureActivityCategory();
        BeanUtil.copyProperties(cmd,entity);
        return cultureActivityCategoryService.updateById(entity) ? SingleResponse.of(entity) : ErrorEnum.FAILED.buildFailure();
    }
}
