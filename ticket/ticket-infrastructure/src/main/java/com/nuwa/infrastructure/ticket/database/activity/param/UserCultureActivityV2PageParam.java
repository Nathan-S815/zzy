package com.nuwa.infrastructure.ticket.database.activity.param;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.nuwa.client.ticket.dto.clientobject.activity.qry.UserCultureActivityV2PageQry;
import com.nuwa.framework.database.PageQry;
import com.nuwa.infrastructure.ticket.database.activity.entity.CultureActivity;
import com.nuwa.infrastructure.ticket.enums.DeleteFlagEnum;
import io.swagger.annotations.ApiModel;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.util.Objects;

/**
 * <pre>
 * 文化活动 分页参数对象
 * </pre>
 *
 * @author huyonghack@163.com
 * @date 2021-06-08
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "文化活动分页参数")
public class UserCultureActivityV2PageParam extends PageQry<CultureActivity> {
    private static final long serialVersionUID = 1L;

    private UserCultureActivityV2PageQry qry;

    public UserCultureActivityV2PageParam(UserCultureActivityV2PageQry qry) {
        super(qry);
        this.qry = qry;
    }

    @Override
    public LambdaQueryWrapper<CultureActivity> toQueryWrapper() {
        LambdaQueryWrapper<CultureActivity> queryWrapper = Wrappers.lambdaQuery();
        queryWrapper.select(CultureActivity.class,
                t -> !t.getColumn().endsWith("_editor")
        );
        queryWrapper.eq(CultureActivity::getMchId, qry.getMchId());
        queryWrapper.eq(CultureActivity::getAppId, qry.getAppId());
        queryWrapper.eq(Objects.nonNull(qry.getCategoryId()), CultureActivity::getCategoryId, qry.getCategoryId());
        queryWrapper.ge(Objects.nonNull(qry.getHoldTimeStart()), CultureActivity::getHoldTimeStart, qry.getHoldTimeStart());
        queryWrapper.le(Objects.nonNull(qry.getHoldTimeEnd()), CultureActivity::getHoldTimeEnd, qry.getHoldTimeEnd());
        queryWrapper.eq(CultureActivity::getDeleteFlag, DeleteFlagEnum.NORMAL.getCode());
        return queryWrapper;
    }
}
