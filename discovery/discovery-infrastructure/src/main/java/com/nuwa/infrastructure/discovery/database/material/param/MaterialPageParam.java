package com.nuwa.infrastructure.discovery.database.material.param;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.nuwa.client.zeus.dto.clientobject.material.qry.MaterialPageQry;
import com.nuwa.framework.database.PageQry;
import com.nuwa.infrastructure.discovery.database.material.entity.Material;
import io.swagger.annotations.ApiModel;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.util.Objects;

/**
 * <pre>
 *  分页参数对象
 * </pre>
 *
 * @author huyonghack@163.com
 * @date 2021-04-27
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
        queryWrapper.eq(BeanUtil.isNotEmpty(qry.getUserAware().getMchId()), Material::getMchId, qry.getUserAware().getMchId());
        queryWrapper.eq(BeanUtil.isNotEmpty(qry.getTypeId()), Material::getTypeId, qry.getTypeId());
        queryWrapper.eq(Material::getStatus, 1);
        queryWrapper.eq(Objects.nonNull(qry.getFileType()), Material::getFileType, qry.getFileType());
        queryWrapper.orderByDesc(Material::getCreateTime);
        return queryWrapper;
    }
}
