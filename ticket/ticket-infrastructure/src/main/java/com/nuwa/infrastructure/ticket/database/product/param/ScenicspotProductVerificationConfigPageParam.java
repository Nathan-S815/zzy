package com.nuwa.infrastructure.ticket.database.product.param;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.nuwa.infrastructure.ticket.database.product.entity.ScenicspotProductVerificationConfig;
import com.nuwa.client.ticket.dto.clientobject.product.qry.ScenicspotProductVerificationConfigPageQry;
import java.util.Objects;

import com.nuwa.framework.database.PageQry;

/**
 * <pre>
 * 景区产品核销规则配置 分页参数对象
 * </pre>
 *
 * @author huyonghack@163.com
 * @date 2021-10-25
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "景区产品核销规则配置分页参数")
public class ScenicspotProductVerificationConfigPageParam extends PageQry<ScenicspotProductVerificationConfig> {
    private static final long serialVersionUID = 1L;

    private ScenicspotProductVerificationConfigPageQry qry;

    public ScenicspotProductVerificationConfigPageParam(ScenicspotProductVerificationConfigPageQry qry) {
        super(qry);
        this.qry = qry;
    }

    @Override
    public LambdaQueryWrapper<ScenicspotProductVerificationConfig> toQueryWrapper() {
        LambdaQueryWrapper<ScenicspotProductVerificationConfig> queryWrapper = Wrappers.lambdaQuery();
        queryWrapper.select(ScenicspotProductVerificationConfig.class,
                t -> !t.getColumn().endsWith("_editor")
        );
            return queryWrapper;
    }
}
