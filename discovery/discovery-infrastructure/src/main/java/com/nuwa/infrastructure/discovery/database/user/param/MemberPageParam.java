package com.nuwa.infrastructure.discovery.database.user.param;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.nuwa.infrastructure.discovery.database.user.entity.Member;
import com.nuwa.client.discovery.dto.clientobject.user.qry.MemberPageQry;
import java.util.Objects;

import com.nuwa.framework.database.PageQry;

/**
 * <pre>
 * 达人用户表 分页参数对象
 * </pre>
 *
 * @author huyonghack@163.com
 * @date 2021-11-09
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "达人用户表分页参数")
public class MemberPageParam extends PageQry<Member> {
    private static final long serialVersionUID = 1L;

    private MemberPageQry qry;

    public MemberPageParam(MemberPageQry qry) {
        super(qry);
        this.qry = qry;
    }

    @Override
    public LambdaQueryWrapper<Member> toQueryWrapper() {
        LambdaQueryWrapper<Member> queryWrapper = Wrappers.lambdaQuery();
        queryWrapper.select(Member.class,
                t -> !t.getColumn().endsWith("_editor")
        );
        if (Objects.nonNull(qry.getCreateTimeEnd())) {
            qry.setCreateTimeEnd(DateUtil.endOfDay(qry.getCreateTimeEnd()));
        }
        queryWrapper.orderByDesc(Member::getUserId);
        queryWrapper.eq(Objects.nonNull(qry.getUserId()), Member::getUserId, qry.getUserId());
        queryWrapper.eq(StrUtil.isNotBlank(qry.getMobile()), Member::getUserPhone, qry.getMobile());
        queryWrapper.like(!StrUtil.isBlankOrUndefined(qry.getUserNike()), Member::getUserNike, qry.getUserNike());
        queryWrapper.between(Objects.nonNull(qry.getCreateTimeStart()) && !Objects.nonNull(qry.getCreateTimeEnd()), Member::getCreateTime,qry.getCreateTimeStart(),qry.getCreateTimeEnd());
        return queryWrapper;
    }
}
