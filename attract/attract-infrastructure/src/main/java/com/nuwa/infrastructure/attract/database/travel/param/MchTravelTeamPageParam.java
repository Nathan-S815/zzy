package com.nuwa.infrastructure.attract.database.travel.param;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.nuwa.client.attract.dto.clientobject.travel.qry.MchTravelTeamPageQry;
import com.nuwa.framework.database.PageQry;
import com.nuwa.infrastructure.attract.database.travel.entity.TravelTeam;
import io.swagger.annotations.ApiModel;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

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
public class MchTravelTeamPageParam extends PageQry<TravelTeam> {
    private static final long serialVersionUID = 1L;

    private MchTravelTeamPageQry qry;

    public MchTravelTeamPageParam(MchTravelTeamPageQry qry) {
        super(qry);
        this.qry = qry;
    }

    @Override
    public LambdaQueryWrapper<TravelTeam> toQueryWrapper() {
//        LambdaQueryWrapper<TravelTeam> queryWrapper = Wrappers.lambdaQuery();
//        queryWrapper.select(TravelTeam.class,
//            t -> !t.getColumn().endsWith("_editor")
//        );
//        queryWrapper.orderByDesc(TravelTeam::getTeamId);
//        queryWrapper.eq(Objects.nonNull(qry.getTeamId()), TravelTeam::getTeamId, qry.getTeamId());
//        queryWrapper.apply(Objects.nonNull(qry.getRefId()), "FIND_IN_SET ('" + qry.getRefId() + "',ref_id)");
//        queryWrapper.ge(Objects.nonNull(qry.getApplyBeginTime()), TravelTeam::getCreateTime, qry.getApplyBeginTime());
//        queryWrapper.le(Objects.nonNull(qry.getApplyEndTime()), TravelTeam::getCreateTime, qry.getApplyEndTime());
//        queryWrapper.in(CollectionUtils.isNotEmpty(qry.getUserIds()), TravelTeam::getUserId, qry.getUserIds());
//        if (Objects.nonNull(qry.getStatus())) {
//            if (qry.getStatus().equals(1)) {
//                queryWrapper.eq(TravelTeam::getTeamStatus, TeamStatusEnum.MCH_AUDIT.getCode());
//            } else {
//                queryWrapper.gt(TravelTeam::getTeamStatus, TeamStatusEnum.MCH_AUDIT_REJECT.getCode());
//            }
//        } else {
//            queryWrapper.gt(TravelTeam::getTeamStatus, TeamStatusEnum.WAIT_AUDIT.getCode());
//        }
        return null;
    }
}
