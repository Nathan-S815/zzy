package com.nuwa.infrastructure.ticket.database.complaint.param;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.nuwa.client.ticket.dto.clientobject.complaint.qry.UserComplaintPageQry;
import com.nuwa.framework.database.PageQry;
import com.nuwa.infrastructure.ticket.database.complaint.entity.Complaint;
import com.nuwa.infrastructure.ticket.enums.DeleteFlagEnum;
import io.swagger.annotations.ApiModel;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <pre>
 * 用户投诉 分页参数对象
 * </pre>
 *
 * @author huyonghack@163.com
 * @date 2021-05-31
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "用户投诉分页参数")
public class UserComplaintPageParam extends PageQry<Complaint> {
    private static final long serialVersionUID = 1L;

    private UserComplaintPageQry qry;

    public UserComplaintPageParam(UserComplaintPageQry qry) {
        super(qry);
        this.qry = qry;
    }

    @Override
    public LambdaQueryWrapper<Complaint> toQueryWrapper() {
        LambdaQueryWrapper<Complaint> queryWrapper = Wrappers.lambdaQuery();
        queryWrapper.select(Complaint.class,
                t -> !t.getColumn().endsWith("_editor")
        );
        queryWrapper.eq( Complaint::getUserId, qry.getUserAware().getUserId());
        queryWrapper.eq( Complaint::getDeleteFlag, DeleteFlagEnum.NORMAL);
        queryWrapper.orderByDesc(Complaint::getCreateTime);
        return queryWrapper;
    }
}
