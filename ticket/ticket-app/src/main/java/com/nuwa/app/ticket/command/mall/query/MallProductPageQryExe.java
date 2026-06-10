package com.nuwa.app.ticket.command.mall.query;

import com.alibaba.cola.dto.SingleResponse;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.nuwa.client.ticket.dto.clientobject.mall.qry.MallProductPageQry;
import com.nuwa.framework.cola.starter.cmd.AbstractQryExe;
import com.nuwa.infrastructure.ticket.database.mall.entity.MallProduct;
import com.nuwa.infrastructure.ticket.database.mall.param.MallProductPageParam;
import com.nuwa.infrastructure.ticket.database.mall.service.MallProductService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class MallProductPageQryExe extends AbstractQryExe<MallProductPageQry, SingleResponse> {

    @Autowired
    private MallProductService mallProductService;

    @Override
    protected SingleResponse handle(MallProductPageQry cmd) {
        IPage<MallProduct> mallProductIPage = mallProductService.paginateByParam(new MallProductPageParam(cmd));
        return SingleResponse.of(mallProductIPage);
    }
}
