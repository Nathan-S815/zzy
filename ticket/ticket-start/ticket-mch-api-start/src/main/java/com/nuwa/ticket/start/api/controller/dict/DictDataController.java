package com.nuwa.ticket.start.api.controller.dict;

import com.alibaba.cola.dto.SingleResponse;
import com.nuwa.app.ticket.command.dict.DelDictDataCmdExe;
import com.nuwa.app.ticket.command.dict.DictDataAddCmdExe;
import com.nuwa.app.ticket.command.dict.EditDictDataCmdExe;
import com.nuwa.app.ticket.command.dict.query.DictDataListQryExe;
import com.nuwa.app.ticket.command.dict.query.DictGetByColumnQryExe;
import com.nuwa.app.ticket.command.dict.query.SysDictDataPageQryExe;
import com.nuwa.client.ticket.dto.clientobject.dict.*;
import com.nuwa.client.ticket.dto.clientobject.dict.qry.SysDictDataPageQry;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 * 字典数据表 前端控制器
 * </p>
 *
 * @author ROOT
 * @since 2020-11-06
 */
@Api(tags = {"字典数据-相关接口"})
@Slf4j
@RestController
@RequestMapping("/dictData")
public class DictDataController {
    @Autowired
    private DictDataAddCmdExe dictDataAddCmdExe;
    @Autowired
    private DictGetByColumnQryExe dictGetByColumnQryExe;

    @Autowired
    private DictDataListQryExe dictDataListQryExe;
    @Autowired
    private SysDictDataPageQryExe sysDictDataPageQryExe;
    @Autowired
    private EditDictDataCmdExe editDictDataCmdExe;
    @Autowired
    private DelDictDataCmdExe delDictDataCmdExe;

    @ApiOperation(value = "新增字段对应的值")
    @PostMapping(value = "/addByColumn")
    //@RequiresPermissions("dict_edit")
    public SingleResponse<?> addByColumn(DictDataCmd cmd) throws Exception {
        return dictDataAddCmdExe.execute(cmd);
    }
    @ApiOperation(value = "修改字典数据")
    @PostMapping(value = "/editDictData")
    //@RequiresPermissions("dict_edit")
    public SingleResponse<?> editDictData(EditDictDataCmd cmd) throws Exception {
        return editDictDataCmdExe.execute(cmd);
    }
    @ApiOperation(value = "根据id删除字典数据")
    @PostMapping(value = "/delDictData")
    //@RequiresPermissions("dict_del")
    public SingleResponse<?> delDictData(DelDictDataCmd cmd) throws Exception {
        return delDictDataCmdExe.execute(cmd);
    }

    @ApiOperation(value = "获取字段对应的值")
    @GetMapping(value = "listByColumn")
    public SingleResponse<?> listByColumn(DictGetByColumnCmd cmd) throws Exception {
        return dictGetByColumnQryExe.execute(cmd);
    }

    @ApiOperation(value = "所有字典")
    @GetMapping(value = "/list")
    public SingleResponse<?> list() {
        return dictDataListQryExe.execute(new DictDataListCmd());
    }
    @ApiOperation(value = "字典数据分页列表")
    @GetMapping(value = "/page")
    public SingleResponse<?> page(SysDictDataPageQry cmd) {
        return sysDictDataPageQryExe.execute(cmd);
    }

}
