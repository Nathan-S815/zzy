package com.nuwa.infrastructure.ticket.database.order.mapper.join;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.nuwa.framework.database.tk.join.mapper.JoinPagingMapper;
import com.nuwa.infrastructure.ticket.database.order.vo.MchRefundOrderPageVO;
import com.nuwa.infrastructure.ticket.database.order.mapper.join.query.MerchantRefundPageJoinQuery;
import com.nuwa.infrastructure.ticket.database.scenicspot.JoinSqlConstant;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.Map;


/**
 * MerchantRefundOrderJoinMapper 商户端退款订单分页查询
 *
 * @author huyonghack@163.com
 * @since 2021-07-08
 */
@Repository
public interface MerchantRefundOrderJoinMapper extends
        JoinPagingMapper<MerchantRefundPageJoinQuery, MchRefundOrderPageVO> {

    String MERCHANT_SCENICSPOT_SQL = "SELECT   " +
            "  ticket_order.id,   " +
            "  ticket_order.order_no,   " +
            "  ticket_order.product_name,   " +
            "  ticket_order.create_time as createOrderTime,   " +
            "  ticket_order.product_id,   " +
            "  ticket_order.supplier_product_code,   " +
            "  ticket_order.scenicspot_id,   " +
            "  ticket_order.available_consume_quantity,   " +
            "  ticket_order.link_name,   " +
            "  ticket_order.link_mobile,   " +
            "  ticket_order.visit_date,   " +
            "  ticket_order.supplier_id,   " +
            "  ticket_order.quantity,   " +
            "  ticket_refund.id as 'refundId',   " +
            "  ticket_refund.refund_biz_code,   " +
            "  ticket_refund.time_audit,   " +
            "  ticket_refund.time_refund,   " +
            "  ticket_refund.create_time,   " +
            "  ticket_refund.audit_status,   " +
            "  ticket_refund.refund_reason,   " +
            "  ticket_refund.refund_serial_no,   " +
            "  ticket_refund.quantity as refundQuantity,   " +
            "  ticket_refund.amount as refundAmount,   " +
            "  ticket_refund.`status`    " +
            "FROM   " +
            "  ticket_refund ticket_refund   " +
            "  LEFT JOIN ticket_order ticket_order ON ticket_refund.order_id = ticket_order.id  " +
            JoinSqlConstant.EW_CUSTOM_SQL_SEGMENT;

    /**
     * 用户退款分页查询
     *
     * @param page    分页参数
     * @param wrapper 查询条件
     * @param extMap  扩展参数
     * @return Page<UserRefundOrderPageVO>
     */
    @Override
    @Select(MERCHANT_SCENICSPOT_SQL)
    Page<MchRefundOrderPageVO> page(IPage<?> page, @Param(Constants.WRAPPER) Wrapper<MerchantRefundPageJoinQuery> wrapper, Map<String, String> extMap);

}
