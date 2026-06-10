package com.nuwa.app.ticket.command.activity;

import cn.hutool.core.bean.BeanUtil;
import com.alibaba.cola.dto.SingleResponse;
import com.baomidou.mybatisplus.extension.service.additional.update.impl.LambdaUpdateChainWrapper;
import com.nuwa.client.ticket.dto.clientobject.activity.OnActivityCategoryCmd;
import com.nuwa.framework.cola.starter.cmd.AbstractCmdExe;
import com.nuwa.infrastructure.ticket.database.activity.entity.CultureActivityCategory;
import com.nuwa.infrastructure.ticket.database.activity.service.CultureActivityCategoryService;
import com.nuwa.infrastructure.ticket.enums.ErrorEnum;
import com.nuwa.infrastructure.ticket.enums.PublishStatusEnum;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class OnActivityCategoryCmdExe extends AbstractCmdExe<OnActivityCategoryCmd, SingleResponse> {

    @Autowired
    private CultureActivityCategoryService cultureActivityCategoryService;

    @Override
    protected SingleResponse handle(OnActivityCategoryCmd cmd) {
        if (BeanUtil.isEmpty(cmd.getId())) {
            return ErrorEnum.MISSING_REQUIRED_PARAMS.buildFailure();
        }
        LambdaUpdateChainWrapper<CultureActivityCategory> lambdaUpdate = cultureActivityCategoryService.lambdaUpdate()
                .set(CultureActivityCategory::getPublishStatus, PublishStatusEnum.ON.getCode())
                .in(true,CultureActivityCategory::getId, cmd.getId());
        return lambdaUpdate.update() ? SingleResponse.buildSuccess() : ErrorEnum.FAILED.buildFailure();
    }
}
