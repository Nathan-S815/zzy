package com.nuwa.ticket.start.api.controller.callcenter;

import com.alibaba.cola.dto.SingleResponse;
import com.nuwa.app.ticket.command.callcenter.customer.GetCustomerServiceAppCmdExe;
import com.nuwa.client.ticket.dto.clientobject.callcenter.customer.GetCustomerServiceAppCmd;
import com.nuwa.framework.base.UserAware;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Api(tags = {"客服基础数据"})
@Slf4j
@RestController
@RequestMapping("/app/customer")
public class CustomerServiceAppController {
    @Autowired
    private GetCustomerServiceAppCmdExe getCustomerServiceCmdExe;

    @ApiOperation(value = "获取客服基础数据")
    @GetMapping(value = "/getCustomerService")
    public SingleResponse<?> getCustomerService(GetCustomerServiceAppCmd cmd, UserAware userAware) throws Exception {
        return getCustomerServiceCmdExe.execute(cmd);
    }
}
