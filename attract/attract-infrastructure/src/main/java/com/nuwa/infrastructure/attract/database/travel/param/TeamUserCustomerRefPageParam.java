package com.nuwa.infrastructure.attract.database.travel.param;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.nuwa.infrastructure.attract.database.travel.entity.TeamUserCustomerRef;
import com.nuwa.client.attract.dto.clientobject.travel.qry.TeamUserCustomerRefPageQry;
import java.util.Objects;

import com.nuwa.framework.database.PageQry;

/**
 * <pre>
 *  分页参数对象
 * </pre>
 *
 * @author nanhuang @南皇
 * @date 2022-09-15
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "分页参数")
public class TeamUserCustomerRefPageParam extends PageQry<TeamUserCustomerRef> {
    private static final long serialVersionUID = 1L;

    private TeamUserCustomerRefPageQry qry;

    public TeamUserCustomerRefPageParam(TeamUserCustomerRefPageQry qry) {
        super(qry);
        this.qry = qry;
    }

    @Override
    public LambdaQueryWrapper<TeamUserCustomerRef> toQueryWrapper() {
        LambdaQueryWrapper<TeamUserCustomerRef> queryWrapper = Wrappers.lambdaQuery();
        queryWrapper.select(TeamUserCustomerRef.class,
                t -> !t.getColumn().endsWith("_editor")
        );
            return queryWrapper;
    }
}
