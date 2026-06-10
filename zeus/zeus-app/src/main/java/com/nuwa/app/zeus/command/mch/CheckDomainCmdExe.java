package com.nuwa.app.zeus.command.mch;

import com.alibaba.cola.dto.SingleResponse;
import com.nuwa.client.zeus.dto.clientobject.mch.CheckDomainCmd;
import com.nuwa.framework.cola.starter.cmd.AbstractCmdExe;
import com.nuwa.infrastructure.zeus.database.mch.entity.MerchantSiteConfig;
import com.nuwa.infrastructure.zeus.database.mch.service.MerchantSiteConfigService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class CheckDomainCmdExe extends AbstractCmdExe<CheckDomainCmd, SingleResponse> {
    @Autowired
    private MerchantSiteConfigService merchantSiteConfigService;
    @Override
    protected SingleResponse handle(CheckDomainCmd cmd) {
        String hostIP = cmd.getDomain().replace("http://","").replace("https://","");//去除http和https前缀
        String [] arr = hostIP.split("/");//按‘/’分隔，取第一个
        cmd.setDomain(arr[0]);
        Integer count = merchantSiteConfigService.lambdaQuery().eq(MerchantSiteConfig::getDomain, cmd.getDomain()).count();
        if (count != 0){
            return SingleResponse.of(false);
        }
        return SingleResponse.of(true);
    }
}
