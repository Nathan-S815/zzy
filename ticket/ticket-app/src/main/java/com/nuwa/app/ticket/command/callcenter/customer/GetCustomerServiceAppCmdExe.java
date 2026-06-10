package com.nuwa.app.ticket.command.callcenter.customer;

import com.alibaba.cola.dto.SingleResponse;
import com.nuwa.client.ticket.dto.clientobject.callcenter.customer.GetCustomerServiceAppCmd;
import com.nuwa.framework.cola.starter.cmd.AbstractQryExe;
import com.nuwa.infrastructure.ticket.database.callcenter.entity.CustomerService;
import com.nuwa.infrastructure.ticket.database.callcenter.service.CustomerServiceService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class GetCustomerServiceAppCmdExe extends AbstractQryExe<GetCustomerServiceAppCmd, SingleResponse> {
    @Autowired
    private CustomerServiceService customerServiceService;
    @Override
    protected SingleResponse handle(GetCustomerServiceAppCmd cmd) {
        CustomerService one = customerServiceService.lambdaQuery()
//                .eq(CustomerService::getAppId, cmd.getUserAware().getMchAppId())
                .eq(CustomerService::getMchId,cmd.getUserAware().getMchId())
                .one();
        return SingleResponse.of(one);
    }
}
