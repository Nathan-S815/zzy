package com.nuwa.app.zeus.command.app;

import com.alibaba.cola.dto.SingleResponse;
import com.alibaba.cola.exception.Assert;
import com.nuwa.client.zeus.dto.clientobject.app.ModifyAppMenuElementCmd;
import com.nuwa.framework.cola.starter.cmd.AbstractCmdExe;
import com.nuwa.infrastructure.zeus.database.base.entity.BaseElement;
import com.nuwa.infrastructure.zeus.database.base.service.BaseElementService;
import com.nuwa.infrastructure.zeus.enums.ErrorEnum;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * AppMenuAddElementCmdExe app 菜单增加权限
 *
 * @author hy
 * @date 2021/6/2 13:32
 * @since 1.0.0
 */
@Slf4j
@Component
public class ModifyAppMenuElementCmdExe extends AbstractCmdExe<ModifyAppMenuElementCmd, SingleResponse> {

    @Autowired
    private BaseElementService baseElementService;

    @Override
    protected SingleResponse handle(ModifyAppMenuElementCmd cmd) {
        Integer count = baseElementService.lambdaQuery()
                .ne(BaseElement::getId, cmd.getId())
                .eq(BaseElement::getCode, cmd.getCode())
                .count();
        Assert.isTrue(count.equals(0), ErrorEnum.BaseElement_CodeIsEXIST, "权限编码已存在");

        BaseElement element = new BaseElement();
        BeanUtils.copyProperties(cmd, element);
        boolean flag = element.updateById();
        if (flag) {
            return ErrorEnum.DATA_SUCCESS.buildSuccess();
        }
        return ErrorEnum.DATA_FAIL.buildSuccess();
    }
}
