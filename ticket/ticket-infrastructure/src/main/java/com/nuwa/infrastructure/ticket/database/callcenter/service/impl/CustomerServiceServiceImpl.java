package com.nuwa.infrastructure.ticket.database.callcenter.service.impl;

import com.nuwa.framework.database.supper.SuperServiceImpl;
import com.nuwa.infrastructure.ticket.database.callcenter.entity.CustomerService;
import com.nuwa.infrastructure.ticket.database.callcenter.mapper.CustomerServiceMapper;
import com.nuwa.infrastructure.ticket.database.callcenter.service.CustomerServiceService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 客服基础信息表 服务实现类
 *
 * @author huyonghack@163.com
 * @since 2021-05-15
 */
@Slf4j
@Service
public class CustomerServiceServiceImpl extends SuperServiceImpl<CustomerServiceMapper, CustomerService> implements CustomerServiceService {

    @Autowired
    private CustomerServiceMapper customerServiceMapper;

}
