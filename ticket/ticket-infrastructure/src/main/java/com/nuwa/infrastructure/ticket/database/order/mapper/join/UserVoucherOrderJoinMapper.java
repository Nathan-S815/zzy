package com.nuwa.infrastructure.ticket.database.order.mapper.join;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import com.nuwa.framework.database.tk.join.mapper.JoinListMapper;
import com.nuwa.infrastructure.ticket.database.order.mapper.join.query.UserTicketOrderListJoinQuery;
import com.nuwa.infrastructure.ticket.database.order.mapper.join.query.UserVoucherOrderListJoinQuery;
import com.nuwa.infrastructure.ticket.database.order.vo.UserTicketOrderListVO;
import com.nuwa.infrastructure.ticket.database.order.vo.UserVoucherOrderListVO;
import com.nuwa.infrastructure.ticket.database.scenicspot.JoinSqlConstant;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;


/**
 * UserVoucherOrderJoinMapper 用户核销码订单查询
 *
 * @author huyonghack@163.com
 * @since 2021-07-08
 */
@Repository
public interface UserVoucherOrderJoinMapper extends
        JoinListMapper<UserVoucherOrderListJoinQuery, UserVoucherOrderListVO> {

    String SQL = "SELECT " +
            " ticket_order.id,ticket_order.amount,ticket_order.scenicspot_id,ticket_order.visit_date,ticket_order.order_no,ticket_order.quantity,ticket_order.unit_price,ticket_order.product_name  from ticket_order ticket_order " +
            " LEFT JOIN scenicspot_product_verification_config scenicspot_product_verification_config  " +
            " on ticket_order.product_id = scenicspot_product_verification_config.scenicspot_product_id  " +
            " and ticket_order.snapshoot_version  = scenicspot_product_verification_config.version" +
            JoinSqlConstant.EW_CUSTOM_SQL_SEGMENT;

    /**
     * 获取用户核销码订单
     *
     * @param wrapper
     * @return
     */
    @Override
    @Select(SQL)
    List<UserVoucherOrderListVO> list(@Param(Constants.WRAPPER) Wrapper<UserVoucherOrderListJoinQuery> wrapper);
}
