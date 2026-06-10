package com.nuwa.infrastructure.ticket.database.callcenter.param;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.nuwa.client.ticket.dto.clientobject.callcenter.qry.CustomerServicePageQry;
import com.nuwa.framework.database.PageQry;
import com.nuwa.infrastructure.ticket.database.callcenter.entity.CustomerService;
import io.swagger.annotations.ApiModel;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <pre>
 * 客服基础信息表 分页参数对象
 * </pre>
 *
 * @author huyonghack@163.com
 * @date 2021-05-15
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "客服基础信息表分页参数")
public class CustomerServicePageParam extends PageQry<CustomerService> {
    private static final long serialVersionUID = 1L;

    private CustomerServicePageQry qry;

    public CustomerServicePageParam(CustomerServicePageQry qry) {
        super(qry);
        this.qry = qry;
    }

    @Override
    public LambdaQueryWrapper<CustomerService> toQueryWrapper() {
        LambdaQueryWrapper<CustomerService> queryWrapper = Wrappers.lambdaQuery();
        queryWrapper.select(CustomerService.class,
                t -> !t.getColumn().endsWith("_editor")
        );
            return queryWrapper;
    }
}
