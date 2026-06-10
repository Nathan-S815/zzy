package com.nuwa.infrastructure.ticket.database.notice.param;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.nuwa.framework.base.UserAware;
import io.swagger.annotations.ApiModel;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.nuwa.infrastructure.ticket.database.notice.entity.NoticeInfo;
import com.nuwa.client.ticket.dto.clientobject.notice.qry.NoticeInfoPageQry;

import java.util.Objects;

import com.nuwa.framework.database.PageQry;

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
public class NoticeInfoPageParam extends PageQry<NoticeInfo> {
    private static final long serialVersionUID = 1L;

    private NoticeInfoPageQry qry;

    public NoticeInfoPageParam(NoticeInfoPageQry qry) {
        super(qry);
        this.qry = qry;
    }

    @Override
    public LambdaQueryWrapper<NoticeInfo> toQueryWrapper() {
        UserAware userAware = qry.getUserAware();
        LambdaQueryWrapper<NoticeInfo> queryWrapper = Wrappers.lambdaQuery();
        queryWrapper.select(NoticeInfo.class,
                t -> !t.getColumn().endsWith("_editor")
        );
        if (Objects.nonNull(userAware)) {
            queryWrapper.eq(Objects.nonNull(userAware.getMchId()) && !userAware.getMchId().equals(-1L), NoticeInfo::getMchId, userAware.getMchId());
        }
        queryWrapper.eq(Objects.nonNull(qry.getMchId()), NoticeInfo::getMchId, qry.getMchId());
        queryWrapper.eq(Objects.nonNull(qry.getStatus()), NoticeInfo::getStatus, qry.getStatus());
        queryWrapper.orderByDesc(NoticeInfo::getRecommendStatus);
        queryWrapper.orderByDesc(NoticeInfo::getCreateTime);
        return queryWrapper;
    }
}
