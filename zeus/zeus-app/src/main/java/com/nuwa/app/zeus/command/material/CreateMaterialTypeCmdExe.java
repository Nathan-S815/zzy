package com.nuwa.app.zeus.command.material;

import com.alibaba.cola.dto.SingleResponse;
import com.nuwa.client.zeus.dto.clientobject.base.ModifyBaseGroupCmd;
import com.nuwa.client.zeus.dto.clientobject.material.CreateMaterialTypeCmd;
import com.nuwa.framework.cola.starter.cmd.AbstractCmdExe;
import com.nuwa.infrastructure.zeus.constant.AdminCommonConstant;
import com.nuwa.infrastructure.zeus.database.base.entity.BaseGroup;
import com.nuwa.infrastructure.zeus.database.base.service.BaseGroupService;
import com.nuwa.infrastructure.zeus.database.material.entity.MaterialType;
import com.nuwa.infrastructure.zeus.database.material.service.MaterialTypeService;
import com.nuwa.infrastructure.zeus.enums.DeleteFlagEnum;
import com.nuwa.infrastructure.zeus.enums.ErrorEnum;
import lombok.extern.slf4j.Slf4j;
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
public class CreateMaterialTypeCmdExe extends AbstractCmdExe<CreateMaterialTypeCmd, SingleResponse> {

    @Autowired
    private MaterialTypeService materialTypeService;

    @Override
    protected SingleResponse handle(CreateMaterialTypeCmd cmd) {
        Integer count = materialTypeService.lambdaQuery()
                .eq(MaterialType::getMchId, cmd.getUserAware().getMchId())
                .eq(MaterialType::getName, cmd.getName())
                .eq(MaterialType::getDeleteFlag, DeleteFlagEnum.NORMAL.getCode())
                .count();
        if (count > 0) {
            return ErrorEnum.NAME_REPEAT.buildFailure();
        }
        MaterialType materialType = new MaterialType();
        materialType.setMchId(cmd.getUserAware().getMchId());
        materialType.setName(cmd.getName());
        boolean save = materialTypeService.save(materialType);
        if (save) {
            return SingleResponse.of(materialType);
        }
        return ErrorEnum.DATA_FAIL.buildFailure();
    }
}
