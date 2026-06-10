package com.nuwa.infrastructure.attract.database.teamuserref.param;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import io.swagger.annotations.ApiModel;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.nuwa.infrastructure.attract.database.teamuserref.entity.TeamUserRef;
import com.nuwa.client.attract.dto.clientobject.teamuseref.qry.TeamUserRefPageQry;

import com.nuwa.framework.database.PageQry;

/**
 * <pre>
 *  分页参数对象
 * </pre>
 *
 * @author nanhuang @南皇
 * @date 2022-11-08
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "分页参数")
public class TeamUserRefPageParam extends PageQry<TeamUserRef> {
    private static final long serialVersionUID = 1L;

    private TeamUserRefPageQry qry;

    public TeamUserRefPageParam(TeamUserRefPageQry qry) {
        super(qry);
        this.qry = qry;
    }

    @Override
    public LambdaQueryWrapper<TeamUserRef> toQueryWrapper() {
        LambdaQueryWrapper<TeamUserRef> queryWrapper = Wrappers.lambdaQuery();
        queryWrapper.select(TeamUserRef.class,
                t -> !t.getColumn().endsWith("_editor")
        );
            return queryWrapper;
    }
}
