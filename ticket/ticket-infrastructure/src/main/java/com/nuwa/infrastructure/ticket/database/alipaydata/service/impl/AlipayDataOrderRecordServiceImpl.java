package com.nuwa.infrastructure.ticket.database.alipaydata.service.impl;

import com.nuwa.infrastructure.ticket.database.alipaydata.entity.AlipayDataOrderRecord;
import com.nuwa.infrastructure.ticket.database.alipaydata.mapper.AlipayDataOrderRecordMapper;
import com.nuwa.infrastructure.ticket.database.alipaydata.service.AlipayDataOrderRecordService;
import com.nuwa.framework.database.supper.SuperServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * 支付宝-景区订单同步记录 服务实现类
 *
 * @author huyonghack@163.com
 * @since 2022-05-26
 */
@Slf4j
@Service
public class AlipayDataOrderRecordServiceImpl extends SuperServiceImpl<AlipayDataOrderRecordMapper, AlipayDataOrderRecord> implements AlipayDataOrderRecordService {

    @Autowired
    private AlipayDataOrderRecordMapper alipayDataOrderRecordMapper;

}
