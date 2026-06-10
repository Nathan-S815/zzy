package com.nuwa.app.zeus.command.mch;

import com.alibaba.cola.dto.SingleResponse;
import com.nuwa.app.zeus.service.MerchantBiz;
import com.nuwa.client.zeus.dto.clientobject.mch.OpenMerchantAppCmd;
import com.nuwa.client.zeus.dto.clientobject.mch.ResetMerchantPasswordCmd;
import com.nuwa.framework.cola.starter.cmd.AbstractCmdExe;
import com.nuwa.infrastructure.zeus.database.base.entity.BaseUser;
import com.nuwa.infrastructure.zeus.database.base.service.BaseUserService;
import com.nuwa.infrastructure.zeus.enums.DeleteFlagEnum;
import com.nuwa.infrastructure.zeus.enums.ErrorEnum;
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
public class ResetMerchantPasswordCmdExe extends AbstractCmdExe<ResetMerchantPasswordCmd, SingleResponse> {

    @Autowired
    private BaseUserService baseUserService;

    @Override
    protected SingleResponse handle(ResetMerchantPasswordCmd cmd) {
        try {
            baseUserService.lambdaUpdate()
                    .eq(BaseUser::getTenantId, cmd.getMchId())
                    .eq(BaseUser::getType, 1)
                    .set(BaseUser::getPassword, "e10adc3949ba59abbe56e057f20f883e")
                    .update();
            return ErrorEnum.DATA_SUCCESS.buildSuccess();
        } catch (Exception e) {
            return ErrorEnum.DATA_FAIL.buildSuccess();
        }
    }
}
