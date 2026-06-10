package com.nuwa.app.zeus.command.app;

import com.alibaba.cola.dto.SingleResponse;
import com.nuwa.client.zeus.dto.clientobject.app.OffAppCmd;
import com.nuwa.client.zeus.dto.clientobject.app.OnAppCmd;
import com.nuwa.framework.cola.starter.cmd.AbstractCmdExe;
import com.nuwa.infrastructure.zeus.database.app.entity.AppInfo;
import com.nuwa.infrastructure.zeus.database.app.service.AppInfoService;
import com.nuwa.infrastructure.zeus.enums.ErrorEnum;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * CreateAppCmdExe 创建应用
 *
 * @author hy
 * @date 2021/5/31 13:36
 * @since 1.0.0
 */
@Slf4j
@Component
public class OffAppCmdExe extends AbstractCmdExe<OffAppCmd, SingleResponse> {

    @Autowired
    private AppInfoService appInfoService;

    @Override
    protected SingleResponse handle(OffAppCmd cmd) {
        boolean update = appInfoService.lambdaUpdate().eq(AppInfo::getId, cmd.getId()).set(AppInfo::getStatus, 0).update();
        if (update) {
            return ErrorEnum.DATA_SUCCESS.buildSuccess();
        }
        return ErrorEnum.DATA_FAIL.buildSuccess();
    }
}
