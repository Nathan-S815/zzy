package com.nuwa.infrastructure.ticket.database.order.mapper;

import com.nuwa.framework.database.supper.SuperMapper;
import com.nuwa.infrastructure.ticket.database.order.entity.ConsumerRecord;

import org.springframework.stereotype.Repository;


/**
 * 核销记录表 Mapper 接口
 *
 * @author huyonghack@163.com
 * @since 2021-12-19
 */
@Repository
public interface ConsumerRecordMapper extends SuperMapper<ConsumerRecord> {


}
