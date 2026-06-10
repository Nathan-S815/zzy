package com.nuwa.infrastructure.zeus.database.app.param;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.nuwa.client.zeus.dto.clientobject.app.qry.AppInfoPageQry;
import com.nuwa.framework.database.PageQry;
import com.nuwa.infrastructure.zeus.database.app.entity.AppInfo;
import com.nuwa.infrastructure.zeus.enums.DeleteFlagEnum;
import io.swagger.annotations.ApiModel;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

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
public class AppInfoListMallPageParam extends PageQry<AppInfo> {
    private static final long serialVersionUID = 1L;

    private AppInfoPageQry qry;

    public AppInfoListMallPageParam(AppInfoPageQry qry) {
        super(qry);
        this.qry = qry;
    }

    @Override
    public LambdaQueryWrapper<AppInfo> toQueryWrapper() {
        LambdaQueryWrapper<AppInfo> queryWrapper = Wrappers.lambdaQuery();
        queryWrapper.eq(BeanUtil.isNotEmpty(qry.getAppType()),AppInfo::getAppType,qry.getAppType());
        queryWrapper.eq(AppInfo::getDeleteFlag, DeleteFlagEnum.NORMAL.getCode());
        queryWrapper.eq(AppInfo::getStatus, 1);
        queryWrapper.eq(AppInfo::getPrivatization, 0);
        queryWrapper.orderByDesc(AppInfo::getRecommend);
        return queryWrapper;
    }
}
