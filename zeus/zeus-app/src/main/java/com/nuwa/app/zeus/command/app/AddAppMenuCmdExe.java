package com.nuwa.app.zeus.command.app;

import com.alibaba.cola.dto.SingleResponse;
import com.alibaba.cola.exception.Assert;
import com.nuwa.app.zeus.service.AppBiz;
import com.nuwa.client.zeus.dto.clientobject.app.AddAppMenuCmd;
import com.nuwa.framework.cola.starter.cmd.AbstractCmdExe;
import com.nuwa.infrastructure.zeus.constant.AdminCommonConstant;
import com.nuwa.infrastructure.zeus.database.base.entity.BaseGroup;
import com.nuwa.infrastructure.zeus.database.base.entity.BaseMenu;
import com.nuwa.infrastructure.zeus.database.base.service.BaseGroupService;
import com.nuwa.infrastructure.zeus.database.base.service.BaseMenuService;
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
public class AddAppMenuCmdExe extends AbstractCmdExe<AddAppMenuCmd, SingleResponse> {

    @Autowired
    private BaseGroupService baseGroupService;

    @Autowired
    private BaseMenuService baseMenuService;

    @Autowired
    private AppBiz appBiz;

    @Override
    protected SingleResponse handle(AddAppMenuCmd cmd) {
        BaseGroup baseGroup = baseGroupService.lambdaQuery().eq(BaseGroup::getCode, cmd.getAppId()).one();
        Assert.notNull(baseGroup, "BaseGroup[appId:" + cmd.getAppId() + "] 为空");

        BaseMenu baseMenu = new BaseMenu();
        BeanUtils.copyProperties(cmd, baseMenu);
        if (AdminCommonConstant.ROOT == cmd.getParentId()) {
            baseMenu.setPath("/" + cmd.getCode());
        } else {
            BaseMenu parent = baseMenuService.getById(cmd.getParentId());
            baseMenu.setPath(parent.getPath() + "/" + cmd.getCode());
        }

        baseMenu.setCreateTime(new Date());
        baseMenu.setCreateHost(cmd.getUserAware().getHostIp());
        baseMenu.setCreateUserId(cmd.getUserAware().getMchUserId() + "");
        baseMenu.setCreateUserName(cmd.getUserAware().getUserName());
        baseMenu.setAppId(cmd.getAppId().longValue());
        Boolean appMenu = appBiz.createAppMenu(baseMenu, baseGroup.getId());
        if (appMenu) {
            return ErrorEnum.DATA_SUCCESS.buildSuccess();
        }
        return ErrorEnum.DATA_FAIL.buildSuccess();
    }
}
