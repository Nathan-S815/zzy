package com.nuwa.app.zeus.command.app.qry;

import com.alibaba.cola.dto.SingleResponse;
import com.nuwa.client.zeus.dto.clientobject.app.qry.AppPageInfoListQry;
import com.nuwa.client.zeus.dto.clientobject.app.qry.AppPageInfoQry;
import com.nuwa.framework.cola.starter.cmd.AbstractQryExe;
import com.nuwa.infrastructure.zeus.database.mch.entity.AppPageInfo;
import com.nuwa.infrastructure.zeus.database.mch.service.AppPageInfoService;
import com.nuwa.infrastructure.zeus.enums.DeleteFlagEnum;
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
public class AppPageInfoListQryExe extends AbstractQryExe<AppPageInfoListQry, SingleResponse<List<AppPageInfo>>> {

    @Autowired
    private AppPageInfoService appPageInfoService;

    @Override
    protected SingleResponse<List<AppPageInfo>> handle(AppPageInfoListQry cmd) {
        List<AppPageInfo> pageInfoList = appPageInfoService.lambdaQuery().eq(AppPageInfo::getAppId, cmd.getAppId()).eq(AppPageInfo::getDeleteFlag, DeleteFlagEnum.NORMAL.getCode()).list();
        return SingleResponse.of(pageInfoList);
    }
}
