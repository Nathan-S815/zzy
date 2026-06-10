package com.nuwa.infrastructure.ticket.database.mall.service.impl;

import com.nuwa.framework.database.supper.SuperServiceImpl;
import com.nuwa.infrastructure.ticket.database.mall.entity.MallPaymentRefund;
import com.nuwa.infrastructure.ticket.database.mall.mapper.MallPaymentRefundMapper;
import com.nuwa.infrastructure.ticket.database.mall.service.MallPaymentRefundService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 交易详情 服务实现类
 *
 * @author huyonghack@163.com
 * @since 2021-06-01
 */
@Slf4j
@Service
public class MallPaymentRefundServiceImpl extends SuperServiceImpl<MallPaymentRefundMapper, MallPaymentRefund> implements MallPaymentRefundService {

    @Autowired
    private MallPaymentRefundMapper mallPaymentRefundMapper;

}
