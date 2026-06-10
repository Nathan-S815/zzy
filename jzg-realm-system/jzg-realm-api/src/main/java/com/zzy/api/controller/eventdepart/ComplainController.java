package com.zzy.api.controller.eventdepart;

import cn.hutool.core.date.DateTime;
import cn.hutool.core.date.DateUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.github.pagehelper.PageInfo;
import com.itextpdf.text.pdf.StringUtils;
import com.zzy.api.annotations.AddLog;
import com.zzy.api.annotations.DeleteLog;
import com.zzy.api.annotations.SelectLog;
import com.zzy.api.annotations.UpdateLog;
import com.zzy.api.dto.ComplainInfoDTO;
import com.zzy.api.dto.EventAddParam;
import com.zzy.api.service.eventdepart.IComplainEventInfoService;
import com.zzy.api.service.eventdepart.IComplainInfoService;
import com.zzy.api.service.eventdepart.IEventInfoService;
import com.zzy.core.dto.R;
import com.zzy.db.entity.eventdepart.ComplainEventInfo;
import com.zzy.db.entity.eventdepart.ComplainInfo;
import com.zzy.db.entity.eventdepart.EventInfo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import java.util.Map;

@CrossOrigin
@Api(tags = "小程序投诉相关接口")
@RestController
@RequestMapping("/complain/public")
@Slf4j
public class ComplainController {

    @Autowired
    private IComplainInfoService iComplainInfoService;

