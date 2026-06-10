package com.nuwa.infrastructure.ticket.database.order.service.impl;

import com.nuwa.infrastructure.ticket.database.order.entity.ConsumerRecord;
import com.nuwa.infrastructure.ticket.database.order.mapper.ConsumerRecordMapper;
import com.nuwa.infrastructure.ticket.database.order.service.ConsumerRecordService;
import com.nuwa.framework.database.supper.SuperServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * 核销记录表 服务实现类
 *
 * @author huyonghack@163.com
 * @since 2021-12-19
 */
@Slf4j
@Service
public class ConsumerRecordServiceImpl extends SuperServiceImpl<ConsumerRecordMapper, ConsumerRecord> implements ConsumerRecordService {

    @Autowired
    private ConsumerRecordMapper consumerRecordMapper;

}
