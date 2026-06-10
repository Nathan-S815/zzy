package com.nuwa.infrastructure.ticket.database.dict.service.impl;

import com.nuwa.framework.database.supper.SuperServiceImpl;
import com.nuwa.infrastructure.ticket.database.dict.entity.SysDictData;
import com.nuwa.infrastructure.ticket.database.dict.mapper.SysDictDataMapper;
import com.nuwa.infrastructure.ticket.database.dict.service.SysDictDataService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 字典数据表 服务实现类
 *
 * @author huyonghack@163.com
 * @since 2021-04-27
 */
@Slf4j
@Service
public class SysDictDataServiceImpl extends SuperServiceImpl<SysDictDataMapper, SysDictData> implements SysDictDataService {

    @Autowired
    private SysDictDataMapper sysDictDataMapper;

    @Override
    public boolean insertNewDict(SysDictData dictData) {
        return sysDictDataMapper.insertNewDict(dictData);
    }
}
