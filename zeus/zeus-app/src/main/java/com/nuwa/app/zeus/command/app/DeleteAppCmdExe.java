package com.nuwa.app.zeus.command.app;

import cn.hutool.core.bean.BeanUtil;
import com.alibaba.cola.dto.SingleResponse;
import com.alibaba.cola.exception.Assert;
import com.nuwa.app.zeus.service.AppBiz;
import com.nuwa.client.zeus.dto.clientobject.app.DeleteAppCmd;
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
public class DeleteAppCmdExe extends AbstractCmdExe<DeleteAppCmd, SingleResponse> {

    @Autowired
    private AppBiz appBiz;

    @Override
    protected SingleResponse handle(DeleteAppCmd cmd) {
        return appBiz.deleteApp(cmd.getId());
    }
}
