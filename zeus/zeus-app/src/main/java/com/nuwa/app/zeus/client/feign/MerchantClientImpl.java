package com.nuwa.app.zeus.client.feign;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.StrUtil;
import com.alibaba.cola.dto.SingleResponse;
import com.nuwa.client.zeus.api.merchant.MerchantClientI;
import com.nuwa.client.zeus.api.merchant.vo.ClientAppVO;
import com.nuwa.client.zeus.api.merchant.vo.MerchantSimpleVO;
import com.nuwa.infrastructure.zeus.config.OssDomainConfigEx;
import com.nuwa.infrastructure.zeus.database.app.entity.AppInfo;
import com.nuwa.infrastructure.zeus.database.app.service.AppInfoService;
import com.nuwa.infrastructure.zeus.database.material.entity.Material;
import com.nuwa.infrastructure.zeus.database.material.service.MaterialService;
import com.nuwa.infrastructure.zeus.database.mch.entity.Merchant;
import com.nuwa.infrastructure.zeus.database.mch.entity.MerchantApp;
import com.nuwa.infrastructure.zeus.database.mch.service.MerchantAppService;
import com.nuwa.infrastructure.zeus.database.mch.service.MerchantService;
import com.nuwa.infrastructure.zeus.util.SpringContextKit;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * MerchantClientImpl
 *
 * @author hy
 * @date 2021/4/23 10:24
 * @since 1.0.0
 */
@Slf4j
@RestController
public class MerchantClientImpl implements MerchantClientI {

    @Autowired
    private MerchantService merchantService;

    @Autowired
    private MaterialService materialService;

    @Autowired
    private AppInfoService appInfoService;

    @Override
    public SingleResponse<List<MerchantSimpleVO>> getMerchantListByIds(Collection<Long> ids) {
        List<Merchant> merchantList = merchantService.lambdaQuery()
                .select(Merchant::getMchId, Merchant::getMchName, Merchant::getContentName, Merchant::getContentPhone)
                .in(Merchant::getMchId, ids).list();
        List<MerchantSimpleVO> merchantSimpleVoList = merchantList.stream().map(x -> {
            MerchantSimpleVO vo = new MerchantSimpleVO();
            vo.setLinkMan(x.getContentName());
            vo.setLinkMobile(x.getContentPhone());
            vo.setMchId(x.getMchId());
            vo.setMchName(x.getMchName());
            return vo;
        }).collect(Collectors.toList());
        return SingleResponse.of(merchantSimpleVoList);
    }

    @Override
    public SingleResponse<List<ClientAppVO>> getAppListByIds(Collection<Long> ids) {
        if (Objects.isNull(ids) || ids.size() == 0) {
            List<ClientAppVO> appInfoList = new ArrayList<>();
            return SingleResponse.of(appInfoList);
        }
        List<AppInfo> appInfoList = appInfoService.lambdaQuery().in(AppInfo::getId, ids).list();
        List<ClientAppVO> merchantClientAppVOList = appInfoList.stream().map(x -> {
            ClientAppVO vo = new ClientAppVO();
            BeanUtil.copyProperties(x, vo);
            Material material = materialService.getById(x.getLogo());
            vo.setLogo(material.getUrl());
            return vo;
        }).collect(Collectors.toList());
        return SingleResponse.of(merchantClientAppVOList);
    }

    public Material urlConvert(Material material) {
        OssDomainConfigEx ossDomainConfig = SpringContextKit.getBean(OssDomainConfigEx.class);
        if (StrUtil.isBlank(ossDomainConfig.getOssDomain())) {
            return material;
        }
        material.setUrl(material.getUrl());
        return material;
    }
}
