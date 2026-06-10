package com.nuwa.app.ticket.command.dict.query;

import com.alibaba.cola.dto.SingleResponse;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.nuwa.client.ticket.dto.clientobject.dict.qry.SysDictDataPageQry;
import com.nuwa.framework.cola.starter.cmd.AbstractQryExe;
import com.nuwa.infrastructure.ticket.database.dict.entity.SysDictData;
import com.nuwa.infrastructure.ticket.database.dict.param.SysDictDataPageParam;
import com.nuwa.infrastructure.ticket.database.dict.service.SysDictDataService;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class SysDictDataPageQryExe extends AbstractQryExe<SysDictDataPageQry, SingleResponse> {
    @Autowired
    private SysDictDataService dictDataService;

    @Override
    protected SingleResponse handle(SysDictDataPageQry cmd) {
        IPage<DictVO> page = dictDataService.paginateAndConvert(new SysDictDataPageParam(cmd),DictVO::toVO);
        return SingleResponse.of(page);
    }

    @Data
    public static class DictVO {

        private Long dictCode;

        private String dictColumn;

        private String name;

        public static DictVO toVO(SysDictData dictData) {
            DictVO vo = new DictVO();
            vo.setDictCode(dictData.getDictCode());
            vo.setDictColumn(dictData.getDictColumn());
            vo.setName(dictData.getRemark());
            return vo;
        }
    }
}
