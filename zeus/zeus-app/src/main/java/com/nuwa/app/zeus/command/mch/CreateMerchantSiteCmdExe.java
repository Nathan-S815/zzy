package com.nuwa.app.zeus.command.mch;

import cn.hutool.core.bean.BeanUtil;
import com.alibaba.cola.dto.SingleResponse;
import com.nuwa.app.zeus.service.MerchantBiz;
import com.nuwa.app.zeus.service.dto.MerchantUserDTO;
import com.nuwa.client.zeus.dto.clientobject.mch.CreateMerchantCmd;
import com.nuwa.client.zeus.dto.clientobject.mch.CreateMerchantSiteCmd;
import com.nuwa.framework.cola.starter.cmd.AbstractCmdExe;
import com.nuwa.infrastructure.zeus.database.mch.entity.Merchant;
import com.nuwa.infrastructure.zeus.database.mch.entity.MerchantSiteConfig;
import com.nuwa.infrastructure.zeus.database.mch.service.MerchantSiteConfigService;
import com.nuwa.infrastructure.zeus.enums.AuditStatusEnum;
import com.nuwa.infrastructure.zeus.enums.ErrorEnum;
import com.nuwa.infrastructure.zeus.util.SerializUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.net.MalformedURLException;
import java.util.Date;

/**
 * CreateMerchantCmdExe 创建商户
 *
 * @author hy
 * @date 2021/6/2 16:23
 * @since 1.0.0
 */
@Slf4j
@Component
public class CreateMerchantSiteCmdExe extends AbstractCmdExe<CreateMerchantSiteCmd, SingleResponse> {

    @Autowired
    private MerchantSiteConfigService merchantSiteConfigService;

    @Override
    protected SingleResponse handle(CreateMerchantSiteCmd cmd) {
        String hostIP = cmd.getDomain().replace("http://","").replace("https://","");//去除http和https前缀
        String [] arr = hostIP.split("/");//按‘/’分隔，取第一个
        cmd.setDomain(arr[0]);

        MerchantSiteConfig conf = merchantSiteConfigService.lambdaQuery()
                .eq(MerchantSiteConfig::getMchId, cmd.getUserAware().getMchId())
                .eq(MerchantSiteConfig::getType, cmd.getType())
                .one();
        if (BeanUtil.isNotEmpty(conf)) {
            if(!conf.getDomain().equals(cmd.getDomain())) {
                Integer count = merchantSiteConfigService.lambdaQuery().eq(MerchantSiteConfig::getDomain, cmd.getDomain()).count();
                if (count != 0) {
                    return ErrorEnum.DOMAIN_REPEAT.buildFailure();
                }
            }
            BeanUtil.copyProperties(cmd, conf);
            conf.setBgImg(SerializUtil.listToStr(cmd.getBgImg()));
            conf.setMchId(cmd.getUserAware().getMchId());
            merchantSiteConfigService.updateById(conf);
            return SingleResponse.buildSuccess();
        } else {
            Integer count = merchantSiteConfigService.lambdaQuery().eq(MerchantSiteConfig::getDomain, cmd.getDomain()).count();
            if (count != 0){
                return ErrorEnum.DOMAIN_REPEAT.buildFailure();
            }
            conf = new MerchantSiteConfig();
            BeanUtil.copyProperties(cmd, conf);
            conf.setBgImg(SerializUtil.listToStr(cmd.getBgImg()));
            conf.setMchId(cmd.getUserAware().getMchId());
            merchantSiteConfigService.save(conf);
            return SingleResponse.buildSuccess();
        }
    }

}
