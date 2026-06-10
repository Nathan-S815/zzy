package com.nuwa.app.zeus.command.material;

import com.alibaba.cola.dto.SingleResponse;
import com.nuwa.client.zeus.dto.clientobject.material.CreateMaterialTypeCmd;
import com.nuwa.client.zeus.dto.clientobject.material.ModifyMaterialTypeCmd;
import com.nuwa.framework.cola.starter.cmd.AbstractCmdExe;
import com.nuwa.infrastructure.zeus.database.material.entity.MaterialType;
import com.nuwa.infrastructure.zeus.database.material.service.MaterialTypeService;
import com.nuwa.infrastructure.zeus.enums.DeleteFlagEnum;
import com.nuwa.infrastructure.zeus.enums.ErrorEnum;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * CreateBaseGroupCmdExe
 *
 * @author hy
 * @date 2021/5/25 17:19
 * @since 1.0.0
 */
@Slf4j
@Component
public class ModifyMaterialTypeCmdExe extends AbstractCmdExe<ModifyMaterialTypeCmd, SingleResponse> {

    @Autowired
    private MaterialTypeService materialTypeService;

    @Override
    protected SingleResponse handle(ModifyMaterialTypeCmd cmd) {
        Integer count = materialTypeService.lambdaQuery()
                .eq(MaterialType::getMchId, cmd.getUserAware().getMchId())
                .eq(MaterialType::getName, cmd.getName())
                .eq(MaterialType::getDeleteFlag, DeleteFlagEnum.NORMAL.getCode())
                .ne(MaterialType::getId, cmd.getId())
                .count();
        if (count > 0) {
            return ErrorEnum.NAME_REPEAT.buildFailure();
        }
        materialTypeService.lambdaUpdate()
                .eq(MaterialType::getId, cmd.getId())
                .set(MaterialType::getName, cmd.getName())
                .update();
        return SingleResponse.buildSuccess();
    }
}
