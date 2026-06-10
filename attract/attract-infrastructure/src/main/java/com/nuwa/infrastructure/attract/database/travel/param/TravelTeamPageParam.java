package com.nuwa.infrastructure.attract.database.travel.param;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.nuwa.infrastructure.attract.database.travel.entity.TravelTeam;
import com.nuwa.client.attract.dto.clientobject.travel.qry.TravelTeamPageQry;

import java.util.Objects;

import com.nuwa.framework.database.PageQry;

/**
 * <pre>
 *  分页参数对象
 * </pre>
 *
 * @author nanhuang @南皇
 * @date 2022-09-14
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "分页参数")
public class TravelTeamPageParam extends PageQry<TravelTeam> {
    private static final long serialVersionUID = 1L;

    private TravelTeamPageQry qry;

    public TravelTeamPageParam(TravelTeamPageQry qry) {
        super(qry);
        this.qry = qry;
    }

    @Override
    public LambdaQueryWrapper<TravelTeam> toQueryWrapper() {
        LambdaQueryWrapper<TravelTeam> queryWrapper = Wrappers.lambdaQuery();
        queryWrapper.select(TravelTeam.class,
            t -> !t.getColumn().endsWith("_editor")
        );
        if (qry.getOrderBy() == null){
            queryWrapper.orderByDesc(TravelTeam::getTeamId);
        } else if (qry.getOrderBy().equals(1)){
            queryWrapper.orderByDesc(TravelTeam::getReward);
            queryWrapper.orderByDesc(TravelTeam::getTeamId);
        }
        queryWrapper.like(StrUtil.isNotBlank(qry.getLeaderName()), TravelTeam::getLeaderName, qry.getLeaderName());
        queryWrapper.like(StrUtil.isNotBlank(qry.getRefMch()), TravelTeam::getRefMch, qry.getRefMch());
        queryWrapper.eq(Objects.nonNull(qry.getUserId()), TravelTeam::getUserId, qry.getUserId());
        queryWrapper.eq(Objects.nonNull(qry.getTeamStatus()), TravelTeam::getTeamStatus, qry.getTeamStatus());
        queryWrapper.eq(Objects.nonNull(qry.getTeamType()), TravelTeam::getTeamType, qry.getTeamType());
        queryWrapper.ge(Objects.nonNull(qry.getApplyBeginTime()), TravelTeam::getCreateTime, qry.getApplyBeginTime());
        queryWrapper.le(Objects.nonNull(qry.getApplyEndTime()), TravelTeam::getCreateTime, qry.getApplyEndTime());
        return queryWrapper;
    }
}
