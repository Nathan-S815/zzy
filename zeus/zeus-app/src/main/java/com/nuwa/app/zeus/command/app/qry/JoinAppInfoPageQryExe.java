package com.nuwa.app.zeus.command.app.qry;

import com.alibaba.cola.dto.SingleResponse;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.nuwa.client.zeus.dto.clientobject.app.qry.AppInfoPageJoinQry;
import com.nuwa.framework.cola.starter.cmd.BaseQryExe;
import com.nuwa.framework.database.tk.join.transfer.QryTransfer;
import com.nuwa.infrastructure.zeus.database.app.entity.MerchantAppPageVO;
import com.nuwa.infrastructure.zeus.database.app.mapper.JoinAppInfoMapper;
import com.nuwa.infrastructure.zeus.database.app.query.PageAppJoinQuery;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * JoinAppInfoPageQryExe
 *
 * @author hy
 * @date 2021/6/3 18:14
 * @since 1.0.0
 */
@Slf4j
@Component
public class JoinAppInfoPageQryExe
        extends BaseQryExe<AppInfoPageJoinQry, IPage<MerchantAppPageVO>>
        implements QryTransfer<AppInfoPageJoinQry, PageAppJoinQuery> {

    @Autowired
    private JoinAppInfoMapper joinAppInfoMapper;

    @Override
    protected SingleResponse<IPage<MerchantAppPageVO>> handle(AppInfoPageJoinQry query) {
        return SingleResponse.of(joinAppInfoMapper.paginateByQuery(transfer(query)));
    }

    @Override
    public PageAppJoinQuery transfer(AppInfoPageJoinQry joinAppInfoPageQry) {
        PageAppJoinQuery query = new PageAppJoinQuery();
        BeanUtils.copyProperties(joinAppInfoPageQry, query);
        return query;
    }
}
