package com.nuwa.app.ticket.command.mall.query;

import com.alibaba.cola.dto.SingleResponse;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.nuwa.client.ticket.dto.clientobject.mall.qry.UserMallProductPageQry;
import com.nuwa.framework.cola.starter.cmd.AbstractQryExe;
import com.nuwa.infrastructure.ticket.database.mall.entity.MallProduct;
import com.nuwa.infrastructure.ticket.database.mall.param.UserMallProductPageParam;
import com.nuwa.infrastructure.ticket.database.mall.service.MallProductService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class UserMallProductPageQryExe extends AbstractQryExe<UserMallProductPageQry, SingleResponse> {

    @Autowired
    private MallProductService mallProductService;

    @Override
    protected SingleResponse handle(UserMallProductPageQry cmd) {
        IPage<MallProduct> mallProductIPage = mallProductService.paginateByParam(new UserMallProductPageParam(cmd));
        return SingleResponse.of(mallProductIPage);
    }
}
