package com.nuwa.infrastructure.ticket.database.order.service.impl;

import com.nuwa.infrastructure.ticket.database.order.entity.UserPromoteSettleRecord;
import com.nuwa.infrastructure.ticket.database.order.mapper.UserPromoteSettleRecordMapper;
import com.nuwa.infrastructure.ticket.database.order.service.UserPromoteSettleRecordService;
import com.nuwa.framework.database.supper.SuperServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * 用户推广结算记录 服务实现类
 *
 * @author huyonghack@163.com
 * @since 2022-01-18
 */
@Slf4j
@Service
public class UserPromoteSettleRecordServiceImpl extends SuperServiceImpl<UserPromoteSettleRecordMapper, UserPromoteSettleRecord> implements UserPromoteSettleRecordService {

    @Autowired
    private UserPromoteSettleRecordMapper userPromoteSettleRecordMapper;

}
