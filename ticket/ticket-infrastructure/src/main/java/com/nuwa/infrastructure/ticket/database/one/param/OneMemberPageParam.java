package com.nuwa.infrastructure.ticket.database.one.param;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.nuwa.client.ticket.dto.clientobject.one.qry.OneMemberPageQry;
import com.nuwa.framework.database.PageQry;
import com.nuwa.infrastructure.ticket.database.one.entity.OneMember;
import io.swagger.annotations.ApiModel;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import org.omg.CORBA.OBJ_ADAPTER;

import java.util.Objects;

/**
 * <pre>
 * 一码通会员 分页参数对象
 * </pre>
 *
 * @author huyonghack@163.com
 * @date 2022-10-27
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "一码通会员分页参数")
public class OneMemberPageParam extends PageQry<OneMember> {
    private static final long serialVersionUID = 1L;

    private OneMemberPageQry qry;

    public OneMemberPageParam(OneMemberPageQry qry) {
        super(qry);
        this.qry = qry;
    }

    @Override
    public LambdaQueryWrapper<OneMember> toQueryWrapper() {
        LambdaQueryWrapper<OneMember> queryWrapper = Wrappers.lambdaQuery();
        queryWrapper.orderByDesc(OneMember::getCreateTime);
        queryWrapper.select(OneMember.class,
                t -> !t.getColumn().endsWith("_editor")
        );
        queryWrapper.eq(Objects.nonNull(qry.getUserAware().getMchId()) && qry.getUserAware().getMchId() != -1, OneMember::getMchId, qry.getUserAware().getMchId());
        queryWrapper.like(!StrUtil.isBlankOrUndefined(qry.getUserNike()), OneMember::getUserNike, qry.getUserNike());
        if (Objects.nonNull(qry.getCreateTimeStart()) && Objects.nonNull(qry.getCreateTimeEnd())) {
            queryWrapper.between(OneMember::getCreateTime, DateUtil.beginOfDay(qry.getCreateTimeStart()), DateUtil.endOfDay(qry.getCreateTimeEnd()));
        }
        return queryWrapper;
    }
}
