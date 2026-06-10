package com.nuwa.infrastructure.attract.database.travel.mapper;

import com.nuwa.framework.database.supper.SuperMapper;
import com.nuwa.infrastructure.attract.database.travel.entity.Customer;

import org.springframework.stereotype.Repository;

import java.util.List;


/**
 * 客户表 Mapper 接口
 *
 * @author nanhuang @南皇
 * @since 2022-09-15
 */
@Repository
public interface CustomerMapper extends SuperMapper<Customer> {


    int savaInfo(List<Customer> customerss);
}
