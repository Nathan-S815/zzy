package com.zzy.api.controller.reportresource;


import cn.hutool.core.util.NumberUtil;
import cn.hutool.core.util.StrUtil;
import com.github.pagehelper.PageInfo;
import com.zzy.api.service.reportresources.ResourceManageService;
import com.zzy.core.dto.R;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


@RestController
@RequestMapping("/resource")
@Api(tags="资源目录管理")
public class ResourceManageController {


    @Autowired
    private ResourceManageService resourceManageService;


    @ApiOperation(value="资源目录列表", notes = "一级列表以及二级列表菜单及数量")
    @GetMapping("/getTabList")
    public R getTabList(){
        return R.ok(resourceManageService.findResourceTab());
    }

    @ApiOperation(value="数据分类总数接口", notes = "二级列表菜单数量")
    @GetMapping("/getTableTypeCount")
    public R getTableTypeCount(){
        return R.ok(resourceManageService.getTableTypeCount());
    }


    @ApiOperation(value="所有资源表各个记录数字段数", notes = "可查询")
    @ApiImplicitParams({
            @ApiImplicitParam(name="tableName",value="表名", required=false,paramType = "query",  dataType="string"),
    })
    @PostMapping("/getAllRowsColumn")
    public R getAllRowsColumn(String tableName){
        Map<String,Object> m = new HashMap<>();
        m.put("tableName",tableName);
        Map<String,Object> mb = resourceManageService.findFeildNumWithTableNum(m);
        List<Map<String,Object>> r = resourceManageService.findResourceAllRowColumn(m);
        mb.put("listData",r);
        return R.ok(mb);
    }


    @ApiOperation(value="根据资源二级列表的表类别查询", notes = "根据类别查表")
    @ApiImplicitParams({
            @ApiImplicitParam(name="tableName",value="表名", required=false,paramType = "query",  dataType="string"),
            @ApiImplicitParam(name="tableType",value="表类别(eg:游客消费,游客画像,全域客流量监测...)",  required=false,paramType = "query",  dataType="string"),
    })
    @PostMapping("/getTypeRowsColumn")
    public R getTypeSubRowsColumn(String tableType, String tableName){
        if(StrUtil.isBlankOrUndefined(tableType)){
            return R.ok("参数不能为空");
        }
        Map<String,Object> m = new HashMap<>();
        m.put("tableName",tableName);
        if(!StrUtil.isBlankOrUndefined(tableType)){
            m.put("tableType",tableType);
        }
        Map<String,Object> mb = resourceManageService.findFeildNumWithTableNum(m);
        List<Map<String,Object>> r = resourceManageService.findResourceAllRowColumn(m);
        mb.put("listData",r);
        return R.ok(mb);
    }


    @ApiOperation(value="表结构详情", notes = "根据tableCode查询对应详情")
    @ApiImplicitParams({
            @ApiImplicitParam(name="tableCode",value="表英文名", required=true,paramType = "query",  dataType="string"),
    })
    @PostMapping("/getTableDetails")
    public R getTypeSubRowsColumn(String tableCode){
        if(StrUtil.isBlankOrUndefined(tableCode)){
            return R.ok("参数不能为空");
        }
        Map<String,Object> m = new HashMap<>();
        m.put("tableCode",tableCode);
        return R.ok(resourceManageService.findTableDetails(m));
    }

    @ApiOperation(value="表详细数据", notes = "根据tableCode查询对应数据")
    @ApiImplicitParams({
            @ApiImplicitParam(name="tableCode",value="表英文名", required=true,paramType = "query",  dataType="string"),
            @ApiImplicitParam(name="pageNo",value="页码", required=true,paramType = "query",  dataType="string"),
            @ApiImplicitParam(name="pageSize",value="条数", required=true,paramType = "query",  dataType="string"),
    })
    @PostMapping("/getTableRecords")
    public R getTableRecords(String tableCode, String pageNo, String pageSize){
        if(StrUtil.isBlankOrUndefined(tableCode) || StrUtil.isBlankOrUndefined(pageNo) || StrUtil.isBlankOrUndefined(pageSize)){
            return R.ok("参数不能为空");
        }
        if(!NumberUtil.isNumber(pageNo)){
            pageNo = "1";
        }
        if(!NumberUtil.isNumber(pageSize)){
            pageSize = "5";
        }
        Map<String,Object> m = new HashMap<>();
        m.put("tableCode",tableCode);
        if(tableCode.contains("_comment_info") || "place_info".equals(tableCode)){
            m.put("schemaName","crawler_base");
        }else{
            m.put("schemaName","putuo_base_data");
        }
        PageInfo<Map<String,Object>> l = resourceManageService.findTableRecords(m,Integer.parseInt(pageNo), Integer.parseInt(pageSize));
        return R.ok(l);
    }



    @ApiOperation(value="根据一级列表名获取所有列行数", notes = "一级列表名")
    @ApiImplicitParams({
            @ApiImplicitParam(name="tabName",value="一级列表名", required=false,paramType = "query",  dataType="string"),
    })
    @PostMapping("/getRowsColumnByFirstType")
    public R getRowsColumnByFirstType(String tabName){
        Map<String,Object> m = new HashMap<>();
        m.put("tabType",tabName);
        Map<String,Object> mb = resourceManageService.findFeildNumWithTableNum(m);
        return R.ok(mb);
    }









}
