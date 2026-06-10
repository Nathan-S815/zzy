package com.nuwa.app.ticket.command.dict.query;

import com.alibaba.cola.dto.SingleResponse;
import com.nuwa.client.ticket.dto.clientobject.dict.DictDataListCmd;
import com.nuwa.client.ticket.dto.clientobject.dict.co.DictDataListCO;
import com.nuwa.framework.cola.starter.cmd.AbstractQryExe;
import com.nuwa.infrastructure.ticket.database.dict.entity.SysDictData;
import com.nuwa.infrastructure.ticket.database.dict.service.SysDictDataService;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Component
public class DictDataListQryExe extends AbstractQryExe<DictDataListCmd, SingleResponse> {
    @Autowired
    private SysDictDataService dictDataService;

    @Override
    protected SingleResponse handle(DictDataListCmd cmd) {
        DictDataListCO dictDataListCO = cmd.getDictDataListCO();
        List<SysDictData> list = dictDataService.lambdaQuery()
                .select(SysDictData::getDictColumn, SysDictData::getRemark)
                .groupBy(SysDictData::getDictColumn)
                .list();
        List<DictVO> listVO = list.stream().map(DictVO::toVO).collect(Collectors.toList());
        return SingleResponse.of(listVO);
    }

    @Data
    public static class DictVO {
        private String dictColumn;

        private String value;

        public static DictVO toVO(SysDictData dictData) {
            DictVO vo = new DictVO();
            vo.setDictColumn(dictData.getDictColumn());
            vo.setValue(dictData.getRemark());
            return vo;
        }
    }
}
