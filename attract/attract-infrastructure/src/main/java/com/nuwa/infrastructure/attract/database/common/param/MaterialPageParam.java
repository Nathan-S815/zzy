package com.nuwa.infrastructure.attract.database.common.param;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.nuwa.infrastructure.attract.database.common.entity.Material;
import com.nuwa.client.attract.dto.clientobject.common.qry.MaterialPageQry;
import java.util.Objects;

import com.nuwa.framework.database.PageQry;

/**
 * <pre>
 *  分页参数对象
 * </pre>
 *
 * @author nanhuang @南皇
 * @date 2022-09-08
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "分页参数")
public class MaterialPageParam extends PageQry<Material> {
    private static final long serialVersionUID = 1L;

    private MaterialPageQry qry;

    public MaterialPageParam(MaterialPageQry qry) {
        super(qry);
        this.qry = qry;
    }

    @Override
    public LambdaQueryWrapper<Material> toQueryWrapper() {
        LambdaQueryWrapper<Material> queryWrapper = Wrappers.lambdaQuery();
        queryWrapper.select(Material.class,
                t -> !t.getColumn().endsWith("_editor")
        );
            return queryWrapper;
    }
}
