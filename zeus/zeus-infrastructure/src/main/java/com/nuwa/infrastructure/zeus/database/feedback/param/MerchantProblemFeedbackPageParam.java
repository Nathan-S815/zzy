package com.nuwa.infrastructure.zeus.database.feedback.param;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.nuwa.client.zeus.dto.clientobject.feedback.qry.ProblemFeedbackPageQry;
import com.nuwa.framework.database.PageQry;
import com.nuwa.infrastructure.zeus.database.feedback.entity.ProblemFeedback;
import io.swagger.annotations.ApiModel;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.util.Objects;

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
public class MerchantProblemFeedbackPageParam extends PageQry<ProblemFeedback> {
    private static final long serialVersionUID = 1L;

    private ProblemFeedbackPageQry qry;

    public MerchantProblemFeedbackPageParam(ProblemFeedbackPageQry qry) {
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
        queryWrapper.eq(ProblemFeedback::getMchId, qry.getUserAware().getMchId());
        queryWrapper.eq(Objects.nonNull(qry.getStatus()), ProblemFeedback::getStatus, qry.getStatus());
        queryWrapper.between(BeanUtil.isNotEmpty(qry.getCreateTimeStart())&&BeanUtil.isNotEmpty(qry.getCreateTimeEnd()), ProblemFeedback::getCreateTime, qry.getCreateTimeStart(),qry.getCreateTimeEnd());
        queryWrapper.orderByDesc(ProblemFeedback::getCreateTime);
        return queryWrapper;
    }
}
