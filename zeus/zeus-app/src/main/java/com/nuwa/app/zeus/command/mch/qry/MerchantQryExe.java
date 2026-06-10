package com.nuwa.app.zeus.command.mch.qry;

import com.alibaba.cola.dto.SingleResponse;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.nuwa.app.zeus.vo.AppTree;
import com.nuwa.client.zeus.dto.clientobject.mch.qry.MerchantPageQry;
import com.nuwa.client.zeus.dto.clientobject.mch.qry.MerchantQry;
import com.nuwa.framework.cola.starter.cmd.AbstractQryExe;
import com.nuwa.infrastructure.zeus.database.mch.entity.Merchant;
import com.nuwa.infrastructure.zeus.database.mch.param.MerchantPageParam;
import com.nuwa.infrastructure.zeus.database.mch.service.MerchantService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * GetAppTreeQryExe
 *
 * @author hy
 * @date 2021/6/3 18:14
 * @since 1.0.0
 */
@Slf4j
@Component
public class MerchantQryExe extends AbstractQryExe<MerchantQry, SingleResponse<Merchant>> {

    @Autowired
    private MerchantService merchantService;

    @Override
    protected SingleResponse<Merchant> handle(MerchantQry cmd) {
        Merchant merchant = merchantService.getById(cmd.getMchId());
        return SingleResponse.of(merchant);
    }
}
