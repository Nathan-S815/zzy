package com.nuwa.app.zeus.command.mch;

import com.alibaba.cola.dto.SingleResponse;
import com.nuwa.client.zeus.dto.clientobject.mch.ForbidMerchantCmd;
import com.nuwa.client.zeus.dto.clientobject.mch.PassMerchantCmd;
import com.nuwa.framework.cola.starter.cmd.AbstractCmdExe;
import com.nuwa.infrastructure.zeus.database.mch.entity.Merchant;
import com.nuwa.infrastructure.zeus.database.mch.service.MerchantService;
import com.nuwa.infrastructure.zeus.enums.AuditStatusEnum;
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
public class ForbidMerchantCmdExe extends AbstractCmdExe<ForbidMerchantCmd, SingleResponse> {

    @Autowired
    private MerchantService merchantService;

    @Override
    protected SingleResponse handle(ForbidMerchantCmd cmd) {
        try {
            merchantService.lambdaUpdate()
                    .eq(Merchant::getMchId, cmd.getMchId())
                    .set(Merchant::getAuditStatus, AuditStatusEnum.NOT_PASS.getCode())
                    .update();
            return ErrorEnum.DATA_SUCCESS.buildSuccess();
        } catch (Exception e) {
            return ErrorEnum.DATA_FAIL.buildSuccess();
        }

    }
}
