package com.nuwa.infrastructure.ticket.database.pubsystem.param;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.nuwa.client.ticket.dto.clientobject.notice.qry.NoticeInfoPageQry;
import com.nuwa.client.ticket.dto.clientobject.pubsystem.qry.PsAppInfoPageQry;
import com.nuwa.framework.base.UserAware;
import com.nuwa.framework.database.PageQry;
import com.nuwa.infrastructure.ticket.database.notice.entity.NoticeInfo;
import com.nuwa.infrastructure.ticket.database.pubsystem.entity.PsAppInfo;
import io.swagger.annotations.ApiModel;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.util.Date;
import java.util.Objects;

/**
 * <pre>
 * 公告表 分页参数对象
 * </pre>
 *
 * @author huyonghack@163.com
 * @date 2022-03-15
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "公告表分页参数")
public class PsAppInfoPageParam extends PageQry<PsAppInfo> {
    private static final long serialVersionUID = 1L;

    private PsAppInfoPageQry qry;

    public PsAppInfoPageParam(PsAppInfoPageQry qry) {
        super(qry);
        this.qry = qry;
    }

    @Override
    public LambdaQueryWrapper<PsAppInfo> toQueryWrapper() {
        UserAware userAware = qry.getUserAware();
        LambdaQueryWrapper<PsAppInfo> queryWrapper = Wrappers.lambdaQuery();
        queryWrapper.select(PsAppInfo.class,
                t -> !t.getColumn().endsWith("_editor")
        );
        queryWrapper.orderByDesc(PsAppInfo::getGmtAuth);
        queryWrapper.eq(StrUtil.isNotBlank(qry.getAppStatus()), PsAppInfo::getAppStatus, qry.getAppStatus());
        queryWrapper.eq(StrUtil.isNotBlank(qry.getAppId()), PsAppInfo::getAlipayAppId, qry.getAppId());
        queryWrapper.like(StrUtil.isNotBlank(qry.getAppName()), PsAppInfo::getAppName, qry.getAppName());
        if (Objects.nonNull(qry.getAuthBegin()) && Objects.nonNull(qry.getAuthEnd())) {
            queryWrapper.between(PsAppInfo::getGmtAuth, DateUtil.beginOfDay(qry.getAuthBegin()), DateUtil.endOfDay(qry.getAuthEnd()));
        }
        if (Objects.nonNull(qry.getPublishBegin()) && Objects.nonNull(qry.getPublishEnd())) {
            queryWrapper.between(PsAppInfo::getGmtPublish, DateUtil.beginOfDay(qry.getPublishBegin()), DateUtil.endOfDay(qry.getPublishEnd()));
        }
        return queryWrapper;
    }
}
