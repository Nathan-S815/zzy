package com.nuwa.infrastructure.ticket.database.callcenter.param;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.nuwa.client.ticket.dto.clientobject.callcenter.qry.OnlineProblemTypePageQry;
import com.nuwa.framework.database.PageQry;
import com.nuwa.infrastructure.ticket.database.callcenter.entity.OnlineProblemType;
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
public class OnlineProblemTypePageParam extends PageQry<OnlineProblemType> {
    private static final long serialVersionUID = 1L;

    private OnlineProblemTypePageQry qry;

    public OnlineProblemTypePageParam(OnlineProblemTypePageQry qry) {
        super(qry);
        this.qry = qry;
    }

    @Override
    public LambdaQueryWrapper<OnlineProblemType> toQueryWrapper() {
        LambdaQueryWrapper<OnlineProblemType> queryWrapper = Wrappers.lambdaQuery();
        queryWrapper.select(OnlineProblemType.class,
                t -> !t.getColumn().endsWith("_editor")
        );
            return queryWrapper;
    }
}
