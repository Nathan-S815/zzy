package com.nuwa.app.zeus.command.ship;

import cn.hutool.core.bean.BeanUtil;
import com.alibaba.cola.dto.SingleResponse;
import com.nuwa.client.zeus.dto.clientobject.ship.EditMerchantAppSubmitUrlCmd;
import com.nuwa.framework.cola.starter.cmd.AbstractCmdExe;
import com.nuwa.infrastructure.zeus.database.mch.entity.MerchantApp;
import com.nuwa.infrastructure.zeus.database.mch.entity.MerchantAppUrl;
import com.nuwa.infrastructure.zeus.database.mch.service.MerchantAppService;
import com.nuwa.infrastructure.zeus.database.mch.service.MerchantAppUrlService;
import com.nuwa.infrastructure.zeus.enums.ErrorEnum;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Date;

@Slf4j
@Component
public class EditMerchantAppSubmitUrlCmdExe extends AbstractCmdExe<EditMerchantAppSubmitUrlCmd, SingleResponse> {
    @Autowired
    private MerchantAppService merchantAppService;
    @Autowired
    private MerchantAppUrlService merchantAppUrlService;
    @Override
    protected SingleResponse handle(EditMerchantAppSubmitUrlCmd cmd) {
        MerchantApp merchantApp = merchantAppService.getById(cmd.getId());
        if (BeanUtil.isEmpty(merchantApp)){
            return ErrorEnum.INFO_CHANGED.buildFailure();
        }
        boolean update = merchantAppUrlService.lambdaUpdate()
                .eq(MerchantAppUrl::getAppId, merchantApp.getAppId())
                .eq(MerchantAppUrl::getMchId, merchantApp.getMerchantId())
                .set(MerchantAppUrl::getLoginSubmitUrl, cmd.getLoginSubmitUrl())
                .set(MerchantAppUrl::getScenicCode, cmd.getScenicCode())
                .update();
        if (update) {
            return ErrorEnum.DATA_SUCCESS.buildSuccess(merchantApp);
        }
        return ErrorEnum.DATA_FAIL.buildFailure();
    }
}
