package com.nuwa.app.ticket.command.dict;

import com.alibaba.cola.dto.SingleResponse;
import com.nuwa.client.ticket.dto.clientobject.dict.DelDictDataCmd;
import com.nuwa.framework.cola.starter.cmd.AbstractCmdExe;
import com.nuwa.infrastructure.ticket.database.dict.entity.SysDictData;
import com.nuwa.infrastructure.ticket.database.dict.service.SysDictDataService;
import com.nuwa.infrastructure.ticket.enums.ErrorEnum;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class DelDictDataCmdExe extends AbstractCmdExe<DelDictDataCmd, SingleResponse> {
    @Autowired
    private SysDictDataService dictDataService;
    @Override
    protected SingleResponse handle(DelDictDataCmd cmd) {
        boolean update = dictDataService.lambdaUpdate().set(SysDictData::getStatus, 1).eq(SysDictData::getDictCode, cmd.getDictCode()).update();
        if (update) {
            return SingleResponse.buildSuccess();
        }
        return ErrorEnum.DATA_FAIL.buildFailure();
    }
}
