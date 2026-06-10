package com.nuwa.app.ticket.command.mall;

import com.alibaba.cola.dto.SingleResponse;
import com.baomidou.mybatisplus.extension.service.additional.update.impl.LambdaUpdateChainWrapper;

import com.nuwa.client.ticket.dto.clientobject.mall.DelMallProductCmd;
import com.nuwa.framework.cola.starter.cmd.AbstractCmdExe;
import com.nuwa.infrastructure.ticket.database.mall.entity.MallProduct;
import com.nuwa.infrastructure.ticket.database.mall.service.MallProductService;
import com.nuwa.infrastructure.ticket.enums.DeleteFlagEnum;
import com.nuwa.infrastructure.ticket.enums.ErrorEnum;
import com.nuwa.infrastructure.ticket.util.SerializUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Slf4j
@Component
public class DelMallProductCmdExe extends AbstractCmdExe<DelMallProductCmd, SingleResponse> {

    @Autowired
    private MallProductService productService;

    @Override
    protected SingleResponse handle(DelMallProductCmd cmd) {
        List<String> ids = SerializUtil.strToList(cmd.getIds());
        if (ids == null || ids.size() == 0) {
            return ErrorEnum.MISSING_REQUIRED_PARAMS.buildFailure();
        }
        LambdaUpdateChainWrapper<MallProduct> lambdaUpdate = productService.lambdaUpdate()
                .set(MallProduct::getDeleteFlag, DeleteFlagEnum.DELETE.getCode())
                .eq(true, MallProduct::getAppId, cmd.getAppId())
                .eq(MallProduct::getMchId,cmd.getUserAware().getMchId())
                .in(true, MallProduct::getId, ids);
        return lambdaUpdate.update() ? SingleResponse.buildSuccess() : ErrorEnum.FAILED.buildFailure();
    }
}
