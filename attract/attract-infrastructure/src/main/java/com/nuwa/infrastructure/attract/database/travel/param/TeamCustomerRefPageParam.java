package com.nuwa.infrastructure.attract.database.travel.param;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.nuwa.infrastructure.attract.database.travel.entity.TeamCustomerRef;
import com.nuwa.client.attract.dto.clientobject.travel.qry.TeamCustomerRefPageQry;
import java.util.Objects;

import com.nuwa.framework.database.PageQry;

/**
 * <pre>
 * 团队-客户关联表 分页参数对象
 * </pre>
 *
 * @author nanhuang @南皇
 * @date 2022-09-15
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "团队-客户关联表分页参数")
public class TeamCustomerRefPageParam extends PageQry<TeamCustomerRef> {
    private static final long serialVersionUID = 1L;

    private TeamCustomerRefPageQry qry;

    public TeamCustomerRefPageParam(TeamCustomerRefPageQry qry) {
        super(qry);
        this.qry = qry;
    }

    @Override
    public LambdaQueryWrapper<TeamCustomerRef> toQueryWrapper() {
        LambdaQueryWrapper<TeamCustomerRef> queryWrapper = Wrappers.lambdaQuery();
        queryWrapper.select(TeamCustomerRef.class,
                t -> !t.getColumn().endsWith("_editor")
        );
            return queryWrapper;
    }
}
