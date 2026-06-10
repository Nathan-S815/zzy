package com.nuwa.app.zeus.command.material;

import com.alibaba.cola.dto.SingleResponse;
import com.nuwa.client.zeus.dto.clientobject.material.DelMaterialCmd;
import com.nuwa.framework.cola.starter.cmd.AbstractCmdExe;
import com.nuwa.infrastructure.zeus.database.material.entity.Material;
import com.nuwa.infrastructure.zeus.database.material.service.MaterialService;
import com.nuwa.infrastructure.zeus.util.SerializUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class DelMaterialCmdExe  extends AbstractCmdExe<DelMaterialCmd, SingleResponse> {
    @Autowired
    private MaterialService materialService;
    @Override
    protected SingleResponse handle(DelMaterialCmd cmd) {
        materialService.lambdaUpdate()
                .in(Material::getId, SerializUtil.strToList(cmd.getIds()))
                .set(Material::getStatus, 3)
                .update();
        return SingleResponse.buildSuccess();
    }
}
