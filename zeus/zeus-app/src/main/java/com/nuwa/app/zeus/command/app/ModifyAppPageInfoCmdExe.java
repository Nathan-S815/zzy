package com.nuwa.app.zeus.command.app;

import cn.hutool.core.bean.BeanUtil;
import com.alibaba.cola.dto.SingleResponse;
import com.nuwa.client.zeus.dto.clientobject.app.AddAppPageInfoCmd;
import com.nuwa.client.zeus.dto.clientobject.app.ModifyAppPageInfoCmd;
import com.nuwa.framework.cola.starter.cmd.AbstractCmdExe;
import com.nuwa.infrastructure.zeus.database.mch.entity.AppPageInfo;
import com.nuwa.infrastructure.zeus.database.mch.service.AppPageInfoService;
import com.nuwa.infrastructure.zeus.enums.ErrorEnum;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * AppAddMenuCmdExe 应用添加菜单
 *
 * @author hy
 * @date 2021/6/1 9:28
 * @since 1.0.0
 */
@Slf4j
@Component
public class ModifyAppPageInfoCmdExe extends AbstractCmdExe<ModifyAppPageInfoCmd, SingleResponse> {

    @Autowired
    private AppPageInfoService appPageInfoService;

    @Override
    protected SingleResponse handle(ModifyAppPageInfoCmd cmd) {
        try {
            appPageInfoService.lambdaUpdate()
                    .eq(AppPageInfo::getId, cmd.getId())
                    .set(AppPageInfo::getPageName, cmd.getPageName())
                    .set(AppPageInfo::getPageUri, cmd.getPageUri())
                    .update();
            return ErrorEnum.DATA_SUCCESS.buildSuccess();

        } catch (Exception e) {
            return ErrorEnum.DATA_FAIL.buildSuccess();
        }
    }
}
