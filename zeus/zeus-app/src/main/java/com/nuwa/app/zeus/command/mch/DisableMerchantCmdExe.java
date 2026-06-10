package com.nuwa.app.zeus.command.mch;

import com.alibaba.cola.dto.SingleResponse;
import com.nuwa.client.zeus.dto.clientobject.mch.DisableMerchantCmd;
import com.nuwa.client.zeus.dto.clientobject.mch.EnableMerchantCmd;
import com.nuwa.framework.cola.starter.cmd.AbstractCmdExe;
import com.nuwa.infrastructure.zeus.database.mch.entity.Merchant;
import com.nuwa.infrastructure.zeus.database.mch.service.MerchantService;
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
public class DisableMerchantCmdExe extends AbstractCmdExe<DisableMerchantCmd, SingleResponse> {

    @Autowired
    private MerchantService merchantService;

    @Override
    protected SingleResponse handle(DisableMerchantCmd cmd) {
        try {
            merchantService.lambdaUpdate()
                    .eq(Merchant::getMchId, cmd.getMchId())
                    .set(Merchant::getStatus, 0)
                    .update();
            return ErrorEnum.DATA_SUCCESS.buildSuccess();
        } catch (Exception e) {
            return ErrorEnum.DATA_FAIL.buildSuccess();
        }

    }
}
