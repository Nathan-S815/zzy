package com.nuwa.ticket.start.api.controller.callcenter;

import cn.hutool.core.bean.BeanUtil;
import com.alibaba.cola.dto.SingleResponse;
import com.alibaba.excel.EasyExcel;
import com.nuwa.app.ticket.command.callcenter.problem.*;
import com.nuwa.app.ticket.command.callcenter.problem.query.GetOnlineProblemQryExe;
import com.nuwa.app.ticket.command.callcenter.problem.query.PageOnlineProblemQryExe;
import com.nuwa.app.ticket.command.callcenter.type.CreateOnlineProblemTypeCmdExe;
import com.nuwa.app.ticket.command.callcenter.type.DelOnlineProblemTypeCmdExe;
import com.nuwa.app.ticket.command.callcenter.type.query.ListAllOnlineProblemTypeByListCmdExe;
import com.nuwa.app.ticket.command.callcenter.type.query.ListAllOnlineProblemTypeQryExe;
import com.nuwa.app.ticket.command.callcenter.type.query.ListOnlineProblemTypeByParentIdQryExe;
import com.nuwa.client.ticket.dto.clientobject.callcenter.problem.CreateOnlineProblemCmd;
import com.nuwa.client.ticket.dto.clientobject.callcenter.problem.DelOnlineProblemCmd;
import com.nuwa.client.ticket.dto.clientobject.callcenter.problem.GetOnlineProblemCmd;
import com.nuwa.client.ticket.dto.clientobject.callcenter.problem.UpdateOnlineProblemCmd;
import com.nuwa.client.ticket.dto.clientobject.callcenter.qry.OnlineProblemPageQry;
import com.nuwa.client.ticket.dto.clientobject.callcenter.type.*;
import com.nuwa.framework.base.UserAware;
import com.nuwa.infrastructure.ticket.database.callcenter.entity.OnlineProblem;
import com.nuwa.infrastructure.ticket.database.callcenter.param.CollectExcel;
import com.nuwa.infrastructure.ticket.database.callcenter.param.OnlineProblemVO;
import com.nuwa.infrastructure.ticket.database.callcenter.service.OnlineProblemService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.Charsets;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URLEncoder;
import java.util.List;
import java.util.stream.Collectors;

@Api(tags = {"在线客服相关接口"})
@Slf4j
@RestController
@RequestMapping("/problem")
public class OnlineProblemController {
    @Autowired
    private CreateOnlineProblemCmdExe createOnlineProblemCmdExe;
    @Autowired
    private DelOnlineProblemCmdExe delOnlineProblemCmdExe;
    @Autowired
    private GetOnlineProblemQryExe getOnlineProblemQryExe;
    @Autowired
    private PageOnlineProblemQryExe pageOnlineProblemQryExe;
    @Autowired
    private UpdateOnlineProblemCmdExe updateOnlineProblemCmdExe;
    @Autowired
    private ListAllOnlineProblemTypeQryExe listAllOnlineProblemTypeQryExe;
    @Autowired
    private ListOnlineProblemTypeByParentIdQryExe listOnlineProblemTypeByParentIdQryExe;
    @Autowired
    private CreateOnlineProblemTypeCmdExe createOnlineProblemTypeCmdExe;
    @Autowired
    private OnlineProblemService onlineProblemService;
    @Autowired
    private ListAllOnlineProblemTypeByListCmdExe listAllOnlineProblemTypeByListCmdExe;
    @Autowired
    private DelOnlineProblemTypeCmdExe delOnlineProblemTypeCmdExe;

    @PostMapping("/createOnlineProblem")
    @ApiOperation(value = "新增在线客服问题")
    //@RequiresPermissions("problem_add")
    public SingleResponse createOnlineProblem(@RequestBody CreateOnlineProblemCmd cmd, UserAware userAware) {
        return createOnlineProblemCmdExe.execute(cmd);
    }
    @PostMapping("/createOnlineProblemType")
    @ApiOperation(value = "新增问题类别，类型")
    //@RequiresPermissions("problem_add")
    public SingleResponse createOnlineProblemType(@RequestBody CreateOnlineProblemTypeCmd cmd, UserAware userAware) {
        return createOnlineProblemTypeCmdExe.execute(cmd);
    }

    @PostMapping("/updateOnlineProblem")
    @ApiOperation(value = "修改在线客服问题")
    //@RequiresPermissions("problem_edit")
    public SingleResponse updateOnlineProblem(@RequestBody UpdateOnlineProblemCmd cmd, UserAware userAware) {
        return updateOnlineProblemCmdExe.execute(cmd);
    }

    @ApiOperation(value = "获取在线客服问题数据列表")
    @GetMapping(value = "/pageOnlineProblem")
    public SingleResponse<?> pageOnlineProblem(OnlineProblemPageQry cmd, UserAware userAware) throws Exception {
        return pageOnlineProblemQryExe.execute(cmd);
    }

    @ApiOperation(value = "根据id获取在线客服问题数据")
    @GetMapping(value = "/getOnlineProblem")
    public SingleResponse<?> getOnlineProblem(GetOnlineProblemCmd cmd, UserAware userAware) throws Exception {
        return getOnlineProblemQryExe.execute(cmd);
    }
    @ApiOperation(value = "批量删除在线客服问题数据")
    @PostMapping(value = "/delOnlineProblem")
    //@RequiresPermissions("problem_del")
    public SingleResponse<?> delOnlineProblem(@RequestBody DelOnlineProblemCmd cmd, UserAware userAware) throws Exception {
        return delOnlineProblemCmdExe.execute(cmd);
    }
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
    @ApiOperation(value = "导出")
    @GetMapping(value = "/excelProblemList")
    //@RequiresPermissions("problem_export")
    public void excelProblemList(HttpServletResponse response) throws IOException {
        List<OnlineProblem> list = onlineProblemService.lambdaQuery().eq(OnlineProblem::getDeleteFlag, 0).list();
        List<OnlineProblemVO> collect = list.stream().map(x -> {
            OnlineProblemVO vo = new OnlineProblemVO();
            BeanUtil.copyProperties(x, vo);
            return vo;
        }).collect(Collectors.toList());
        response.setContentType("application/vnd.ms-excel");
        response.setCharacterEncoding(Charsets.UTF_8.name());
        String fileName = URLEncoder.encode("在线问答", Charsets.UTF_8.name());
        response.setHeader("Content-disposition", "attachment;filename=" + fileName + ".xlsx");
        EasyExcel.write(response.getOutputStream(), CollectExcel.class).sheet("在线问答").doWrite(collect);
    }

    @ApiOperation(value = "获取问题类别和类型")
    @GetMapping(value = "/listAllOnlineProblemTypeByList")
    public SingleResponse<?> listAllOnlineProblemTypeByList(ListAllOnlineProblemTypeByListCmd cmd, UserAware userAware) throws Exception {
        return listAllOnlineProblemTypeByListCmdExe.execute(cmd);
    }

    @PostMapping("/delOnlineProblemType")
    @ApiOperation(value ="删除问题的类别和类型")
    public SingleResponse delOnlineProblemType(@RequestBody DelOnlineProblemTypeCmd cmd, UserAware userAware) {
        return delOnlineProblemTypeCmdExe.execute(cmd);
    }
}
