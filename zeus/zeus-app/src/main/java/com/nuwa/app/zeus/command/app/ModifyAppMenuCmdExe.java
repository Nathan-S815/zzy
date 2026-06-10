package com.nuwa.app.zeus.command.app;

import com.alibaba.cola.dto.SingleResponse;
import com.nuwa.client.zeus.dto.clientobject.app.ModifyAppMenuCmd;
import com.nuwa.framework.cola.starter.cmd.AbstractCmdExe;
import com.nuwa.infrastructure.zeus.constant.AdminCommonConstant;
import com.nuwa.infrastructure.zeus.database.base.entity.BaseMenu;
import com.nuwa.infrastructure.zeus.database.base.service.BaseMenuService;
import com.nuwa.infrastructure.zeus.enums.ErrorEnum;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * AppModifyMenuCmdExe 应用修改菜单
 *
 * @author hy
 * @date 2021/6/1 9:28
 * @since 1.0.0
 */
@Slf4j
@Component
public class ModifyAppMenuCmdExe extends AbstractCmdExe<ModifyAppMenuCmd, SingleResponse> {

    @Autowired
    private BaseMenuService baseMenuService;

    @Override
    protected SingleResponse handle(ModifyAppMenuCmd cmd) {
        BaseMenu baseMenu = baseMenuService.getById(cmd.getId());
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
        boolean updateBaseMenu = baseMenu.updateById();
        if (updateBaseMenu) {
            return ErrorEnum.DATA_SUCCESS.buildSuccess();
        }
        return ErrorEnum.DATA_FAIL.buildSuccess();
    }
}
