package com.zzy.api.service.ticket.impl;

import com.zzy.api.service.ticket.IFutureTicketInformationService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zzy.db.dao.ticket.FutureTicketInformationMapper;
import com.zzy.db.entity.ticket.FutureTicketInformation;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author zzy
 * @since 2020-05-23
 */
@Service
public class FutureTicketInformationServiceImpl extends ServiceImpl<FutureTicketInformationMapper, FutureTicketInformation> implements IFutureTicketInformationService {

}
