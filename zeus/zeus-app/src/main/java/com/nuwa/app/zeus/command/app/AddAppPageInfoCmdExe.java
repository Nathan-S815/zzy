package com.nuwa.app.zeus.command.app;

import cn.hutool.core.bean.BeanUtil;
import com.alibaba.cola.dto.SingleResponse;
import com.alibaba.cola.exception.Assert;
import com.nuwa.app.zeus.service.AppBiz;
import com.nuwa.client.zeus.dto.clientobject.app.AddAppMenuCmd;
import com.nuwa.client.zeus.dto.clientobject.app.AddAppPageInfoCmd;
import com.nuwa.framework.cola.starter.cmd.AbstractCmdExe;
import com.nuwa.infrastructure.zeus.constant.AdminCommonConstant;
import com.nuwa.infrastructure.zeus.database.base.entity.BaseGroup;
import com.nuwa.infrastructure.zeus.database.base.entity.BaseMenu;
import com.nuwa.infrastructure.zeus.database.base.service.BaseGroupService;
import com.nuwa.infrastructure.zeus.database.base.service.BaseMenuService;
import com.nuwa.infrastructure.zeus.database.mch.entity.AppPageInfo;
import com.nuwa.infrastructure.zeus.database.mch.service.AppPageInfoService;
import com.nuwa.infrastructure.zeus.enums.ErrorEnum;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * AppAddMenuCmdExe 应用添加菜单
 *
 * @author hy
 * @date 2021/6/1 9:28
 * @since 1.0.0
 */
@Slf4j
@Component
public class AddAppPageInfoCmdExe extends AbstractCmdExe<AddAppPageInfoCmd, SingleResponse> {

    @Autowired
    private AppPageInfoService appPageInfoService;

    @Override
    protected SingleResponse handle(AddAppPageInfoCmd cmd) {
        AppPageInfo pageInfo = new AppPageInfo();
        BeanUtil.copyProperties(cmd,pageInfo);
        boolean save = appPageInfoService.save(pageInfo);
        if (save) {
            return ErrorEnum.DATA_SUCCESS.buildSuccess();
        }
        return ErrorEnum.DATA_FAIL.buildSuccess();
    }
}
