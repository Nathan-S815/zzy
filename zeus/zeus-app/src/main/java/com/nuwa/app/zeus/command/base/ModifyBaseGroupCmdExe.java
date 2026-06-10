package com.nuwa.app.zeus.command.base;

import com.alibaba.cola.dto.SingleResponse;
import com.nuwa.app.zeus.service.GroupBiz;
import com.nuwa.client.zeus.dto.clientobject.base.CreateBaseGroupCmd;
import com.nuwa.client.zeus.dto.clientobject.base.ModifyBaseGroupCmd;
import com.nuwa.framework.cola.starter.cmd.AbstractCmdExe;
import com.nuwa.infrastructure.zeus.constant.AdminCommonConstant;
import com.nuwa.infrastructure.zeus.database.base.entity.BaseGroup;
import com.nuwa.infrastructure.zeus.database.base.service.BaseGroupService;
import com.nuwa.infrastructure.zeus.enums.ErrorEnum;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * CreateBaseGroupCmdExe
 *
 * @author hy
 * @date 2021/5/25 17:19
 * @since 1.0.0
 */
@Slf4j
@Component
public class ModifyBaseGroupCmdExe extends AbstractCmdExe<ModifyBaseGroupCmd, SingleResponse> {

    @Autowired
    private BaseGroupService baseGroupService;

    @Override
    protected SingleResponse handle(ModifyBaseGroupCmd cmd) {
        String path = "";
        if (AdminCommonConstant.ROOT != cmd.getParentId()) {
            BaseGroup parent = baseGroupService.getById(cmd.getParentId());
            BaseGroup baseGroup = baseGroupService.getById(cmd.getId());
            path = parent.getPath() + "/" + baseGroup.getCode();
        }
        boolean update = baseGroupService.lambdaUpdate()
                .set(BaseGroup::getGroupType, cmd.getGroupType())
                .set(BaseGroup::getName, cmd.getName())
                .set(BaseGroup::getPath, path)
                .set(BaseGroup::getDescription, cmd.getDescription())
                .set(cmd.getTenantId() != -1, BaseGroup::getTenantId, cmd.getTenantId())
                .set(cmd.getParentId() != -1, BaseGroup::getParentId, cmd.getParentId())
                .set(BaseGroup::getUpdateHost, cmd.getUserAware().getHostIp())
                .set(BaseGroup::getUpdateTime, new Date())
                .set(BaseGroup::getUpdateUserId, cmd.getUserAware().getUserId())
                .set(BaseGroup::getUpdateUserName, cmd.getUserAware().getUserName())
                .eq(BaseGroup::getId, cmd.getId())
                .update();
        if (update) {
            return SingleResponse.buildSuccess();
        }
        return ErrorEnum.DATA_FAIL.buildFailure();
    }
}
