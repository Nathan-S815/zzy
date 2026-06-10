package com.nuwa.infrastructure.ticket.database.scenicspot.param;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.nuwa.infrastructure.ticket.database.scenicspot.entity.ScenicspotLabel;
import com.nuwa.client.ticket.dto.clientobject.scenicspot.qry.ScenicspotLabelPageQry;
import java.util.Objects;

import com.nuwa.framework.database.PageQry;

/**
 * <pre>
 * 景区标签表 分页参数对象
 * </pre>
 *
 * @author huyonghack@163.com
 * @date 2021-10-20
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "景区标签表分页参数")
public class ScenicspotLabelPageParam extends PageQry<ScenicspotLabel> {
    private static final long serialVersionUID = 1L;

    private ScenicspotLabelPageQry qry;

    public ScenicspotLabelPageParam(ScenicspotLabelPageQry qry) {
        super(qry);
        this.qry = qry;
    }

    @Override
    public LambdaQueryWrapper<ScenicspotLabel> toQueryWrapper() {
        LambdaQueryWrapper<ScenicspotLabel> queryWrapper = Wrappers.lambdaQuery();
        queryWrapper.select(ScenicspotLabel.class,
                t -> !t.getColumn().endsWith("_editor")
        );
            return queryWrapper;
    }
}
