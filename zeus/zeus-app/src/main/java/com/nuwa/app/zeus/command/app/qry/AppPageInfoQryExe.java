package com.nuwa.app.zeus.command.app.qry;

import cn.hutool.core.bean.BeanUtil;
import com.alibaba.cola.dto.SingleResponse;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.nuwa.client.zeus.dto.clientobject.app.qry.AppInfoQry;
import com.nuwa.client.zeus.dto.clientobject.app.qry.AppPageInfoQry;
import com.nuwa.framework.cola.starter.cmd.AbstractQryExe;
import com.nuwa.infrastructure.zeus.database.app.entity.AppInfo;
import com.nuwa.infrastructure.zeus.database.app.entity.AppSkuInfo;
import com.nuwa.infrastructure.zeus.database.app.service.AppInfoService;
import com.nuwa.infrastructure.zeus.database.app.service.AppSkuInfoService;
import com.nuwa.infrastructure.zeus.database.mch.entity.AppPageInfo;
import com.nuwa.infrastructure.zeus.database.mch.service.AppPageInfoService;
import com.nuwa.infrastructure.zeus.enums.DeleteFlagEnum;
import com.nuwa.infrastructure.zeus.util.MaterialJson;
import lombok.Data;
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
public class AppPageInfoQryExe extends AbstractQryExe<AppPageInfoQry, SingleResponse<AppPageInfo>> {

    @Autowired
    private AppPageInfoService appPageInfoService;

    @Override
    protected SingleResponse<AppPageInfo> handle(AppPageInfoQry cmd) {
        AppPageInfo appPageInfo = appPageInfoService.lambdaQuery().eq(AppPageInfo::getId,cmd.getId()).one();
        return SingleResponse.of(appPageInfo);
    }
}
