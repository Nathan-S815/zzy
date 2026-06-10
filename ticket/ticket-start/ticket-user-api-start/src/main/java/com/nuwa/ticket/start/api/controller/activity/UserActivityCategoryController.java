package com.nuwa.ticket.start.api.controller.activity;

import com.alibaba.cola.dto.SingleResponse;
import com.nuwa.app.ticket.command.activity.query.*;
import com.nuwa.client.ticket.dto.clientobject.activity.qry.*;
import com.nuwa.framework.base.UserAware;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * ExampleOrderController
 *
 * @author hy
 * @date 2021/4/15 14:09
 * @since 1.0.0
 */
@Api(tags = {"文化活动分类"})
@Slf4j
@RestController
@RequestMapping("/user/cultureActivityCategory")
public class UserActivityCategoryController {

    @Autowired
    private GetCultureActivityCategoryQryExe getCultureActivityCategoryQryExe;

    @ApiOperation(value = "查询所有活动分类")
    @GetMapping(value = "/list")
    public SingleResponse<?> add(GetCultureActivityCategoryQry cmd, UserAware userAware){
        return getCultureActivityCategoryQryExe.execute(cmd);
    }

}
