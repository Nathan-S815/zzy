package com.nuwa.infrastructure.ticket.database.order.mapper.join;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.nuwa.framework.database.tk.join.mapper.JoinPagingMapper;
import com.nuwa.infrastructure.ticket.database.order.vo.UserRefundOrderPageVO;
import com.nuwa.infrastructure.ticket.database.order.mapper.join.query.UserRefundPageJoinQuery;
import com.nuwa.infrastructure.ticket.database.scenicspot.JoinSqlConstant;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.Map;


/**
 * UserRefundOrderJoinMapper 用户退款订单分页查询
 *
 * @author huyonghack@163.com
 * @since 2021-07-08
 */
@Repository
public interface UserRefundOrderJoinMapper extends
        JoinPagingMapper<UserRefundPageJoinQuery, UserRefundOrderPageVO> {

    String SQL = "SELECT" +
            " ticket_refund.id," +
            " ticket_order.product_name," +
            " ticket_refund.create_time," +
            " ticket_refund.refund_serial_no," +
            " ticket_refund.quantity," +
            " ticket_refund.amount," +
            " ticket_refund.`status` " +
            "FROM" +
            " ticket_refund ticket_refund" +
            " LEFT JOIN ticket_order ticket_order ON ticket_refund.order_id = ticket_order.id  " +
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
    @Select(SQL)
    Page<UserRefundOrderPageVO> page(IPage<?> page, @Param(Constants.WRAPPER) Wrapper<UserRefundPageJoinQuery> wrapper, Map<String, String> extMap);

}
