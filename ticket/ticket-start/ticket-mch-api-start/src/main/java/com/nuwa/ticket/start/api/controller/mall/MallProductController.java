package com.nuwa.ticket.start.api.controller.mall;

import com.alibaba.cola.dto.SingleResponse;
import com.nuwa.app.ticket.command.mall.*;
import com.nuwa.app.ticket.command.mall.query.MallProductPageQryExe;
import com.nuwa.app.ticket.command.mall.query.MallProductQryExe;
import com.nuwa.client.ticket.dto.clientobject.mall.*;
import com.nuwa.client.ticket.dto.clientobject.mall.qry.MallProductPageQry;
import com.nuwa.client.ticket.dto.clientobject.mall.qry.MallProductQry;
import com.nuwa.framework.base.UserAware;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author ROOT
 * @since 2021-02-05
 */
@Api(tags = {"文创产品-相关接口"})
@Slf4j
@RestController
@RequestMapping("/product")
public class MallProductController {

    @Autowired
    private CreateMallProductCmdExe createMallProductCmdExe;

    @Autowired
    private EditMallProductCmdExe editMallProductCmdExe;

    @Autowired
    private DelMallProductCmdExe delMallProductCmdExe;

    @Autowired
    private OnMallProductCmdExe onMallProductCmdExe;

    @Autowired
    private OffMallProductCmdExe offMallProductCmdExe;

    @Autowired
    private MallProductQryExe mallProductQryExe;

    @Autowired
    private MallProductPageQryExe mallProductPageQryExe;

    @ApiOperation(value = "新增数据")
    @PostMapping(value = "/add")
    public SingleResponse<?> add(@RequestBody CreateMallProductCmd cmd, UserAware userAware) throws Exception {
        return createMallProductCmdExe.execute(cmd);
    }

    @ApiOperation(value = "更新数据")
    @PostMapping(value = "/edit")
    public SingleResponse<?> edit(@RequestBody EditMallProductCmd cmd, UserAware userAware) throws Exception {
        return editMallProductCmdExe.execute(cmd);
    }

    @ApiOperation(value = "删除数据")
    @GetMapping(value = "/del")
    public SingleResponse<?> del(DelMallProductCmd cmd, UserAware userAware) throws Exception {
        return delMallProductCmdExe.execute(cmd);
    }

    @ApiOperation(value = "上架")
    @GetMapping(value = "/on")
    public SingleResponse<?> on(OnMallProductCmd cmd, UserAware userAware) throws Exception {
        return onMallProductCmdExe.execute(cmd);
    }

    @ApiOperation(value = "下架")
    @GetMapping(value = "/off")
    public SingleResponse<?> off(OffMallProductCmd cmd, UserAware userAware) throws Exception {
        return offMallProductCmdExe.execute(cmd);
    }

    @ApiOperation(value = "根据id查询数据")
    @GetMapping(value = "/getById")
    public SingleResponse<?> getById(MallProductQry cmd, UserAware userAware) throws Exception {
        return mallProductQryExe.execute(cmd);
    }

    @ApiOperation(value = "分页查询")
    @GetMapping(value = "/page")
    public SingleResponse<?> page(MallProductPageQry cmd, UserAware userAware) throws Exception {
        return mallProductPageQryExe.execute(cmd);
    }

}
