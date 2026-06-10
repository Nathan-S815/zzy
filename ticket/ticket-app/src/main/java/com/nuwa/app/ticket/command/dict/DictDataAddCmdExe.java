package com.nuwa.app.ticket.command.dict;

import com.alibaba.cola.dto.SingleResponse;
import com.nuwa.client.ticket.dto.clientobject.dict.DictDataCmd;
import com.nuwa.client.ticket.dto.clientobject.dict.co.DictDataCO;
import com.nuwa.framework.cola.starter.cmd.AbstractCmdExe;
import com.nuwa.infrastructure.ticket.database.dict.entity.SysDictData;
import com.nuwa.infrastructure.ticket.database.dict.service.SysDictDataService;
import com.nuwa.infrastructure.ticket.enums.ErrorEnum;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class DictDataAddCmdExe extends AbstractCmdExe<DictDataCmd, SingleResponse> {
    @Autowired
    private SysDictDataService dictDataService;

    @Override
    protected SingleResponse handle(DictDataCmd cmd) {
        DictDataCO dictDataCO = cmd.getDictDataCO();
        SysDictData dictData = new SysDictData();
        dictData.setDictColumn(dictDataCO.getDictColumn());
        dictData.setDictLabel(dictDataCO.getDictLabel());
        dictData.setDictValue(dictDataCO.getDictValue());
        dictData.setRemark(dictDataCO.getRemark());
        dictData.setDictType("int");
        dictData.setIsDefault("N");
        boolean flag = dictDataService.save(dictData);
        return flag ? SingleResponse.buildSuccess() : SingleResponse.buildFailure("9856", "操作失败");
    }
}
