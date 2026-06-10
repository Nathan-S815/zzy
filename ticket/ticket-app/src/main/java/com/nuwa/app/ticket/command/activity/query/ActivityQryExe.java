package com.nuwa.app.ticket.command.activity.query;

import com.alibaba.cola.dto.SingleResponse;
import com.nuwa.client.ticket.dto.clientobject.activity.qry.CultureActivityQry;
import com.nuwa.framework.cola.starter.cmd.AbstractQryExe;
//import com.nuwa.infrastructure.ticket.bo.CultureActivityDatailBO;
import com.nuwa.infrastructure.ticket.database.activity.entity.CultureActivity;
import com.nuwa.infrastructure.ticket.database.activity.service.CultureActivityService;
//import com.nuwa.infrastructure.ticket.database.collect.service.CollectLogService;
//import com.nuwa.infrastructure.ticket.database.like.service.LikeLogService;
import com.nuwa.infrastructure.ticket.enums.ErrorEnum;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Slf4j
@Component
public class ActivityQryExe extends AbstractQryExe<CultureActivityQry, SingleResponse> {

    @Autowired
    private CultureActivityService cultureActivityService;
//    @Autowired
//    private LikeLogService likeLogService;
//    @Autowired
//    private CollectLogService collectLogService;

    @Override
    protected SingleResponse handle(CultureActivityQry cmd) {
        if(Objects.isNull(cmd.getId())){
            return ErrorEnum.MISSING_REQUIRED_PARAMS.buildFailure();
        }
        CultureActivity byId = cultureActivityService.getById(cmd.getId());

//        CultureActivityDatailBO entity = CultureActivityDatailBO.getEntity(byId);
//
//        entity.setMyLike(likeLogService.myLiked(cmd.getUserAware().getUserId(),cmd.getId(),111L));
//        entity.setMyCollect(collectLogService.myCollected(cmd.getUserAware().getUserId(),cmd.getId(),111L));
//        entity.setCollectCount(collectLogService.getCollectCountByItemId(Integer.valueOf(cmd.getId()+""),111));
        return SingleResponse.of(byId);
    }


}
