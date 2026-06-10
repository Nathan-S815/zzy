package com.nuwa.app.zeus.command.mch;

import com.alibaba.cola.dto.SingleResponse;
import com.nuwa.client.zeus.dto.clientobject.mch.EnableMerchantChildCmd;
import com.nuwa.client.zeus.dto.clientobject.mch.ForbidMerchantChildCmd;
import com.nuwa.framework.cola.starter.cmd.AbstractCmdExe;
import com.nuwa.infrastructure.zeus.database.base.entity.BaseUser;
import com.nuwa.infrastructure.zeus.database.base.service.BaseUserService;
import com.nuwa.infrastructure.zeus.enums.ErrorEnum;
import com.nuwa.infrastructure.zeus.util.SerializUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * OpenMerchantAppCmdExe 开通应用
 *
 * @author hy
 * @date 2021/6/3 9:52
 * @since 1.0.0
 */
@Slf4j
@Component
public class ForbidMerchantChildCmdExe extends AbstractCmdExe<ForbidMerchantChildCmd, SingleResponse> {

    @Autowired
    private BaseUserService baseUserService;

    @Override
    protected SingleResponse handle(ForbidMerchantChildCmd cmd) {
        try {
            baseUserService.lambdaUpdate()
                    .in(BaseUser::getId, SerializUtil.strToList(cmd.getId()))
                    .set(BaseUser::getStatus, 1)
                    .update();
            return ErrorEnum.DATA_SUCCESS.buildSuccess();
        } catch (Exception e) {
            return ErrorEnum.DATA_FAIL.buildSuccess();
        }

    }
}
