package com.nuwa.infrastructure.zeus.database.app.param;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.nuwa.infrastructure.zeus.database.app.entity.AppUpdateLog;
import com.nuwa.client.zeus.dto.clientobject.app.qry.AppUpdateLogPageQry;

import java.util.Objects;

import com.nuwa.framework.database.PageQry;

/**
 * <pre>
 * app升级日志 分页参数对象
 * </pre>
 *
 * @author huyonghack@163.com
 * @date 2022-06-27
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "app升级日志分页参数")
public class AppUpdateLogPageParam extends PageQry<AppUpdateLog> {
    private static final long serialVersionUID = 1L;

    private AppUpdateLogPageQry qry;

    public AppUpdateLogPageParam(AppUpdateLogPageQry qry) {
        super(qry);
        this.qry = qry;
    }

    @Override
    public LambdaQueryWrapper<AppUpdateLog> toQueryWrapper() {
        LambdaQueryWrapper<AppUpdateLog> queryWrapper = Wrappers.lambdaQuery();
        queryWrapper.orderByDesc(AppUpdateLog::getVersion);
        queryWrapper.select(AppUpdateLog.class,
                t -> !t.getColumn().endsWith("_editor")
        );
        queryWrapper.eq(Objects.nonNull(qry.getAppId()), AppUpdateLog::getAppId, qry.getAppId());
        queryWrapper.like(StrUtil.isNotBlank(qry.getTitle()), AppUpdateLog::getTitle, qry.getTitle());
        return queryWrapper;
    }
}
