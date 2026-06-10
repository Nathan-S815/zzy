package com.nuwa.app.ticket.command.dict.query;

import com.alibaba.cola.dto.SingleResponse;
import com.nuwa.client.ticket.dto.clientobject.dict.DictGetByColumnCmd;
import com.nuwa.client.ticket.dto.clientobject.dict.co.DictGetByColumnCO;
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
public class DictGetByColumnQryExe extends AbstractQryExe<DictGetByColumnCmd, SingleResponse> {
    @Autowired
    private SysDictDataService dictDataService;

    @Override
    protected SingleResponse handle(DictGetByColumnCmd cmd) {
        DictGetByColumnCO dictGetByColumnCO = cmd.getDictGetByColumnCO();
        List<SysDictData> list = dictDataService.lambdaQuery()
                .eq(SysDictData::getDictColumn, dictGetByColumnCO.getDictColumn())
                .eq(SysDictData::getStatus,0)
                .orderByAsc(SysDictData::getDictSort)
                .list();
        List<DictVO> listVO = list.stream().map(DictVO::toVO).collect(Collectors.toList());
        return SingleResponse.of(listVO);
    }

    @Data
    public static class DictVO {
        private Long dictCode;
        private String label;
        private String value;

        public static DictVO toVO(SysDictData dictData) {
            DictVO vo = new DictVO();
            vo.setDictCode(dictData.getDictCode());
            vo.setLabel(dictData.getDictLabel());
            vo.setValue(dictData.getDictValue());
            return vo;
        }
    }
}
