package com.nuwa.infrastructure.attract.database.travel.service.impl;

import com.nuwa.infrastructure.attract.database.travel.entity.Customer;
import com.nuwa.infrastructure.attract.database.travel.mapper.CustomerMapper;
import com.nuwa.infrastructure.attract.database.travel.service.CustomerService;
import com.nuwa.framework.database.supper.SuperServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * 客户表 服务实现类
 *
 * @author nanhuang @南皇
 * @since 2022-09-15
 */
@Slf4j
@Service
public class CustomerServiceImpl extends SuperServiceImpl<CustomerMapper, Customer> implements CustomerService {

    @Autowired
    private CustomerMapper customerMapper;

}