    @Autowired
    private IEventInfoService eventInfoService;
    @Autowired
    IComplainEventInfoService iComplainEventInfoService;
    @SelectLog(operContent = "获取推送投诉列表")
    @GetMapping("/listComplainInfo")
    @ApiOperation(value = "获取推送投诉列表")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "pageSize", value = "分页条数", required = true, paramType = "query", dataType = "string"),
            @ApiImplicitParam(name = "pageNo", value = "页码", required = true, paramType = "query", dataType = "string"),
            @ApiImplicitParam(name = "creatTime", value = "今天数据时间（eg:2020-07-22 14:20:18）", required = false, paramType = "query", dataType = "string"),
            @ApiImplicitParam(name = "inTime", value = "24小时数据时间（eg:2020-07-22 14:20:18）", required = false, paramType = "query", dataType = "string"),
            @ApiImplicitParam(name = "outTime", value = "24小时外数据时间（eg:2020-07-22 14:20:18）", required = false, paramType = "query", dataType = "string"),
            @ApiImplicitParam(name = "sevenDayTime", value = "7天内数据时间（eg:2020-07-22 14:20:18）", required = false, paramType = "query", dataType = "string"),
            @ApiImplicitParam(name = "thirtyDayTime", value = "30天内数据时间（eg:2020-07-22 14:20:18）", required = false, paramType = "query", dataType = "string"),
            @ApiImplicitParam(name = "state", value = "投诉状态（0-未经过二次上报为事件，1-已经过二次上报为事件，2-转换为事件已被处理结束）", required = false, paramType = "query", dataType = "Integer")
    })
    public R listComplainInfo(Integer pageNo, Integer pageSize, Integer state,String creatTime,String inTime,String outTime,String sevenDayTime,String thirtyDayTime) {
        PageInfo<Map<String, Object>> mapPageInfo=null;
        String startTime=null;
        String endTime=null;
        if(creatTime!=null&&creatTime!=""){
            mapPageInfo = iComplainInfoService.listComplainInfo(pageNo, pageSize, state,creatTime,startTime,endTime);
        }else if(inTime!=null&&inTime!=""){
            Date date = DateUtil.parse(inTime);
            DateTime newDate3 = DateUtil.offsetHour(date, -24);
            startTime=newDate3.toString();
            endTime=inTime;
            mapPageInfo = iComplainInfoService.listComplainInfo(pageNo, pageSize, state,creatTime,startTime,endTime);
        }else if(outTime!=null&&outTime!=""){
            Date date = DateUtil.parse(outTime);
            DateTime newDate3 = DateUtil.offsetHour(date, -24);
            startTime="2017-03-01 22:33:23";
            endTime=newDate3.toString();
            mapPageInfo = iComplainInfoService.listComplainInfo(pageNo, pageSize, state,creatTime,startTime,endTime);
        }else if(sevenDayTime!=null&&sevenDayTime!=""){
            Date date = DateUtil.parse(sevenDayTime);
            DateTime newDate3 = DateUtil.offsetHour(date, -168);
            startTime=newDate3.toString();
            endTime=sevenDayTime;
            mapPageInfo = iComplainInfoService.listComplainInfo(pageNo, pageSize, state,creatTime,startTime,endTime);
        }else if(thirtyDayTime!=null&&thirtyDayTime!=""){
            Date date = DateUtil.parse(thirtyDayTime);
            DateTime newDate3 = DateUtil.offsetHour(date, -720);
            startTime=newDate3.toString();
            endTime=thirtyDayTime;
            mapPageInfo = iComplainInfoService.listComplainInfo(pageNo, pageSize, state,creatTime,startTime,endTime);
        }else {
            mapPageInfo = iComplainInfoService.listComplainInfo(pageNo, pageSize, state,creatTime,startTime,endTime);
        }

        if (mapPageInfo.getSize() != 0) {
            return R.ok(mapPageInfo);
        } else {
            return R.ok("暂无数据");
        }
    }
    @SelectLog(operContent = "获取投诉数据")
    @GetMapping("/getComplainInfoById")
    @ApiOperation(value = "根据id获取投诉数据")
    public R getComplainInfoById(Integer id) {
        ComplainInfo byId = iComplainInfoService.getById(id);
        if (byId != null) {
            return R.ok(byId);
        }
        return R.ok("暂无数据");
    }
    @AddLog(operContent = "新增投诉数据")
    @PostMapping("/saveComplainInfo")
    @ApiOperation(value = "新增投诉数据")
    public R saveComplainInfo(@RequestBody ComplainInfoDTO complainInfoDTO) {
        List<ComplainInfo> complainInfos = iComplainInfoService.list(new QueryWrapper<ComplainInfo>().eq("complain_id", complainInfoDTO.getComplainId()));
        if (complainInfos.size() > 0) {
            return R.ok("complain_id重复");
        }
        ComplainInfo complainInfo = new ComplainInfo();
        BeanUtils.copyProperties(complainInfoDTO, complainInfo);
        complainInfo.setCreateTime(LocalDateTime.now());
        boolean save = iComplainInfoService.save(complainInfo);
        if (save) {
            return R.ok("新增成功");
        }
        return R.ok("新增失败");
    }
    @UpdateLog(operContent = "修改投诉数据")
    @PostMapping("/updateComplainInfo")
    @ApiOperation(value = "修改投诉数据")
    public R updateComplainInfo(Integer id, ComplainInfoDTO complainInfoDTO) {
        ComplainInfo complainInfo = new ComplainInfo();
        BeanUtils.copyProperties(complainInfoDTO, complainInfo);
        complainInfo.setUpdateTime(LocalDateTime.now());
        complainInfo.setId(id);
        boolean save = iComplainInfoService.updateById(complainInfo);
        if (save) {
            return R.ok("修改成功");
        }

        return R.ok("修改失败");
    }
    @DeleteLog(operContent = "根据id删除投诉数据")
    @PostMapping("/deleteComplainInfo")
    @ApiOperation(value = "根据id删除投诉数据")
    public R deleteComplainInfo(Integer id) {
        boolean remove = iComplainInfoService.removeById(id);
        if (remove) {
            return R.ok("删除成功");
        }
        return R.ok("删除失败");
    }
    @UpdateLog(operContent = "修改投诉数据状态")
    @PostMapping("/updateComplainInfoState")
    @ApiOperation(value = "修改投诉数据状态")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "投诉数据id", required = true, paramType = "query", dataType = "Integer"),
            @ApiImplicitParam(name = "state", value = "投诉状态（0-未经过二次上报为事件，1-已经过二次上报为事件，2-转换为事件已被处理结束）", required = true, paramType = "query", dataType = "Integer")
    })
    public R updateComplainInfoState(Integer id, Integer state) {
        int complainInfoState = iComplainInfoService.updateComplainInfoState(id, state);
        if (complainInfoState > 0) {
            return R.ok("修改成功");
        }
        return R.ok("修改失败");
    }

    @PostMapping("/saveComplainToEvent")
    @ApiOperation(value = "将投诉事件放到事件任务池中")
    public R saveComplainToEvent(Integer id, String departIds, String replenish) {
        if (replenish != null && "".equals(replenish)) {
            iComplainInfoService.insertReplenish(id,replenish);
        }
        ComplainInfo complainInfo = iComplainInfoService.getById(id);
        complainInfo.setState(1);
        iComplainInfoService.updateById(complainInfo);
        EventAddParam eventAddParam = new EventAddParam();
        if (departIds.contains(",")){
            eventAddParam.setIsSingle(0);
        }else {
            eventAddParam.setIsSingle(1);
        }
        eventAddParam.setEventLevel(1);
        eventAddParam.setDepartIds(departIds);
        if(complainInfo.getComplainName()!=null) {
            eventAddParam.setEventName(complainInfo.getComplainName());
        }else {
            eventAddParam.setEventName("投诉事件");
        }
        eventAddParam.setEventContent(complainInfo.getComplainContent());
        eventAddParam.setReportStatus(2);
        eventAddParam.setEventTypeId(4);
        if(complainInfo.getComplainPeople()!=null) {
            eventAddParam.setEventPublisher(complainInfo.getComplainPeople());
        }else {
            eventAddParam.setEventPublisher("root");
        }
        EventInfo eventInfo = eventInfoService.addEventByParamCatchId(eventAddParam, null, null, null, null, null);
        ComplainEventInfo complainEventInfo = new ComplainEventInfo();
        complainEventInfo.setComplainId(id);
        complainEventInfo.setEventId(eventInfo.getId());
        iComplainEventInfoService.save(complainEventInfo);
        return R.ok();

    }



}
