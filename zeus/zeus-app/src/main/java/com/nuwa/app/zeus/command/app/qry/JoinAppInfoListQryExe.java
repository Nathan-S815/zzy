package com.nuwa.app.zeus.command.app.qry;

import com.alibaba.cola.dto.SingleResponse;
import com.nuwa.client.zeus.dto.clientobject.app.qry.AppInfoGetListJoinQry;
import com.nuwa.framework.cola.starter.cmd.AbstractQryExe;
import com.nuwa.infrastructure.zeus.database.app.entity.MerchantAppPageVO;
import com.nuwa.infrastructure.zeus.database.app.mapper.JoinAppInfoMapper;
import com.nuwa.infrastructure.zeus.database.app.query.ListAppByJoinQuery;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * JoinAppInfoListQryExe
 *
 * @author hy
 * @date 2021/6/3 18:14
 * @since 1.0.0
 */
@Slf4j
@Component
public class JoinAppInfoListQryExe extends AbstractQryExe<AppInfoGetListJoinQry, SingleResponse<List<MerchantAppPageVO>>>{

    @Autowired
    private JoinAppInfoMapper joinAppInfoMapper;

    @Override
    protected SingleResponse<List<MerchantAppPageVO>> handle(AppInfoGetListJoinQry qry) {
        ListAppByJoinQuery param = new ListAppByJoinQuery();
        BeanUtils.copyProperties(qry,param);
        return SingleResponse.of(joinAppInfoMapper.listAllByQuery(param));
    }
}
