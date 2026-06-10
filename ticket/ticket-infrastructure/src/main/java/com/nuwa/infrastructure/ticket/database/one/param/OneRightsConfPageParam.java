package com.nuwa.infrastructure.ticket.database.one.param;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.nuwa.infrastructure.ticket.database.one.entity.OneRightsConf;
import com.nuwa.client.ticket.dto.clientobject.one.qry.OneRightsConfPageQry;

import java.util.Objects;

import com.nuwa.framework.database.PageQry;

/**
 * <pre>
 * 一码通商户端权益配置 分页参数对象
 * </pre>
 *
 * @author huyonghack@163.com
 * @date 2022-10-25
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "一码通商户端权益配置分页参数")
public class OneRightsConfPageParam extends PageQry<OneRightsConf> {
    private static final long serialVersionUID = 1L;

    private OneRightsConfPageQry qry;

    public OneRightsConfPageParam(OneRightsConfPageQry qry) {
        super(qry);
        this.qry = qry;
    }

    @Override
    public LambdaQueryWrapper<OneRightsConf> toQueryWrapper() {
        LambdaQueryWrapper<OneRightsConf> queryWrapper = Wrappers.lambdaQuery();
        queryWrapper.select(OneRightsConf.class,
                t -> !t.getColumn().endsWith("_editor")
        );
        queryWrapper.between(Objects.nonNull(qry.getCreateTimeStart()) && !Objects.nonNull(qry.getCreateTimeEnd()), OneRightsConf::getCreateTime, qry.getCreateTimeStart(), qry.getCreateTimeEnd());
        queryWrapper.eq(Objects.nonNull(qry.getUserAware().getMchId()), OneRightsConf::getMchId, qry.getUserAware().getMchId());
        return queryWrapper;
    }
}
