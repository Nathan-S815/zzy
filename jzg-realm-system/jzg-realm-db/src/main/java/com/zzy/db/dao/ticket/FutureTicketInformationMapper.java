package com.zzy.db.dao.ticket;

import com.zzy.db.entity.ticket.FutureTicketInformation;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author zzy
 * @since 2020-05-23
 */
public interface FutureTicketInformationMapper extends BaseMapper<FutureTicketInformation> {

    int batchInsert(List<FutureTicketInformation> futureTicketInformations);

    FutureTicketInformation selectOneByInfo(FutureTicketInformation gr);
}
