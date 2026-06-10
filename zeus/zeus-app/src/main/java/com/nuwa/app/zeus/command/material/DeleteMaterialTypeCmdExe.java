package com.nuwa.app.zeus.command.material;

import com.alibaba.cola.dto.SingleResponse;
import com.nuwa.client.zeus.dto.clientobject.material.DeleteMaterialTypeCmd;
import com.nuwa.client.zeus.dto.clientobject.material.ModifyMaterialTypeCmd;
import com.nuwa.framework.cola.starter.cmd.AbstractCmdExe;
import com.nuwa.infrastructure.zeus.database.material.entity.Material;
import com.nuwa.infrastructure.zeus.database.material.entity.MaterialType;
import com.nuwa.infrastructure.zeus.database.material.service.MaterialService;
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
public class DeleteMaterialTypeCmdExe extends AbstractCmdExe<DeleteMaterialTypeCmd, SingleResponse> {

    @Autowired
    private MaterialTypeService materialTypeService;
    @Autowired
    private MaterialService materialService;

    @Override
    protected SingleResponse handle(DeleteMaterialTypeCmd cmd) {
        boolean update = materialTypeService.lambdaUpdate()
                .eq(MaterialType::getId, cmd.getId())
                .eq(MaterialType::getGroupType,0)
                .set(MaterialType::getDeleteFlag, DeleteFlagEnum.DELETE.getCode()).update();
        if (update) {
            materialService.lambdaUpdate().eq(Material::getTypeId,cmd.getId()).set(Material::getStatus,3).update();
            return SingleResponse.buildSuccess();
        }
        return ErrorEnum.DATA_FAIL.buildFailure();
    }
}
