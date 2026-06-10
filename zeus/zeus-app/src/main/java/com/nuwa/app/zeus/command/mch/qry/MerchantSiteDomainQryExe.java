package com.nuwa.app.zeus.command.mch.qry;

import com.alibaba.cola.dto.SingleResponse;
import com.nuwa.app.zeus.vo.AppTree;
import com.nuwa.client.zeus.dto.clientobject.mch.qry.MerchantSiteDomainQry;
import com.nuwa.client.zeus.dto.clientobject.mch.qry.MerchantSiteQry;
import com.nuwa.framework.cola.starter.cmd.AbstractQryExe;
import com.nuwa.infrastructure.zeus.database.mch.entity.MerchantSiteConfig;
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
public class MerchantSiteDomainQryExe extends AbstractQryExe<MerchantSiteDomainQry, SingleResponse<MerchantSiteConfig>> {

    @Autowired
    private MerchantSiteConfigService merchantSiteConfigService;

    @Override
    protected SingleResponse<MerchantSiteConfig> handle(MerchantSiteDomainQry cmd) {
        MerchantSiteConfig conf = merchantSiteConfigService.lambdaQuery()
                .like(MerchantSiteConfig::getDomain, cmd.getDomain())
                .eq(MerchantSiteConfig::getType, cmd.getType())
                .one();
        if(conf==null){
            MerchantSiteConfig merchantSiteConfig = new MerchantSiteConfig();
            merchantSiteConfig.setLogo(Long.valueOf("13213"))
                    .setBgImg("3131").setDomain(cmd.getDomain())
                .setWebsiteApproveNo("浙ICP备19041494号-9 COPYRIGHT  2021 中智游 CORPORATION, ALL RIGHTS RESERVED");
            return SingleResponse.of(merchantSiteConfig);
        }
        return SingleResponse.of(conf);
    }
}
