package com.nuwa.infrastructure.ticket.database.one.param;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.nuwa.client.ticket.dto.clientobject.one.qry.OneOpenApiRecordPageQry;
import com.nuwa.framework.database.PageQry;
import com.nuwa.infrastructure.ticket.database.one.entity.OneOpenApiRecord;
import io.swagger.annotations.ApiModel;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.util.Objects;

/**
 * <pre>
 * 一码通调用记录 分页参数对象
 * </pre>
 *
 * @author huyonghack@163.com
 * @date 2022-10-29
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "一码通调用记录分页参数")
public class OneOpenApiRecordPageParam extends PageQry<OneOpenApiRecord> {
    private static final long serialVersionUID = 1L;

    private OneOpenApiRecordPageQry qry;

    public OneOpenApiRecordPageParam(OneOpenApiRecordPageQry qry) {
        super(qry);
        this.qry = qry;
    }

    @Override
    public LambdaQueryWrapper<OneOpenApiRecord> toQueryWrapper() {
        LambdaQueryWrapper<OneOpenApiRecord> queryWrapper = Wrappers.lambdaQuery();
        queryWrapper.orderByDesc(OneOpenApiRecord::getCreateTime);
        queryWrapper.select(OneOpenApiRecord.class,
                t -> !t.getColumn().endsWith("_editor")
        );
        if (Objects.nonNull(qry.getCreateTimeStart()) && Objects.nonNull(qry.getCreateTimeEnd())) {
            queryWrapper.between(OneOpenApiRecord::getCreateTime, DateUtil.beginOfDay(qry.getCreateTimeStart()), DateUtil.endOfDay(qry.getCreateTimeEnd()));
        }
        queryWrapper.eq(Objects.nonNull(qry.getScanMchId()), OneOpenApiRecord::getScanMchId, qry.getScanMchId());
        queryWrapper.eq(Objects.nonNull(qry.getOneMchId()), OneOpenApiRecord::getOneMchId, qry.getOneMchId());
        queryWrapper.like(StrUtil.isNotBlank(qry.getIdName()), OneOpenApiRecord::getIdName, qry.getIdName());
        queryWrapper.like(StrUtil.isNotBlank(qry.getIdNo()), OneOpenApiRecord::getIdNo, qry.getIdNo());
        queryWrapper.like(StrUtil.isNotBlank(qry.getMobile()), OneOpenApiRecord::getMobile, qry.getMobile());
        return queryWrapper;
    }
}
