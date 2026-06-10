package com.nuwa.infrastructure.ticket.database.order.mapper;

import com.nuwa.framework.database.supper.SuperMapper;
import com.nuwa.infrastructure.ticket.database.order.entity.OrderVoucher;

import org.springframework.stereotype.Repository;


/**
 * 订单核销凭证表 Mapper 接口
 *
 * @author huyonghack@163.com
 * @since 2021-12-19
 */
@Repository
public interface OrderVoucherMapper extends SuperMapper<OrderVoucher> {


}
