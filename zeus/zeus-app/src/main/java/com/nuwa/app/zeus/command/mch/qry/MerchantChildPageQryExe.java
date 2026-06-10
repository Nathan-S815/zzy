package com.nuwa.app.zeus.command.mch.qry;

import cn.hutool.core.bean.BeanUtil;
import com.alibaba.cola.dto.SingleResponse;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.nuwa.app.zeus.vo.AppTree;
import com.nuwa.client.zeus.dto.clientobject.mch.qry.MerchantChildPageQry;
import com.nuwa.client.zeus.dto.clientobject.mch.qry.MerchantPageQry;
import com.nuwa.framework.cola.starter.cmd.AbstractQryExe;
import com.nuwa.infrastructure.zeus.database.base.entity.BaseUser;
import com.nuwa.infrastructure.zeus.database.base.param.BaseUserPageParam;
import com.nuwa.infrastructure.zeus.database.base.param.MerchantChildPageParam;
import com.nuwa.infrastructure.zeus.database.base.service.BaseUserService;
import com.nuwa.infrastructure.zeus.database.mch.entity.Merchant;
import com.nuwa.infrastructure.zeus.database.mch.param.MerchantPageParam;
import com.nuwa.infrastructure.zeus.database.mch.service.MerchantService;
import lombok.Data;
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
public class MerchantChildPageQryExe extends AbstractQryExe<MerchantChildPageQry, SingleResponse<IPage<BaseUser>>> {

    @Autowired
    private BaseUserService baseUserService;

    @Override
    protected SingleResponse<IPage<BaseUser>> handle(MerchantChildPageQry cmd) {
        IPage<BaseUser> baseUserIPage = baseUserService.paginateByParam(new MerchantChildPageParam(cmd));
        return SingleResponse.of(baseUserIPage);
    }
}
