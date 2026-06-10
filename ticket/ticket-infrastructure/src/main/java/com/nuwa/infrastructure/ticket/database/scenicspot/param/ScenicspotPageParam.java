package com.nuwa.infrastructure.ticket.database.scenicspot.param;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import io.swagger.annotations.ApiModel;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.nuwa.infrastructure.ticket.database.scenicspot.entity.Scenicspot;
import com.nuwa.client.ticket.dto.clientobject.scenicspot.qry.ScenicspotPageQry;

import com.nuwa.framework.database.PageQry;

/**
 * <pre>
 * 景区poi 分页参数对象
 * </pre>
 *
 * @author huyonghack@163.com
 * @date 2021-10-26
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "景区poi分页参数")
public class ScenicspotPageParam extends PageQry<Scenicspot> {
    private static final long serialVersionUID = 1L;

    private ScenicspotPageQry qry;

    public ScenicspotPageParam(ScenicspotPageQry qry) {
        super(qry);
        this.qry = qry;
    }

    @Override
    public LambdaQueryWrapper<Scenicspot> toQueryWrapper() {
        LambdaQueryWrapper<Scenicspot> queryWrapper = Wrappers.lambdaQuery();
        queryWrapper.select(Scenicspot.class,
                t -> !t.getColumn().endsWith("_editor")
        );
            return queryWrapper;
    }
}
