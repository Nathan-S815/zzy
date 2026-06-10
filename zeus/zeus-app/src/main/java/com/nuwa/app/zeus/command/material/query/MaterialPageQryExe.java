package com.nuwa.app.zeus.command.material.query;

import com.alibaba.cola.dto.SingleResponse;
import com.nuwa.client.zeus.dto.clientobject.material.qry.MaterialPageQry;
import com.nuwa.client.zeus.dto.clientobject.material.qry.MaterialTypeListQry;
import com.nuwa.framework.cola.starter.cmd.AbstractCmdExe;
import com.nuwa.infrastructure.zeus.database.base.entity.BaseGroup;
import com.nuwa.infrastructure.zeus.database.material.entity.MaterialType;
import com.nuwa.infrastructure.zeus.database.material.param.MaterialPageParam;
import com.nuwa.infrastructure.zeus.database.material.service.MaterialService;
import com.nuwa.infrastructure.zeus.database.material.service.MaterialTypeService;
import com.nuwa.infrastructure.zeus.enums.DeleteFlagEnum;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * GetBaseGroupByIdQryExe
 *
 * @author hy
 * @date 2021/5/25 16:32
 * @since 1.0.0
 */
@Slf4j
@Component
public class MaterialPageQryExe extends AbstractCmdExe<MaterialPageQry, SingleResponse<BaseGroup>> {

    @Autowired
    private MaterialService materialService;

    @Override
    protected SingleResponse handle(MaterialPageQry cmd) {
        return SingleResponse.of(materialService.paginateByParam(new MaterialPageParam(cmd)));
    }
}
