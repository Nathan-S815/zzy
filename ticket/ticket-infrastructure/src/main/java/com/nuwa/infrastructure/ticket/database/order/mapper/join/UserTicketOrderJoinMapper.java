package com.nuwa.infrastructure.ticket.database.order.mapper.join;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import com.nuwa.framework.database.tk.join.mapper.JoinListMapper;
import com.nuwa.infrastructure.ticket.database.order.mapper.join.query.UserTicketOrderListJoinQuery;
import com.nuwa.infrastructure.ticket.database.order.vo.UserTicketOrderListVO;
import com.nuwa.infrastructure.ticket.database.scenicspot.JoinSqlConstant;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;


/**
 * UserTicketOrderJoinMapper 用户订单查询
 *
 * @author huyonghack@163.com
 * @since 2021-07-08
 */
@Repository
public interface UserTicketOrderJoinMapper extends
        JoinListMapper<UserTicketOrderListJoinQuery, UserTicketOrderListVO> {

    String SQL = "SELECT ticket_order.id,ticket_order.create_time,ticket_order.user_id,ticket_order.quantity,ticket_order.`status`,tourist_info.mobile,tourist_info.id_card from ticket_order ticket_order LEFT JOIN tourist_info tourist_info on ticket_order.id = tourist_info.order_id " +
            JoinSqlConstant.EW_CUSTOM_SQL_SEGMENT;

    /**
     * 获取用户订单列表
     *
     * @param wrapper
     * @return
     */
    @Override
    @Select(SQL)
    List<UserTicketOrderListVO> list(@Param(Constants.WRAPPER) Wrapper<UserTicketOrderListJoinQuery> wrapper);
}
