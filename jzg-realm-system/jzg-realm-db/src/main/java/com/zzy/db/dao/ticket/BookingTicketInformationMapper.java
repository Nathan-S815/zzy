package com.zzy.db.dao.ticket;

import com.zzy.db.entity.ticket.BookingTicketInformation;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import java.util.List;

/**
 * <p>
 * 景区当日团体/个人订票信息表 Mapper 接口
 * </p>
 *
 * @author zzy
 * @since 2020-05-23
 */
public interface BookingTicketInformationMapper extends BaseMapper<BookingTicketInformation> {

    int batchInsert(List<BookingTicketInformation> bookingTicketInformations);

    BookingTicketInformation selectOneByInfo(BookingTicketInformation gr);
}
