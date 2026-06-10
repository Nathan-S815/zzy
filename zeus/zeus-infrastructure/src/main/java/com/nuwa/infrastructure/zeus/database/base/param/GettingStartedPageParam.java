package com.nuwa.infrastructure.zeus.database.base.param;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.nuwa.infrastructure.zeus.database.base.entity.GettingStarted;
import com.nuwa.client.zeus.dto.clientobject.base.qry.GettingStartedPageQry;
import java.util.Objects;

import com.nuwa.framework.database.PageQry;

/**
 * <pre>
 * 新手入门 分页参数对象
 * </pre>
 *
 * @author huyonghack@163.com
 * @date 2021-07-12
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "新手入门分页参数")
public class GettingStartedPageParam extends PageQry<GettingStarted> {
    private static final long serialVersionUID = 1L;

    private GettingStartedPageQry qry;

    public GettingStartedPageParam(GettingStartedPageQry qry) {
        super(qry);
        this.qry = qry;
    }

    @Override
    public LambdaQueryWrapper<GettingStarted> toQueryWrapper() {
        LambdaQueryWrapper<GettingStarted> queryWrapper = Wrappers.lambdaQuery();
        queryWrapper.select(GettingStarted.class,
                t -> !t.getColumn().endsWith("_editor")
        );
            return queryWrapper;
    }
}
