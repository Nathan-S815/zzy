package com.zzy.api.service.ticket.impl;

import com.zzy.api.service.ticket.IBookingTicketInformationService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zzy.db.dao.ticket.BookingTicketInformationMapper;
import com.zzy.db.entity.ticket.BookingTicketInformation;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 景区当日团体/个人订票信息表 服务实现类
 * </p>
 *
 * @author zzy
 * @since 2020-05-23
 */
@Service
public class BookingTicketInformationServiceImpl extends ServiceImpl<BookingTicketInformationMapper, BookingTicketInformation> implements IBookingTicketInformationService {

}
