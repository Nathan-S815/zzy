package com.nuwa.app.zeus.command.app;

import com.alibaba.cola.dto.SingleResponse;
import com.nuwa.client.zeus.dto.clientobject.app.qry.PageInfoListQry;
import com.nuwa.framework.cola.starter.cmd.AbstractQryExe;
import com.nuwa.infrastructure.zeus.database.mch.entity.AppPageInfo;
import com.nuwa.infrastructure.zeus.database.mch.entity.MerchantApp;
import com.nuwa.infrastructure.zeus.database.mch.service.AppPageInfoService;
import com.nuwa.infrastructure.zeus.database.mch.service.MerchantAppService;
import com.nuwa.infrastructure.zeus.enums.DeleteFlagEnum;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

/**
 * AppendPageNavItemCmdExe
 *
 * @author hy
 * @date 2021/4/28 15:26
 * @since 1.0.0
 */
@Slf4j
@Component
public class GetPageInfoListQryExe extends AbstractQryExe<PageInfoListQry, SingleResponse<List<AppPageInfo>>> {

    @Autowired
    private AppPageInfoService appPageInfoService;

    @Autowired
    private MerchantAppService merchantAppService;

    @Override
    protected SingleResponse<List<AppPageInfo>> handle(PageInfoListQry cmd) {
        List<Long> appIds = merchantAppService.lambdaQuery()
                .eq(MerchantApp::getMerchantId, cmd.getUserAware().getMchId())
                .eq(MerchantApp::getStatus, 1)
                .list().stream().map(MerchantApp::getAppId).collect(Collectors.toList());
        List<AppPageInfo> list = appPageInfoService.lambdaQuery()
                //.in(AppPageInfo::getAppId, appIds)
                .eq(AppPageInfo::getAppId,cmd.getAppId())
                .eq(AppPageInfo::getDeleteFlag, DeleteFlagEnum.NORMAL.getCode())
                .list();
        return SingleResponse.of(list);
    }
}
