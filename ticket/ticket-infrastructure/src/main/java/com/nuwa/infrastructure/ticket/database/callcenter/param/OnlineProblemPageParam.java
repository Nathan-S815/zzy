package com.nuwa.infrastructure.ticket.database.callcenter.param;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.nuwa.client.ticket.dto.clientobject.callcenter.qry.OnlineProblemPageQry;
import com.nuwa.framework.database.PageQry;
import com.nuwa.infrastructure.ticket.database.callcenter.entity.OnlineProblem;
import io.swagger.annotations.ApiModel;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <pre>
 *  分页参数对象
 * </pre>
 *
 * @author huyonghack@163.com
 * @date 2021-05-11
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "分页参数")
public class OnlineProblemPageParam extends PageQry<OnlineProblem> {
    private static final long serialVersionUID = 1L;

    private OnlineProblemPageQry qry;

    public OnlineProblemPageParam(OnlineProblemPageQry qry) {
        super(qry);
        this.qry = qry;
    }

    @Override
    public LambdaQueryWrapper<OnlineProblem> toQueryWrapper() {
        LambdaQueryWrapper<OnlineProblem> queryWrapper = Wrappers.lambdaQuery();
        queryWrapper.select(OnlineProblem.class,
                t -> !t.getColumn().endsWith("_editor")
        );
        queryWrapper.eq(OnlineProblem::getDeleteFlag,0);
        queryWrapper.eq(OnlineProblem::getAppId,qry.getAppId());
        queryWrapper.eq(OnlineProblem::getMchId,qry.getUserAware().getMchId());
        queryWrapper.eq(!StrUtil.isBlankOrUndefined(qry.getCategory()), OnlineProblem::getCategory,qry.getCategory());
        queryWrapper.eq(!StrUtil.isBlankOrUndefined(qry.getType()), OnlineProblem::getType,qry.getType());
        queryWrapper.like(!StrUtil.isBlankOrUndefined(qry.getTime()), OnlineProblem::getCreateTime,qry.getTime());
        queryWrapper.orderByDesc(OnlineProblem::getCreateTime);
            return queryWrapper;
    }
}
