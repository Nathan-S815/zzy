package com.nuwa.infrastructure.zeus.database.app.param;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.nuwa.infrastructure.zeus.database.mch.entity.Merchant;
import com.nuwa.infrastructure.zeus.enums.DeleteFlagEnum;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.nuwa.infrastructure.zeus.database.app.entity.AppInfo;
import com.nuwa.client.zeus.dto.clientobject.app.qry.AppInfoPageQry;

import java.util.Objects;

import com.nuwa.framework.database.PageQry;

/**
 * <pre>
 * 应用信息 分页参数对象
 * </pre>
 *
 * @author huyonghack@163.com
 * @date 2021-05-31
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "应用信息分页参数")
public class AppInfoPageParam extends PageQry<AppInfo> {
    private static final long serialVersionUID = 1L;

    private AppInfoPageQry qry;

    public AppInfoPageParam(AppInfoPageQry qry) {
        super(qry);
        this.qry = qry;
    }

    @Override
    public LambdaQueryWrapper<AppInfo> toQueryWrapper() {
        LambdaQueryWrapper<AppInfo> queryWrapper = Wrappers.lambdaQuery();
        queryWrapper.select(AppInfo.class,
                t -> !t.getColumn().endsWith("_editor")
        );
        queryWrapper.like(StrUtil.isNotBlank(qry.getAppName()), AppInfo::getAppName, qry.getAppName());
        queryWrapper.eq(StrUtil.isNotBlank(qry.getProvider()), AppInfo::getProvider, qry.getProvider());
        queryWrapper.eq(BeanUtil.isNotEmpty(qry.getAppType()), AppInfo::getAppType, qry.getAppType());
        queryWrapper.eq(BeanUtil.isNotEmpty(qry.getStatus()), AppInfo::getStatus, qry.getStatus());
        queryWrapper.eq(AppInfo::getDeleteFlag, DeleteFlagEnum.NORMAL.getCode());
        queryWrapper.orderByDesc(AppInfo::getCreateTime);
        return queryWrapper;
    }
}
