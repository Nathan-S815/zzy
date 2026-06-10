package com.nuwa.ticket.start.api.controller.callcenter;

import com.alibaba.cola.dto.SingleResponse;
import com.nuwa.app.ticket.command.callcenter.customer.GetCustomerServiceCmdExe;
import com.nuwa.app.ticket.command.callcenter.customer.UpdateCustomerServiceCmdExe;
import com.nuwa.client.ticket.dto.clientobject.callcenter.customer.GetCustomerServiceCmd;
import com.nuwa.client.ticket.dto.clientobject.callcenter.customer.UpdateCustomerServiceCmd;
import com.nuwa.framework.base.UserAware;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@Api(tags = {"客服基础数据"})
@Slf4j
@RestController
@RequestMapping("/customer")
public class CustomerServiceController {
    @Autowired
    private GetCustomerServiceCmdExe getCustomerServiceCmdExe;
    @Autowired
    private UpdateCustomerServiceCmdExe updateCustomerServiceCmdExe;

    @ApiOperation(value = "根据id获取客服基础数据")
    @GetMapping(value = "/getCustomerService")
    public SingleResponse<?> getCustomerService(GetCustomerServiceCmd cmd, UserAware userAware) throws Exception {
        return getCustomerServiceCmdExe.execute(cmd);
    }

    @PostMapping("/updateCustomerService")
    @ApiOperation(value = "修改客服基础数据")
    //@RequiresPermissions("customer_service_edit")
    public SingleResponse updateCustomerService(@RequestBody UpdateCustomerServiceCmd cmd, UserAware userAware) {
        return updateCustomerServiceCmdExe.execute(cmd);
    }
}
