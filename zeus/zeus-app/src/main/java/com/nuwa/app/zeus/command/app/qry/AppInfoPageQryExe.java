package com.nuwa.app.zeus.command.app.qry;

import com.alibaba.cola.dto.SingleResponse;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.nuwa.app.zeus.vo.AppTree;
import com.nuwa.client.zeus.dto.clientobject.app.qry.AppInfoPageQry;
import com.nuwa.client.zeus.dto.clientobject.mch.qry.MerchantPageQry;
import com.nuwa.framework.cola.starter.cmd.AbstractQryExe;
import com.nuwa.infrastructure.zeus.database.app.entity.AppInfo;
import com.nuwa.infrastructure.zeus.database.app.param.AppInfoPageParam;
import com.nuwa.infrastructure.zeus.database.app.service.AppInfoService;
import com.nuwa.infrastructure.zeus.database.mch.entity.Merchant;
import com.nuwa.infrastructure.zeus.database.mch.param.MerchantPageParam;
import com.nuwa.infrastructure.zeus.database.mch.service.MerchantService;
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
public class AppInfoPageQryExe extends AbstractQryExe<AppInfoPageQry, SingleResponse<IPage<AppInfo>>> {

    @Autowired
    private AppInfoService appInfoService;

    @Override
    protected SingleResponse<IPage<AppInfo>> handle(AppInfoPageQry cmd) {
        IPage<AppInfo> appInfoIPage = appInfoService.paginateByParam(new AppInfoPageParam(cmd));
        return SingleResponse.of(appInfoIPage);
    }
}
