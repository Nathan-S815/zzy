package com.nuwa.app.ticket.command.mall.query;

import com.alibaba.cola.dto.SingleResponse;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.nuwa.client.ticket.dto.clientobject.mall.qry.MallTradePageQry;
import com.nuwa.framework.cola.starter.cmd.AbstractQryExe;
import com.nuwa.infrastructure.ticket.database.mall.entity.MallTrade;
import com.nuwa.infrastructure.ticket.database.mall.param.MallTradePageParam;
import com.nuwa.infrastructure.ticket.database.mall.service.MallTradeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class MallTradePageQryExe extends AbstractQryExe<MallTradePageQry, SingleResponse> {

    @Autowired
    private MallTradeService mallTradeService;

    @Override
    protected SingleResponse handle(MallTradePageQry cmd) {
        IPage<MallTrade> mallTradeIPage = mallTradeService.paginateByParam(new MallTradePageParam(cmd));
        return SingleResponse.of(mallTradeIPage);
    }
}
