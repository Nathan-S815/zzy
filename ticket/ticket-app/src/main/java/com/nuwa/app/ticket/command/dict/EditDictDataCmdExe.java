package com.nuwa.app.ticket.command.dict;


import com.alibaba.cola.dto.SingleResponse;
import com.nuwa.client.ticket.dto.clientobject.dict.EditDictDataCmd;
import com.nuwa.client.ticket.dto.clientobject.dict.co.EditDictDataCO;
import com.nuwa.framework.cola.starter.cmd.AbstractCmdExe;
import com.nuwa.infrastructure.ticket.database.dict.entity.SysDictData;
import com.nuwa.infrastructure.ticket.database.dict.service.SysDictDataService;
import com.nuwa.infrastructure.ticket.enums.ErrorEnum;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class EditDictDataCmdExe extends AbstractCmdExe<EditDictDataCmd, SingleResponse> {
    @Autowired
    private SysDictDataService dictDataService;
    @Override
    protected SingleResponse handle(EditDictDataCmd cmd) {
        EditDictDataCO co = cmd.getEditDictDataCO();
        SysDictData sysDictData = dictDataService.lambdaQuery().eq(SysDictData::getDictCode, co.getDictCode()).one();
        sysDictData.setDictLabel(co.getDictLabel());
        sysDictData.setDictValue(co.getDictValue());
        boolean updateById = sysDictData.updateById();
        if (updateById) {
            return SingleResponse.of(sysDictData);
        }
        return ErrorEnum.DATA_FAIL.buildFailure();
    }
}
