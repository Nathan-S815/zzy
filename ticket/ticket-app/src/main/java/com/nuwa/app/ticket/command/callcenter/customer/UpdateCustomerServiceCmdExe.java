package com.nuwa.app.ticket.command.callcenter.customer;

import com.alibaba.cola.dto.SingleResponse;
import com.alibaba.cola.exception.Assert;
import com.nuwa.client.ticket.dto.clientobject.callcenter.customer.UpdateCustomerServiceCmd;
import com.nuwa.client.ticket.dto.clientobject.callcenter.customer.co.UpdateCustomerServiceCO;
import com.nuwa.framework.cola.starter.cmd.AbstractCmdExe;
import com.nuwa.infrastructure.ticket.database.callcenter.entity.CustomerService;
import com.nuwa.infrastructure.ticket.database.callcenter.service.CustomerServiceService;
import com.nuwa.infrastructure.ticket.enums.ErrorEnum;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Date;

@Slf4j
@Component
public class UpdateCustomerServiceCmdExe extends AbstractCmdExe<UpdateCustomerServiceCmd, SingleResponse> {
    @Autowired
    private CustomerServiceService customerServiceService;
    @Override
    protected SingleResponse handle(UpdateCustomerServiceCmd cmd) {
        UpdateCustomerServiceCO updateCustomerServiceCO = cmd.getUpdateCustomerServiceCO();
        CustomerService one = customerServiceService.lambdaQuery().eq(CustomerService::getId, updateCustomerServiceCO.getId()).one();
        Assert.notNull(one, ErrorEnum.DATA_NON, "客服基础信息(id:" + cmd.getUpdateCustomerServiceCO().getId() + ")不存在");
        one.setAppId(cmd.getAppId()).setName(updateCustomerServiceCO.getName()).setPic(updateCustomerServiceCO.getPic()).setRemark(updateCustomerServiceCO.getRemark()).setUpdateTime(new Date());
        boolean updateById = one.updateById();
        if (updateById) {
            return ErrorEnum.DATA_SUCCESS.buildSuccess(one.getId());
        }
        return ErrorEnum.DATA_FAIL.buildFailure();
    }
}
