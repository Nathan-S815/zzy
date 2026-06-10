package com.nuwa.app.zeus.command.mch.qry;

import cn.hutool.core.bean.BeanUtil;
import com.alibaba.cola.dto.SingleResponse;
import com.nuwa.client.zeus.dto.clientobject.mch.qry.GetMerchantOpenedAppListQry;
import com.nuwa.framework.cola.starter.cmd.AbstractQryExe;
import com.nuwa.infrastructure.zeus.database.app.service.AppInfoService;
import com.nuwa.infrastructure.zeus.database.mch.entity.MerchantApp;
import com.nuwa.infrastructure.zeus.database.mch.entity.MerchantAppUrl;
import com.nuwa.infrastructure.zeus.database.mch.service.MerchantAppService;
import com.nuwa.infrastructure.zeus.database.mch.service.MerchantAppUrlService;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

/**
 * GetMerchantOpenedAppListQryExe 获取商户已开通应用列表
 *
 * @author hy
 * @date 2021/6/3 14:23
 * @since 1.0.0
 */
@Slf4j
@Component
public class GetMerchantOpenedAppIdQryExe extends AbstractQryExe<GetMerchantOpenedAppListQry, SingleResponse> {

    @Autowired
    private MerchantAppService merchantAppService;

    @Override
    protected SingleResponse handle(GetMerchantOpenedAppListQry cmd) {
        List<Long> collect = merchantAppService.lambdaQuery().eq(MerchantApp::getMerchantId, cmd.getMerchantId()).list()
                .stream().map(MerchantApp::getAppId).collect(Collectors.toList());
        return SingleResponse.of(collect);
    }

}
