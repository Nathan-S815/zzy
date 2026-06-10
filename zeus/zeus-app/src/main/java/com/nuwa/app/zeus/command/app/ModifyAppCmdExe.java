package com.nuwa.app.zeus.command.app;

import cn.hutool.core.bean.BeanUtil;
import com.alibaba.cola.dto.SingleResponse;
import com.alibaba.cola.exception.Assert;
import com.nuwa.app.zeus.service.AppBiz;
import com.nuwa.client.zeus.dto.clientobject.app.CreateAppCmd;
import com.nuwa.client.zeus.dto.clientobject.app.ModifyAppCmd;
import com.nuwa.framework.cola.starter.cmd.AbstractCmdExe;
import com.nuwa.infrastructure.zeus.database.app.entity.AppInfo;
import com.nuwa.infrastructure.zeus.database.app.entity.AppSkuInfo;
import com.nuwa.infrastructure.zeus.database.app.service.AppInfoService;
import com.nuwa.infrastructure.zeus.enums.ErrorEnum;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * CreateAppCmdExe 创建应用
 *
 * @author hy
 * @date 2021/5/31 13:36
 * @since 1.0.0
 */
@Slf4j
@Component
public class ModifyAppCmdExe extends AbstractCmdExe<ModifyAppCmd, SingleResponse> {

    @Autowired
    private AppBiz appBiz;

    @Autowired
    private AppInfoService appInfoService;

    @Override
    protected SingleResponse handle(ModifyAppCmd cmd) {
        AppInfo appInfo = appInfoService.getById(cmd.getId());
        if (cmd.getAppType().equals(2)) {
            Assert.notNull(cmd.getDependentAppId(), "依耐应用Id不能为空");
            appInfo.setAppType(2);
        }
        BeanUtils.copyProperties(cmd, appInfo);
        appInfo.setUpdateHost(cmd.getUserAware().getHostIp());
        appInfo.setUpdateUserId(cmd.getUserAware().getMchUserId() + "");
        appInfo.setUpdateUserName(cmd.getUserAware().getUserName());

        List<AppSkuInfo> skuList = cmd.getSku().stream().map(x -> {
            AppSkuInfo appSkuInfo = new AppSkuInfo();
            BeanUtil.copyProperties(x, appSkuInfo);
            appSkuInfo.setAppId(cmd.getId());
            return appSkuInfo;
        }).collect(Collectors.toList());

        Boolean update = appBiz.updateApp(appInfo, skuList, cmd.getDependentAppId());
        if (update) {
            return ErrorEnum.DATA_SUCCESS.buildSuccess();
        }
        return ErrorEnum.DATA_FAIL.buildSuccess();
    }
}
