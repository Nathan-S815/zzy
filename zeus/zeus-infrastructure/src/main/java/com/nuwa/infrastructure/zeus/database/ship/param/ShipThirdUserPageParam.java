package com.nuwa.infrastructure.zeus.database.ship.param;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.nuwa.infrastructure.zeus.database.ship.entity.ShipThirdUser;
import com.nuwa.client.zeus.dto.clientobject.ship.qry.ShipThirdUserPageQry;
import java.util.Objects;

import com.nuwa.framework.database.PageQry;

/**
 * <pre>
 * 跳转第三方商户用户信息表 分页参数对象
 * </pre>
 *
 * @author huyonghack@163.com
 * @date 2021-06-10
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "跳转第三方商户用户信息表分页参数")
public class ShipThirdUserPageParam extends PageQry<ShipThirdUser> {
    private static final long serialVersionUID = 1L;

    private ShipThirdUserPageQry qry;

    public ShipThirdUserPageParam(ShipThirdUserPageQry qry) {
        super(qry);
        this.qry = qry;
    }

    @Override
    public LambdaQueryWrapper<ShipThirdUser> toQueryWrapper() {
        LambdaQueryWrapper<ShipThirdUser> queryWrapper = Wrappers.lambdaQuery();
        queryWrapper.select(ShipThirdUser.class,
                t -> !t.getColumn().endsWith("_editor")
        );
            return queryWrapper;
    }
}
