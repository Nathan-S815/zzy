package com.nuwa.ticket.start.api.controller.activity;

import com.alibaba.cola.dto.SingleResponse;
import com.nuwa.app.ticket.command.activity.*;
import com.nuwa.app.ticket.command.activity.query.ActivityCategoryPageQryExe;
import com.nuwa.app.ticket.command.activity.query.ActivityCategoryQryExe;
import com.nuwa.client.ticket.dto.clientobject.activity.*;
import com.nuwa.client.ticket.dto.clientobject.activity.qry.CultureActivityCategoryPageQry;
import com.nuwa.client.ticket.dto.clientobject.activity.qry.CultureActivityCategoryQry;
import com.nuwa.framework.base.UserAware;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * ExampleOrderController
 *
 * @author hy
 * @date 2021/4/15 14:09
 * @since 1.0.0
 */
@Api(tags = {"文化活动类别"})
@Slf4j
@RestController
@RequestMapping("/merchant/cultureActivityCategory")
public class MchActivityCategoryController {

    @Autowired
    private CreateActivityCategoryCmdExe createActivityCategoryCmdExe;

    @Autowired
    private DeleteActivityCategoryCmdExe deleteActivityCategoryCmdExe;

    @Autowired
    private EditActivityCategoryCmdExe editActivityCategoryCmdExe;

    @Autowired
    private ActivityCategoryQryExe activityCategoryQryExe;

    @Autowired
    private ActivityCategoryPageQryExe activityCategoryPageQryExe;

    @Autowired
    private OnActivityCategoryCmdExe onActivityCategoryCmdExe;

    @Autowired
    private OffActivityCategoryCmdExe offActivityCategoryCmdExe;

    @ApiOperation(value = "新增数据")
    @PostMapping(value = "/add")
    public SingleResponse<?> add(@RequestBody CreateActivityCategoryCmd cmd, UserAware userAware) {
        return createActivityCategoryCmdExe.execute(cmd);
    }

    @ApiOperation(value = "删除数据")
    @GetMapping(value = "/del")
    public SingleResponse<?> del(DeleteActivityCategoryCmd cmd, UserAware userAware) {
        return deleteActivityCategoryCmdExe.execute(cmd);
    }

    @ApiOperation(value = "修改数据")
    @PostMapping(value = "/update")
    public SingleResponse<?> update(@RequestBody EditActivityCategoryCmd cmd, UserAware userAware) {
        return editActivityCategoryCmdExe.execute(cmd);
    }

    @ApiOperation(value = "根据Id查询")
    @GetMapping(value = "/getById")
    public SingleResponse<?> getById(CultureActivityCategoryQry cmd, UserAware userAware) {
        return activityCategoryQryExe.execute(cmd);
    }

    @ApiOperation(value = "分页查询")
    @GetMapping(value = "/page")
    public SingleResponse<?> page(CultureActivityCategoryPageQry cmd, UserAware userAware) {
        return activityCategoryPageQryExe.execute(cmd);
    }

    @ApiOperation(value = "上架")
    @GetMapping(value = "/on")
    public SingleResponse<?> on(OnActivityCategoryCmd cmd, UserAware userAware) {
        return onActivityCategoryCmdExe.execute(cmd);
    }

    @ApiOperation(value = "下架")
    @GetMapping(value = "/off")
    public SingleResponse<?> off(OffActivityCategoryCmd cmd, UserAware userAware) {
        return offActivityCategoryCmdExe.execute(cmd);
    }

}
