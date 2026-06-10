package com.nuwa.infrastructure.zeus.database.app.param;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.nuwa.infrastructure.zeus.database.app.entity.AppAttachment;
import com.nuwa.client.zeus.dto.clientobject.app.qry.AppAttachmentPageQry;
import java.util.Objects;

import com.nuwa.framework.database.PageQry;

/**
 * <pre>
 *  分页参数对象
 * </pre>
 *
 * @author huyonghack@163.com
 * @date 2021-05-31
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "分页参数")
public class AppAttachmentPageParam extends PageQry<AppAttachment> {
    private static final long serialVersionUID = 1L;

    private AppAttachmentPageQry qry;

    public AppAttachmentPageParam(AppAttachmentPageQry qry) {
        super(qry);
        this.qry = qry;
    }

    @Override
    public LambdaQueryWrapper<AppAttachment> toQueryWrapper() {
        LambdaQueryWrapper<AppAttachment> queryWrapper = Wrappers.lambdaQuery();
        queryWrapper.select(AppAttachment.class,
                t -> !t.getColumn().endsWith("_editor")
        );
            return queryWrapper;
    }
}
