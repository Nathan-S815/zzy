package com.nuwa.infrastructure.ticket.database.complaint.param;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.nuwa.client.ticket.dto.clientobject.complaint.qry.ComplaintPageQry;
import com.nuwa.framework.database.PageQry;
import com.nuwa.infrastructure.ticket.database.complaint.entity.Complaint;
import com.nuwa.infrastructure.ticket.enums.DeleteFlagEnum;
import io.swagger.annotations.ApiModel;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.util.Objects;

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
public class ComplaintPageParam extends PageQry<Complaint> {
    private static final long serialVersionUID = 1L;

    private ComplaintPageQry qry;

    public ComplaintPageParam(ComplaintPageQry qry) {
        super(qry);
        this.qry = qry;
    }

    @Override
    public LambdaQueryWrapper<Complaint> toQueryWrapper() {
        LambdaQueryWrapper<Complaint> queryWrapper = Wrappers.lambdaQuery();
        queryWrapper.select(Complaint.class,
                t -> !t.getColumn().endsWith("_editor")
        );
//        queryWrapper.eq(Complaint::getAppId, qry.getAppId());
        queryWrapper.like(StrUtil.isNotBlank(qry.getUserName()), Complaint::getUserName,qry.getUserName());
        queryWrapper.eq(Complaint::getMchId,qry.getUserAware().getMchId());
        queryWrapper.eq(Objects.nonNull(qry.getAuditStatus()), Complaint::getAuditStatus, qry.getAuditStatus());
        queryWrapper.eq(!StrUtil.isBlankOrUndefined(qry.getAuditResult()), Complaint::getAuditResult, qry.getAuditResult());
        queryWrapper.eq( Complaint::getDeleteFlag, DeleteFlagEnum.NORMAL);
        queryWrapper.orderByDesc(Complaint::getCreateTime);
        return queryWrapper;
    }
}
