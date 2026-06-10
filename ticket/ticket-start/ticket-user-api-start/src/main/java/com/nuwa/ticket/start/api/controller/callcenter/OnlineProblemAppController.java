package com.nuwa.ticket.start.api.controller.callcenter;

import com.alibaba.cola.dto.SingleResponse;
import com.nuwa.app.ticket.command.callcenter.problem.query.GetOnlineProblemAppByNameExe;
import com.nuwa.app.ticket.command.callcenter.problem.query.GetOnlineProblemAppQryExe;
import com.nuwa.app.ticket.command.callcenter.type.query.ListAllOnlineProblemTypeQryExe;
import com.nuwa.app.ticket.command.callcenter.type.query.ListOnlineProblemTypeByParentIdQryExe;
import com.nuwa.client.ticket.dto.clientobject.callcenter.problem.GetOnlineProblemAppCmd;
import com.nuwa.client.ticket.dto.clientobject.callcenter.type.ListAllOnlineProblemTypeCmd;
import com.nuwa.client.ticket.dto.clientobject.callcenter.type.ListOnlineProblemTypeByParentIdCmd;
import com.nuwa.framework.base.UserAware;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Api(tags = {"在线客服相关接口"})
@Slf4j
@RestController
@RequestMapping("/app/problem")
public class OnlineProblemAppController {
    @Autowired
    private ListAllOnlineProblemTypeQryExe listAllOnlineProblemTypeQryExe;
    @Autowired
    private ListOnlineProblemTypeByParentIdQryExe listOnlineProblemTypeByParentIdQryExe;
    @Autowired
    private GetOnlineProblemAppQryExe getOnlineProblemAppQryExe;
    @Autowired
    private GetOnlineProblemAppByNameExe getOnlineProblemAppByNameExe;

    @ApiOperation(value = "获取问题类别")
    @GetMapping(value = "/listProblemCategory")
    public SingleResponse<?> listProblemCategory(ListAllOnlineProblemTypeCmd cmd, UserAware userAware) throws Exception {
        return listAllOnlineProblemTypeQryExe.execute(cmd);
    }

    @ApiOperation(value = "根据父id获取问题类型")
    @GetMapping(value = "/listProblemType")
    public SingleResponse<?> listProblemType(ListOnlineProblemTypeByParentIdCmd cmd, UserAware userAware) throws Exception {
        return listOnlineProblemTypeByParentIdQryExe.execute(cmd);
    }
    @ApiOperation(value = "根据问题类别，类型，问题获取在线问题回答")
    @GetMapping(value = "/getOnlineProblemApp")
    public SingleResponse<?> getOnlineProblemApp(GetOnlineProblemAppCmd cmd, UserAware userAware) throws Exception {
        return getOnlineProblemAppQryExe.execute(cmd);
    }
    @ApiOperation(value = "根据问题关键字模糊查询获取在线问题回答")
    @GetMapping(value = "/getOnlineProblemAppByName")
    public SingleResponse<?> getOnlineProblemAppByName(GetOnlineProblemAppCmd cmd, UserAware userAware) throws Exception {
        return getOnlineProblemAppByNameExe.execute(cmd);
    }
}
