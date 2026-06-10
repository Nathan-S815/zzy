package com.nuwa.infrastructure.ticket.database.one.service.impl;

import com.nuwa.infrastructure.ticket.database.one.entity.OneOpenApiRecord;
import com.nuwa.infrastructure.ticket.database.one.mapper.OneOpenApiRecordMapper;
import com.nuwa.infrastructure.ticket.database.one.service.OneOpenApiRecordService;
import com.nuwa.framework.database.supper.SuperServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * 一码通调用记录 服务实现类
 *
 * @author huyonghack@163.com
 * @since 2022-10-29
 */
@Slf4j
@Service
public class OneOpenApiRecordServiceImpl extends SuperServiceImpl<OneOpenApiRecordMapper, OneOpenApiRecord> implements OneOpenApiRecordService {

    @Autowired
    private OneOpenApiRecordMapper oneOpenApiRecordMapper;

}
