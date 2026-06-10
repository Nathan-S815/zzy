package com.nuwa.infrastructure.ticket.database.order.mapper.join;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.nuwa.framework.database.tk.join.mapper.JoinListMapper;
import com.nuwa.framework.database.tk.join.mapper.JoinPagingMapper;
import com.nuwa.framework.database.tk.join.query.BaseJoinListQuery;
import com.nuwa.infrastructure.ticket.database.order.mapper.join.query.PayChannelOrderJoinPageJoinQuery;
import com.nuwa.infrastructure.ticket.database.order.mapper.join.query.SupplierOrderJoinPageJoinQuery;
import com.nuwa.infrastructure.ticket.database.order.vo.PayChannelOrderPageVO;
import com.nuwa.infrastructure.ticket.database.order.vo.SupplierOrderPageVO;
import com.nuwa.infrastructure.ticket.database.scenicspot.JoinSqlConstant;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;


/**
 * PayChannelOrderJoinMapper 支付渠渠道订单查询
 *
 * @author huyonghack@163.com
 * @since 2021-07-08
 */
@Repository
public interface PayChannelOrderJoinMapper extends
        JoinPagingMapper<PayChannelOrderJoinPageJoinQuery, PayChannelOrderPageVO>  {

    String SQL = "SELECT" +
            " ticket_order.id," +
            " ticket_order.order_no," +
            " ticket_order.unit_price," +
            " ticket_order.product_id," +
            " ticket_order.visit_date," +
            " ticket_order.create_time," +
            " ticket_order.amount," +
            " ticket_order.quantity," +
            " ticket_order.refunded_quantity," +
            " ticket_order.already_consume_quantity," +
            " ticket_order.`status`," +
            " ticket_order.product_name," +
            " ticket_order.supplier_product_code," +
            " channel_payment_order.mch_pay_order_no," +
            " channel_payment_order.channel_mch_no," +
            " channel_payment_order.pay_type," +
            " channel_payment_order.time_paid," +
            " channel_payment_order.`status` as 'pay_status'" +
            " FROM" +
            " channel_payment_order channel_payment_order" +
            " LEFT JOIN ticket_order ticket_order ON channel_payment_order.order_id = ticket_order.id " +
            JoinSqlConstant.EW_CUSTOM_SQL_SEGMENT;


    /**
     * 分页查询
     *
     * @param page    分页参数
     * @param wrapper 查询条件
     * @param extMap  扩展参数
     * @return Page<PayChannelOrderPageVO>
     */
    @Override
    @Select(SQL)
    Page<PayChannelOrderPageVO> page(IPage<?> page, @Param(Constants.WRAPPER) Wrapper<PayChannelOrderJoinPageJoinQuery> wrapper, Map<String, String> extMap);
}
