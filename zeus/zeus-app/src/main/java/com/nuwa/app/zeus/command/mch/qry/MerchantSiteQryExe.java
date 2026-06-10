package com.nuwa.app.zeus.command.mch.qry;

import com.alibaba.cola.dto.SingleResponse;
import com.nuwa.app.zeus.vo.AppTree;
import com.nuwa.client.zeus.dto.clientobject.mch.qry.MerchantQry;
import com.nuwa.client.zeus.dto.clientobject.mch.qry.MerchantSiteQry;
import com.nuwa.framework.cola.starter.cmd.AbstractQryExe;
import com.nuwa.infrastructure.zeus.database.mch.entity.Merchant;
import com.nuwa.infrastructure.zeus.database.mch.entity.MerchantSiteConfig;
import com.nuwa.infrastructure.zeus.database.mch.service.MerchantService;
import com.nuwa.infrastructure.zeus.database.mch.service.MerchantSiteConfigService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * GetAppTreeQryExe
 *
 * @author hy
 * @date 2021/6/3 18:14
 * @since 1.0.0
 */
@Slf4j
@Component
public class MerchantSiteQryExe extends AbstractQryExe<MerchantSiteQry, SingleResponse<MerchantSiteConfig>> {

    @Autowired
    private MerchantSiteConfigService merchantSiteConfigService;

    @Override
    protected SingleResponse<MerchantSiteConfig> handle(MerchantSiteQry cmd) {
        MerchantSiteConfig conf = merchantSiteConfigService.lambdaQuery()
                .eq(MerchantSiteConfig::getMchId, cmd.getMchId())
                .eq(MerchantSiteConfig::getType, cmd.getType())
                .one();
        return SingleResponse.of(conf);
    }
}
