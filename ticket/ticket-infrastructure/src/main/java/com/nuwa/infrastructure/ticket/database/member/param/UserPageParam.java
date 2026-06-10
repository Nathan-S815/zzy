package com.nuwa.infrastructure.ticket.database.member.param;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.nuwa.client.ticket.dto.clientobject.user.qry.UserPageQry;
import com.nuwa.framework.base.UserAware;
import com.nuwa.framework.database.PageQry;
import com.nuwa.infrastructure.ticket.database.member.entity.Member;
import io.swagger.annotations.ApiModel;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.util.Objects;

/**
 * <pre>
 * 商户供应商 分页参数对象
 * </pre>
 *
 * @author huyonghack@163.com
 * @date 2021-12-17
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "用户分页参数")
public class UserPageParam extends PageQry<Member> {
    private static final long serialVersionUID = 1L;

    private UserPageQry qry;

    public UserPageParam(UserPageQry qry) {
        super(qry);
        this.qry = qry;
    }

    @Override
    public LambdaQueryWrapper<Member> toQueryWrapper() {
        UserAware userAware = qry.getUserAware();
        LambdaQueryWrapper<Member> queryWrapper = Wrappers.lambdaQuery();
        queryWrapper.orderByDesc(Member::getUserId);
        queryWrapper.select(Member.class,
                t -> !t.getColumn().endsWith("_editor")
        );
        queryWrapper.like(StrUtil.isNotBlank(qry.getUserNike()), Member::getUserNike, qry.getUserNike());
        queryWrapper.eq(StrUtil.isNotBlank(qry.getUid()), Member::getShareCode, qry.getUid());
        if (Objects.nonNull(qry.getCreateTimeStart())) {
            queryWrapper.ge(Objects.nonNull(qry.getCreateTimeStart()), Member::getCreateTime, DateUtil.beginOfDay(qry.getCreateTimeStart()));
        }
        if (Objects.nonNull(qry.getCreateTimeEnd())) {
            queryWrapper.le(Objects.nonNull(qry.getCreateTimeEnd()), Member::getCreateTime, DateUtil.endOfDay(qry.getCreateTimeEnd()));
        }
        queryWrapper.eq(Objects.nonNull(qry.getUserId()), Member::getUserId, qry.getUserId());
        queryWrapper.eq(Objects.nonNull(userAware.getMchId()) && !userAware.getMchId().equals(-1L), Member::getMchId, userAware.getMchId());
        return queryWrapper;
    }
}
