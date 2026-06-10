package com.nuwa.app.zeus.command.material;

import com.alibaba.cola.dto.SingleResponse;
import com.nuwa.client.zeus.dto.clientobject.material.ModifyMaterialCmd;
import com.nuwa.client.zeus.dto.clientobject.material.ModifyMaterialTypeCmd;
import com.nuwa.framework.cola.starter.cmd.AbstractCmdExe;
import com.nuwa.infrastructure.zeus.database.material.entity.Material;
import com.nuwa.infrastructure.zeus.database.material.entity.MaterialType;
import com.nuwa.infrastructure.zeus.database.material.service.MaterialService;
import com.nuwa.infrastructure.zeus.database.material.service.MaterialTypeService;
import com.nuwa.infrastructure.zeus.enums.DeleteFlagEnum;
import com.nuwa.infrastructure.zeus.enums.ErrorEnum;
import com.nuwa.infrastructure.zeus.util.SerializUtil;
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
public class ModifyMaterialCmdExe extends AbstractCmdExe<ModifyMaterialCmd, SingleResponse> {

    @Autowired
    private MaterialService materialService;

    @Override
    protected SingleResponse handle(ModifyMaterialCmd cmd) {
        materialService.lambdaUpdate()
                .in(Material::getId,SerializUtil.strToList(cmd.getIds()))
                .set(Material::getTypeId, cmd.getTypeId())
                .update();
        return SingleResponse.buildSuccess();
    }
}
