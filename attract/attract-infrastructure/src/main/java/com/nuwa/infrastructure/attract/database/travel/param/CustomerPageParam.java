package com.nuwa.infrastructure.attract.database.travel.param;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.nuwa.infrastructure.attract.database.travel.entity.Customer;
import com.nuwa.client.attract.dto.clientobject.travel.qry.CustomerPageQry;
import java.util.Objects;

import com.nuwa.framework.database.PageQry;

/**
 * <pre>
 * 客户表 分页参数对象
 * </pre>
 *
 * @author nanhuang @南皇
 * @date 2022-09-15
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "客户表分页参数")
public class CustomerPageParam extends PageQry<Customer> {
    private static final long serialVersionUID = 1L;

    private CustomerPageQry qry;

    public CustomerPageParam(CustomerPageQry qry) {
        super(qry);
        this.qry = qry;
    }

    @Override
    public LambdaQueryWrapper<Customer> toQueryWrapper() {
        LambdaQueryWrapper<Customer> queryWrapper = Wrappers.lambdaQuery();
        queryWrapper.select(Customer.class,
                t -> !t.getColumn().endsWith("_editor")
        );
            return queryWrapper;
    }
}
