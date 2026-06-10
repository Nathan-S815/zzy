package com.nuwa.infrastructure.ticket.database.callcenter.param;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.nuwa.client.ticket.dto.clientobject.callcenter.qry.CallCenterPageQry;
import com.nuwa.framework.database.PageQry;
import com.nuwa.infrastructure.ticket.database.callcenter.entity.CallCenter;
import com.nuwa.infrastructure.ticket.enums.DeleteFlagEnum;
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
public class CallCenterPageParam extends PageQry<CallCenter> {
    private static final long serialVersionUID = 1L;

    private CallCenterPageQry qry;

    public CallCenterPageParam(CallCenterPageQry qry) {
        super(qry);
        this.qry = qry;
    }

    @Override
    public LambdaQueryWrapper<CallCenter> toQueryWrapper() {
        LambdaQueryWrapper<CallCenter> queryWrapper = Wrappers.lambdaQuery();
        queryWrapper.select(CallCenter.class,
                t -> !t.getColumn().endsWith("_editor")
        );
        queryWrapper.eq(CallCenter::getDeleteFlag, DeleteFlagEnum.NORMAL.getCode());
        queryWrapper.eq(CallCenter::getMchId,qry.getUserAware().getMchId());
        queryWrapper.eq(CallCenter::getAppId, qry.getAppId());
        return queryWrapper;
    }
}
