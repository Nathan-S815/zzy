package com.nuwa.infrastructure.ticket.database.dict.param;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.nuwa.client.ticket.dto.clientobject.dict.qry.SysDictDataPageQry;
import com.nuwa.framework.database.PageQry;
import com.nuwa.infrastructure.ticket.database.dict.entity.SysDictData;
import io.swagger.annotations.ApiModel;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <pre>
 * 字典数据表 分页参数对象
 * </pre>
 *
 * @author huyonghack@163.com
 * @date 2021-04-27
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "字典数据表分页参数")
public class SysDictDataPageParam extends PageQry<SysDictData> {
    private static final long serialVersionUID = 1L;

    private SysDictDataPageQry qry;

    public SysDictDataPageParam(SysDictDataPageQry qry) {
        super(qry);
        this.qry = qry;
    }

    @Override
    public LambdaQueryWrapper<SysDictData> toQueryWrapper() {
        LambdaQueryWrapper<SysDictData> queryWrapper = Wrappers.lambdaQuery();
        queryWrapper.select(SysDictData.class,
                t -> !t.getColumn().endsWith("_editor")
        );
        queryWrapper.eq(SysDictData::getStatus,0);
        queryWrapper.like(!StrUtil.isBlankOrUndefined(qry.getName()), SysDictData::getRemark,qry.getName());
        queryWrapper.groupBy(SysDictData::getDictColumn);
        return queryWrapper;
    }
}
