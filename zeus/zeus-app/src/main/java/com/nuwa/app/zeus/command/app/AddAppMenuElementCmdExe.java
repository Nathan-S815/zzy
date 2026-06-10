package com.nuwa.app.zeus.command.app;

import com.alibaba.cola.dto.SingleResponse;
import com.alibaba.cola.exception.Assert;
import com.nuwa.app.zeus.service.AppBiz;
import com.nuwa.client.zeus.dto.clientobject.app.AddAppMenuElementCmd;
import com.nuwa.framework.cola.starter.cmd.AbstractCmdExe;
import com.nuwa.infrastructure.zeus.database.base.entity.BaseElement;
import com.nuwa.infrastructure.zeus.database.base.entity.BaseGroup;
import com.nuwa.infrastructure.zeus.database.base.service.BaseElementService;
import com.nuwa.infrastructure.zeus.database.base.service.BaseGroupService;
import com.nuwa.infrastructure.zeus.enums.ErrorEnum;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * AppMenuAddElementCmdExe app 菜单增加权限
 *
 * @author hy
 * @date 2021/6/2 13:32
 * @since 1.0.0
 */
@Slf4j
@Component
public class AddAppMenuElementCmdExe extends AbstractCmdExe<AddAppMenuElementCmd, SingleResponse> {

    @Autowired
    private BaseGroupService baseGroupService;

    @Autowired
    private BaseElementService baseElementService;

    @Autowired
    private AppBiz appBiz;

    @Override
    protected SingleResponse handle(AddAppMenuElementCmd cmd) {
        Integer count = baseElementService.lambdaQuery().eq(BaseElement::getCode, cmd.getCode()).count();
        Assert.isTrue(count.equals(0), ErrorEnum.BaseElement_CodeIsEXIST, "权限编码已存在");

        BaseGroup baseGroup = baseGroupService.lambdaQuery().eq(BaseGroup::getCode, cmd.getAppId()).one();
        Assert.notNull(baseGroup, "BaseGroup[appId:" + cmd.getAppId() + "] 为空");

        BaseElement element = new BaseElement();
        BeanUtils.copyProperties(cmd, element);
        element.setCreateUserId(cmd.getUserAware().getMchUserId() + "");
        element.setCreateUserName(cmd.getUserAware().getUserName());
        element.setCreateHost(cmd.getUserAware().getHostIp());
        element.setCreateTime(new Date());
        Boolean flag = appBiz.createAppMenuElement(element, baseGroup.getId());
        if (flag) {
            return ErrorEnum.DATA_SUCCESS.buildSuccess();
        }
        return ErrorEnum.DATA_FAIL.buildSuccess();
    }
}
