package com.nuwa.infrastructure.zeus.database.feedback.param;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.nuwa.infrastructure.zeus.database.feedback.entity.ProblemFeedback;
import com.nuwa.client.zeus.dto.clientobject.feedback.qry.ProblemFeedbackPageQry;
import java.util.Objects;

import com.nuwa.framework.database.PageQry;

/**
 * <pre>
 * 商户问题反馈信息 分页参数对象
 * </pre>
 *
 * @author huyonghack@163.com
 * @date 2021-07-19
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "商户问题反馈信息分页参数")
public class ProblemFeedbackPageParam extends PageQry<ProblemFeedback> {
    private static final long serialVersionUID = 1L;

    private ProblemFeedbackPageQry qry;

    public ProblemFeedbackPageParam(ProblemFeedbackPageQry qry) {
        super(qry);
        this.qry = qry;
    }

    @Override
    public LambdaQueryWrapper<ProblemFeedback> toQueryWrapper() {
        LambdaQueryWrapper<ProblemFeedback> queryWrapper = Wrappers.lambdaQuery();
        queryWrapper.select(ProblemFeedback.class,
                t -> !t.getColumn().endsWith("_editor")
                        && !t.getColumn().equalsIgnoreCase(ProblemFeedback.DELETE_FLAG)
        );
        queryWrapper.eq(StrUtil.isNotBlank(qry.getMchName()), ProblemFeedback::getMchName, qry.getMchName());
        queryWrapper.eq(Objects.nonNull(qry.getStatus()), ProblemFeedback::getStatus, qry.getStatus());
        queryWrapper.eq(StrUtil.isNotBlank(qry.getRegistPhone()), ProblemFeedback::getRegistPhone, qry.getRegistPhone());
        queryWrapper.eq(StrUtil.isNotBlank(qry.getContactPeople()), ProblemFeedback::getContactPeople, qry.getContactPeople());
        queryWrapper.eq(StrUtil.isNotBlank(qry.getContactPhone()), ProblemFeedback::getContactPhone, qry.getContactPhone());
        queryWrapper.between(BeanUtil.isNotEmpty(qry.getCreateTimeStart())&&BeanUtil.isNotEmpty(qry.getCreateTimeEnd()), ProblemFeedback::getCreateTime, qry.getCreateTimeStart(),qry.getCreateTimeEnd());
        queryWrapper.orderByDesc(ProblemFeedback::getCreateTime);
        return queryWrapper;
    }
}
