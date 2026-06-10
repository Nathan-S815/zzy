package com.nuwa.app.zeus.command.app;

import cn.hutool.core.bean.BeanUtil;
import com.alibaba.cola.dto.SingleResponse;
import com.alibaba.cola.exception.Assert;
import com.nuwa.app.zeus.service.AppBiz;
import com.nuwa.client.zeus.dto.clientobject.app.CreateAppCmd;
import com.nuwa.client.zeus.dto.clientobject.app.co.AppSkuInfoCO;
import com.nuwa.framework.cola.starter.cmd.AbstractCmdExe;
import com.nuwa.infrastructure.zeus.database.app.entity.AppInfo;
import com.nuwa.infrastructure.zeus.database.app.entity.AppSkuInfo;
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
public class CreateAppCmdExe extends AbstractCmdExe<CreateAppCmd, SingleResponse> {

    @Autowired
    private AppBiz appBiz;

    @Override
    protected SingleResponse handle(CreateAppCmd cmd) {
        AppInfo appInfo = new AppInfo();
        if (cmd.getAppType().equals(2)) {
            Assert.notNull(cmd.getDependentAppId(), "依耐应用Id不能为空");
            appInfo.setAppType(2);
        }
        BeanUtils.copyProperties(cmd, appInfo);
        appInfo.setCreateTime(new Date());
        appInfo.setCreateHost(cmd.getUserAware().getHostIp());
        appInfo.setCreateUserId(cmd.getUserAware().getMchUserId() + "");
        appInfo.setCreateUserName(cmd.getUserAware().getUserName());

        List<AppSkuInfo> skuList = cmd.getSku().stream().map(x -> {
            AppSkuInfo appSkuInfo = new AppSkuInfo();
            BeanUtil.copyProperties(x, appSkuInfo);
            return appSkuInfo;
        }).collect(Collectors.toList());

        Boolean save = appBiz.createApp(appInfo, skuList, cmd.getDependentAppId());
        if (save) {
            return ErrorEnum.DATA_SUCCESS.buildSuccess();
        }
        return ErrorEnum.DATA_FAIL.buildSuccess();
    }
}
