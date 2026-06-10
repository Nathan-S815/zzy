package com.zzy.api.controller.eventdepart;


import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.http.HttpException;
import cn.hutool.http.HttpUtil;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.util.TypeUtils;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.github.pagehelper.PageInfo;
import com.zzy.api.dto.DepartmentTaskListParam;
import com.zzy.api.dto.EventAddParam;
import com.zzy.api.dto.EventListParam;
import com.zzy.api.service.eventdepart.*;
import com.zzy.core.dto.R;
import com.zzy.db.entity.eventdepart.*;
import com.zzy.security.annotations.RequiredPermission;
import com.zzy.security.common.JwtUtil;
import com.zzy.security.dto.CustomUser;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.awt.*;
import java.time.LocalDateTime;
import java.util.*;
import java.util.List;
import java.util.stream.Collectors;

/**
 * privilege
 */
@CrossOrigin
@Api(tags = "事件相关接口")
@RestController
@RequestMapping("/event")
public class EventController {


    @Autowired
    private IEventInfoService eventInfoService;


    @Autowired
    private IEventDepartMemberService eventDepartMemberService;

    @Autowired
    private IDepartmentMemberService departmentMemberService;


    @Autowired
    private IDepartmentInfoService departmentInfoService;

    @Autowired
    private IMsgNotifyMemberTaskService msgNotifyMemberTaskService;


    @Autowired
    private IComplainEventInfoService iComplainEventInfoService;

    @Autowired
    private IComplainInfoService iComplainInfoService;

    @Autowired
    private IEventInfoService iEventInfoService;

    @Autowired
    private IEventFeedbackService eventFeedbackService;


    @Autowired
    private IEventFeedbackFileService eventFeedbackFileService;


    @Autowired
    private IEventMediaInfoService eventMediaInfoService;


    @Autowired
    private IEventPunishService eventPunishService;



    private static final String departmentinfo_column_login_name = "leader_login_name";
    private static final String depart_member_column_login_name = "login_name";


    @GetMapping("getPunishByMemberEventId")
    @ApiOperation(value = "根据部员Id事件Id获取对应的惩罚内容")
    public R getPunishByMemberEventId(Integer memberId,Integer eventId){
        if(memberId==null || eventId==null) return R.nullValueError();
        DepartmentInfo di = departmentInfoService.lambdaQuery().eq(DepartmentInfo::getLeader,memberId).one();
        if(di==null){
            return R.error("非负责人无法查看");
        }
        EventPunish ep = eventPunishService.lambdaQuery()
                .eq(EventPunish::getEventId,eventId)
                .eq(EventPunish::getExeuteDepartId,di.getLeader()).one();
        JSONObject jo = new JSONObject();
        jo.put("punishInfo", ep);
        return R.ok(jo);
    }


    @GetMapping("getDepartTitleListByDepartId")
    @ApiOperation(value = "根据部门Id获取该部门的岗位列表")
    public R getDepartTitleListByDepartId(Integer departId){
        if(departId==null){
            return R.nullValueError();
        }
        List<DepartmentMember> list = departmentMemberService.lambdaQuery().eq(DepartmentMember::getDepartmentId,departId)
                .groupBy(DepartmentMember::getTitle).list();
        Set<String> sets = list.stream().map(o->o.getTitle()).collect(Collectors.toSet());
        return R.ok(sets);
    }


    @GetMapping("deleteEvent")
    @ApiOperation(value = "根据事件Id删除事件(只有管理员可以)")
    public R deleteEvent(Integer eventId,HttpServletRequest request){
        if(eventId==null){
            return R.nullValueError();
        }
        CustomUser cu = JwtUtil.getFromJwt(request);
        if((cu.getRoleCode()!=null && cu.getRoleCode().contains("ADMIN")) || cu.getAuths().contains("ROOT")){
            eventInfoService.removeById(eventId);
            eventDepartMemberService.remove(new QueryWrapper<EventDepartMember>()
                    .eq("event_id", eventId));
            eventMediaInfoService.remove(
                    new QueryWrapper<EventMediaInfo>().eq("event_id", eventId));
            msgNotifyMemberTaskService.remove(
                    new QueryWrapper<MsgNotifyMemberTask>()
                            .eq("event_id", eventId));
            List<EventFeedback> liss = eventFeedbackService.lambdaQuery()
                    .eq(EventFeedback::getEventId,eventId).list();
            eventFeedbackService.remove(
                    new QueryWrapper<EventFeedback>()
                            .eq("event_id", eventId));
            if(liss!=null && liss.size()>0){
                eventFeedbackFileService.remove(
                        new QueryWrapper<EventFeedbackFile>()
                                .in("feed_back_id", liss.stream().map(o->o.getId()).collect(Collectors.toList())));
            }
            eventPunishService.remove(
                    new QueryWrapper<EventPunish>()
                            .eq("event_id", eventId));
            return R.success();
        }else {
            return R.error("权限不足");
        }
    }


    @GetMapping("getDepartInfoListByEventId")
    @ApiOperation(value = "根据事件Id获取该事件指定的部门列表")
    public R getDepartInfoListByEventId(Integer eventId){
        if(eventId==null){
            return R.nullValueError();
        }
        List<EventDepartMember> list = eventDepartMemberService.lambdaQuery().eq(EventDepartMember::getEventId,eventId).list();
        if(list!=null && list.size() > 0){
            Set<Integer> sets = list.stream().map(o->o.getDepartMemberId()).collect(Collectors.toSet());
            List<DepartmentMember> dm = departmentMemberService.lambdaQuery().in(DepartmentMember::getId,sets).list();
            Set<Integer> ids = dm.stream().map(o->o.getDepartmentId()).collect(Collectors.toSet());
            List<Map<String,Object>> dis = departmentInfoService.listMaps(new QueryWrapper<DepartmentInfo>()
                    .in("id", ids).select("id,name,leader")
            );
            return R.ok(dis);
        }
        return R.ok();
    }


    @ApiOperation(value = "事件备注说明")
    @PostMapping("/eventRemarkEdit")
    public R eventRemarkEdit(Integer eventId,String message){
        if(eventId==null || StrUtil.isBlankOrUndefined(message)){
            return R.nullValueError();
        }
        EventInfo ei = new EventInfo();
        ei.setId(eventId).setRemark(message);
        return eventInfoService.updateById(ei)?R.success():R.fail();
    }



    @ApiOperation(value = "创建事件任务接口",notes = "相同事件名不能有相同的[事件类型&&事件等级]")
    @PostMapping("/createEvent")
    public R createEvent(HttpServletRequest request, EventAddParam info){
        CustomUser cu = JwtUtil.getFromJwt(request);
        if(StrUtil.isBlankOrUndefined(info.getEventName())
                || info.getEventLevel()==null
                || info.getEventTypeId()==null
                || info.getReportStatus()==null
        ){
            return R.nullValueError();
        }
//        EventInfo ei = eventInfoService.getOne(new QueryWrapper<EventInfo>()
//                .eq("event_name", info.getEventName()));
//        if(ei!=null && ei.getEventLevel().equals(info.getEventLevel())
//                && ei.getEventTypeId().equals(info.getEventTypeId())
//                && ei.getReportStatus().equals(info.getReportStatus())
//        ){
//            return R.ok("已存在同名的类型等级事件，请勿重复创建");
//        }
        DepartmentMember dm = null;
        int memberId = -1;
        if(info.getReportStatus().equals(2)){ //二级上报
            String departIds = info.getDepartIds().replace("[","").replace("]","");
            info.setDepartIds(departIds);
            if(info.getIsSingle().equals(1)){
                if(departIds.split(",").length>1){
                    return R.error("单部门事件只能指定一个部门");
                }
            }
            if(info.getIsSingle().equals(0)){
                if(departIds.split(",").length<=1){
                    return R.error("多部门事件需提前指定相关负责部门");
                }
            }
            DepartmentInfo di = departmentInfoService.lambdaQuery().eq(DepartmentInfo::getLeaderUserId,cu.getUserId()).one();
            if(di==null){
                if((cu.getRoleCode()!=null && !cu.getRoleCode().contains("ADMIN")) || !cu.getAuths().contains("ROOT")){
                    return R.error("权限不足");
                }else{
                    info.setEventPublisher(cu.getUsername());
                }
            }else{
                dm = departmentMemberService.lambdaQuery().eq(DepartmentMember::getId,di.getLeader()).one();
                if(dm==null){
                    return R.error("部门负责人账号不存在");
                }
                memberId = dm.getId();
                info.setEventPublisher(dm.getName());
            }
        }else{ //一级上报
            dm = departmentMemberService.lambdaQuery().eq(DepartmentMember::getUserId,cu.getUserId()).one();
            if(dm==null){
                return R.error("部门成员账号不存在或登录账号非成员账号");
            }
            memberId = dm.getId();
        }
        info.setMemeberId(memberId);
        return eventInfoService.addEventByParam(info)?R.success():R.fail();
    }

    @ApiOperation(value = "修改事件任务接口",notes = "相同事件名不能有相同的[事件类型&&事件等级]")
    @PostMapping("/editEvent")
    public R editEvent(Integer eventId, EventAddParam info,HttpServletRequest request){
        if(eventId==null) return R.nullValueError();
        info.setEventTmpId(eventId);
        if(info.isIllegal()){
            return R.nullValueError();
        }
        CustomUser cu = JwtUtil.getFromJwt(request);
        if(info.getReportStatus().equals(2)){
            DepartmentInfo di = departmentInfoService.lambdaQuery().eq(DepartmentInfo::getLeaderUserId,cu.getUserId()).one();
            if(di==null){
                if(!cu.getAuths().contains("ROOT") && cu.getRoleCode()!=null && !cu.getRoleCode().contains("ADMIN")){
                    return R.error("无权限");
                }else{
                    info.setEventPublisher(cu.getUsername());
                }
            }else{
                DepartmentMember dm = departmentMemberService.lambdaQuery().eq(DepartmentMember::getId,di.getLeader()).one();
                info.setEventPublisher(dm.getName());
                info.setMemeberId(di.getLeader());
            }
            String departIds = info.getDepartIds().replace("[","").replace("]","");
            info.setDepartIds(departIds);
            if(info.getIsSingle().equals(1)){
                if(departIds.split(",").length>1){
                    return R.error("单部门事件只能指定一个部门");
                }
            }
            if(info.getIsSingle().equals(0)){
                if(departIds.split(",").length<=1){
                    return R.error("多部门事件需提前指定相关负责部门");
                }
            }
        }
//        EventInfo ei = eventInfoService.getOne(new QueryWrapper<EventInfo>().eq("event_name", info.getEventName()));
//        if(ei!=null && ei.getEventLevel().equals(info.getEventLevel())
//                && ei.getEventTypeId().equals(info.getEventTypeId())
//                && ei.getReportStatus().equals(info.getReportStatus())
//                && !eventId.equals(ei.getId())
//        ){
//            return R.ok("已存在同名的类型等级事件，请勿重复创建");
//        }
        if(info.getReportStatus().equals(1)){
            DepartmentMember dm = departmentMemberService.getOne(new QueryWrapper<DepartmentMember>().eq("user_id", cu.getUserId()));
            if(dm==null){
                return R.error("用户无权限");
            }
            int memberId = dm.getId();
            info.setMemeberId(memberId);
        }
        return eventInfoService.editEventByParam(info)?R.success():R.fail();
    }


    @ApiOperation(value = "一级任务转二级上报",notes = "负责人将一级任务上报为二级任务")
    @GetMapping("/reportEventFromFirstLevel")
    public R reportEventFromFirstLevel(Integer eventId){
        if(eventId==null) return R.nullValueError();
        EventInfo ei = new EventInfo();
        ei.setId(eventId).setUpdateTime(LocalDateTime.now()).setReportStatus(2);
//        EventInfo tmp = eventInfoService.getById(eventId);
//        EventInfo newEi = eventInfoService.lambdaQuery()
//                .eq(EventInfo::getEventTypeId,tmp.getEventTypeId())
//                .eq(EventInfo::getEventLevel,tmp.getEventLevel())
//                .eq(EventInfo::getEventName,tmp.getEventName())
//                .eq(EventInfo::getReportStatus,2).one();
//        if(newEi!=null){
//            ei.setEventName(tmp.getEventName()+DateUtil.format(new Date(), "yyyyMMdd"))
//        }
        return ei.updateById()?R.success():R.fail();
    }

    @ApiOperation(value = "再处理任务",notes = "可按事件、时间、类型、部门等进行执法事件的统计及查询(eventStatus不传)。")
    @GetMapping("/getReDoEventList")
    public R getReDoEventList(Integer pageNo,Integer pageSize, EventListParam param){
        if(pageNo==null) pageNo=1;
        if(pageSize==null) pageSize=10;
        String startDate = param.getStartTime();
        String endDate = param.getEndTime();
        if(StrUtil.isNotBlank(startDate) && StrUtil.isNotBlank(endDate)){
            if(TypeUtils.castToDate(startDate).after(TypeUtils.castToDate(endDate))){
                return R.ok("日期参数有误");
            }
        }
        return R.ok(eventInfoService.getReDoEventList(pageNo,pageSize, param));
    }


    @ApiOperation(value = "执法统计(获取事件任务列表接口)",notes = "可按事件、时间、类型、部门等进行执法事件的统计及查询。")
    @GetMapping("/getEventList")
    public R getEventList(Integer pageNo,Integer pageSize, EventListParam param){
        if(pageNo==null) pageNo=1;
        if(pageSize==null) pageSize=10;
        String startDate = param.getStartTime();
        String endDate = param.getEndTime();
        if(StrUtil.isNotBlank(startDate) && StrUtil.isNotBlank(endDate)){
            if(TypeUtils.castToDate(startDate).after(TypeUtils.castToDate(endDate))){
                return R.ok("日期参数有误");
            }
        }
        return R.ok(eventInfoService.getEventList(pageNo,pageSize, param));
    }

    @ApiOperation(value = "根据事件和部门Id获取该部门被分配该事件的部员")
    @GetMapping("/getMemberByEventWithDepartId")
    public R getMemberByEventWithDepartId(Integer eventId, String departIds){
        if(eventId==null || StrUtil.isBlankOrUndefined(departIds)) return R.nullValueError();
        List<Map<String,Object>> list = msgNotifyMemberTaskService.getMemberByEventWithDepartId(eventId,departIds);
        return R.ok(list);
    }


    @ApiOperation(value = "获取事件任务详情",notes = "查看某个任务详情")
    @GetMapping("/getEventByEventId")
    public R getEventByEventId(Integer eventId){
        if(eventId==null) return R.nullValueError();
        Map m = eventInfoService.getEventDetailByEventId(eventId);
        if(m==null){
            return R.ok("事件不存在");
        }
        List<Map<String,Object>> l =  eventPunishService.getPunishDepartInfoByEventId(eventId);
        m.put("punish", l);
        return R.ok(m);
    }


    @ApiOperation(value = "获取被分配事件的部员列表")
    @GetMapping("/getEventMemberIdsByEventId")
    public R getEventMemberIdsByEventId(Integer eventId){
        if(eventId==null) return R.nullValueError();
        List<MsgNotifyMemberTask> list = msgNotifyMemberTaskService.lambdaQuery().eq(MsgNotifyMemberTask::getEventId,eventId).list();
//        List<EventDepartMember> m = eventDepartMemberService.lambdaQuery().eq(EventDepartMember::getEventId,eventId).list();
        if(list!=null && list.size()>0){
            Set<Integer> ids = list.stream().map(o->o.getDepartmemberId()).collect(Collectors.toSet());
            if(ids!=null && ids.size()>0){
                List<Map<String,Object>> lists = departmentMemberService.listMaps(new QueryWrapper<DepartmentMember>().select("id","name").in("id", ids));
                return R.ok(lists);
            }
        }
        return R.ok();
    }




    @ApiOperation(value = "任务认领",notes = "pc登录用户在任务列表认领任务")
    @GetMapping("/signForTask")
    public R claimTask(Integer eventId, Integer userId, HttpServletRequest request){
        if(eventId==null || userId==null) return R.nullValueError();
        DepartmentInfo di = departmentInfoService.lambdaQuery().eq(DepartmentInfo::getLeaderUserId,userId).one();
        if(di==null || di.getLeader()==null){
            return R.error("用户无权限");
        }
        boolean isExist = false;
        List<EventDepartMember> ed = eventDepartMemberService.list(new QueryWrapper<EventDepartMember>().eq("event_id", eventId));
        if(ed!=null && ed.size() > 0){//发布事件的时候指定了相关部门
            ed = ed.stream().filter(o->o.getDepartMemberId().equals(di.getLeader())).collect(Collectors.toList());
            if(ed!=null && ed.size()==1){//认领人在被指定部门列表内
                isExist = true;
                if(ed.get(0).getIsAssigned().equals(1)){
                    return R.error("您已于"+ DateUtil.format(ed.get(0).getAssignedTime(),"yyyy-MM-dd HH:mm:ss")+"认领过该任务");
                }
            }else{
                return R.error("非任务指定部门无法认领");
            }
        }
        return eventInfoService.claimTask(eventId,di.getLeader(),isExist)?R.success():R.fail();
    }



    @ApiOperation(value = "任务指派",notes = "pc登录用户在任务列表指派无部门认领的事件(任务指定后会立刻短信通知对应的部门负责人)")
    @ApiImplicitParams({
            @ApiImplicitParam(name="eventId",value="事件ID", required=true,paramType = "query",  dataType="string"),
            @ApiImplicitParam(name="departId",value="部门ID", required=true,paramType = "query",  dataType="string"),
    })
    @RequiredPermission(hasRoleCode = "ADMIN")
    @GetMapping("/designateEvent")
    public R designateEvent(Integer eventId, Integer departId, HttpServletRequest request){
        if(eventId==null || departId==null) return R.nullValueError();
        if(eventDepartMemberService.getByDepartIdWithEventId(eventId,departId)!=null){
            return R.error("该部门已被指定过此任务,勿重复操作");
        }
        return eventInfoService.designateEvent(eventId,departId)?R.success():R.fail();
    }



    /**
     * 获取本部门一级上报任务列表
     * @return
     */
    @ApiOperation(value = "本部门一级任务分页列表",notes = "该列表只能由登录用户是部门成员才能看到")
    @GetMapping("/getDepartmentTaskList")
    public R getDepartmentTaskList(Integer pageNo, Integer pageSize, DepartmentTaskListParam para){
        if(pageNo==null) pageNo=1;
        if(pageSize==null) pageSize=10;
        Map<String,Object> m = new HashMap<>();
        if(para.getDepartId()!=null){
            m.put("departId",para.getDepartId());
        }
        if(para.getEventStatus()!=null){
            m.put("eventStatus", para.getEventStatus());
        }
        if(!StrUtil.isBlankOrUndefined(para.getTitle())){
            m.put("title", para.getTitle());
        }
        if(!StrUtil.isBlankOrUndefined(para.getStartTime()) && !StrUtil.isBlankOrUndefined(para.getEndTime())){
            if(TypeUtils.castToDate(para.getEndTime()).before(TypeUtils.castToDate(para.getStartTime()))){
                return R.error("结束时间不得小于开始时间");
            }
        }
        if(!StrUtil.isBlankOrUndefined(para.getStartTime())){
            m.put("startTime", TypeUtils.castToDate(para.getStartTime()));
        }
        if(!StrUtil.isBlankOrUndefined(para.getEndTime())){
            m.put("endTime", TypeUtils.castToDate(para.getEndTime()));
        }
        PageInfo<Map<String,Object>> pi = eventInfoService.getDepartmentTaskList(m,pageNo,pageSize);
        return R.ok(pi);
    }



//    @ApiOperation(value = "获取当前部门被指派的任务列表",notes = "登录用户看本部门被指定的任务列表")
//    @ApiImplicitParams({
//            @ApiImplicitParam(name="apiType",value="接口来源(pc,只支持pc登录用户查看)", required=true,paramType = "query",  dataType="string"),
//            @ApiImplicitParam(name="eventStatus",value="事件状态(1:待分配, 2:已分配待确认,3:已确认处理中,4:失效,5:结束,6:需强制指定)", required=false,paramType = "query",  dataType="string"),
//    })
//    @GetMapping("/getAssignedTaskList")
//    public R getAssignedTaskList(Integer pageNo,Integer pageSize, String apiType,Integer eventStatus,Integer departId,HttpServletRequest request){
//        if(pageNo==null) pageNo=1;
//        if(pageSize==null) pageSize=10;
//        if(StrUtil.isBlankOrUndefined(apiType)){
//            return R.nullValueError();
//        }
//        CustomUser cu = JwtUtil.getFromJwt(request);
//        Integer departIds = departId;
//        if("pc".equals(apiType.toLowerCase())){
//            departIds = departmentInfoService.getOne(new QueryWrapper<DepartmentInfo>()
//                    .eq(departmentinfo_column_login_name, username)).getId();
//        }else{
//            DepartmentMember dm = departmentMemberService.getOne(new QueryWrapper<DepartmentMember>()
//                    .eq(depart_member_column_login_name, username));
//                    if(dm!=null){
//                        departIds = dm.getDepartmentId();
//                    }
//            return R.error("本接口只能pc端查看");
//        }
//        Map<String,Object> m = new HashMap<>();
//        m.put("departId", departIds);
//        m.put("eventStatus", eventStatus);
//        return R.ok(eventInfoService.getAssignedTaskList(pageNo,pageSize,m));
//    }



    @GetMapping("/getMemberTask")
    @ApiOperation(value = "部门成员获取该成员被部门负责人短信通知指定的任务列表",notes = "h5仅限")
    @ApiImplicitParams({
            @ApiImplicitParam(name="eventStatus",value="事件状态(3:已确认处理中,4:失效,5:结束)", required=false,paramType = "query",  dataType="string"),
    })
    public R getMemberTask(Integer pageNo,Integer pageSize,Integer eventStatus,HttpServletRequest request){
        if(pageNo==null) pageNo=1;
        if(pageSize==null) pageSize = 10;
        CustomUser cu = JwtUtil.getFromJwt(request);
        Map<String,Object> m = new HashMap<>();
        System.out.println(cu.getUserId());
        m.put("userId", cu.getUserId());
        if(eventStatus!=null){
            m.put("eventStatus", eventStatus);
        }
        PageInfo<Map<String,Object>> dm = msgNotifyMemberTaskService.getEventListByMsgTaskMember(pageNo,pageSize,m);
        return R.ok(dm);
    }



    //    @RequiredPermission(hasRoleCode = "ADMIN")
    @PostMapping("/closeEvent")
    @ApiOperation(value = "管理员关闭事件设置")
    @ApiImplicitParams({
            @ApiImplicitParam(name="eventId",value="事件Id", required=true,paramType = "query",  dataType="string"),
            @ApiImplicitParam(name="closeRemark",value="完结与否的说明", required=true,paramType = "query",  dataType="string"),
            @ApiImplicitParam(name="isClose",value="是否关闭(1:是,0:否)", required=true,paramType = "query",  dataType="string"),
    })
    public R closeEvent(Integer eventId,String closeRemark,Integer isClose){
        if(eventId==null || StrUtil.isBlankOrUndefined(closeRemark) || isClose==null){
            return R.nullValueError();
        }
        EventInfo ei = new EventInfo();
        ei.setId(eventId).setUpdateTime(LocalDateTime.now());
        if(isClose==1){
            ei.setEventStatus(5);
        }
        ei.setCloseRemark(closeRemark);
        boolean flag = ei.updateById();
        try {
            List<ComplainEventInfo> complainEventInfos = iComplainEventInfoService.list(new QueryWrapper<ComplainEventInfo>().eq("event_id", eventId));
            if (complainEventInfos.size()>0){
                int id =0;
                String handleRemarks = "";
                for (ComplainEventInfo complainEventInfo : complainEventInfos) {
                    ComplainInfo complainInfo = iComplainInfoService.getById(complainEventInfo.getComplainId());
                    id = complainInfo.getComplainId();
                    EventInfo eventInfo = iEventInfoService.getById(complainEventInfo.getEventId());
                    handleRemarks = eventInfo.getCloseRemark();
                    String url = "https://jzgapi.1lianyou.com/api/ComplaintProposal/ProcessingResults?id="+id+"&handleRemarks="+handleRemarks;
                    HttpUtil.createPost(url).timeout(1000 * 60).execute().body();
                }
            }
        } catch (HttpException e) {
            e.printStackTrace();
        }
        return flag?R.success():R.fail();
    }



    @GetMapping("/getschedule")
    @ApiOperation(value = "获取事件处理进度")
    @ApiImplicitParams({
            @ApiImplicitParam(name="eventId",value="事件Id", required=true,paramType = "query",  dataType="Integer"),
    })
    public R getschedule(Integer eventId){
        Map<String, Object> getschedule = eventInfoService.getschedule(eventId);
        return R.ok(getschedule);
    }



    @GetMapping("setIsNeedInter")
    @ApiOperation(value = "是否需要其他部门介入")
    @ApiImplicitParams({
            @ApiImplicitParam(name="eventId",value="事件Id", required=true,paramType = "query",  dataType="Integer"),
            @ApiImplicitParam(name="isNeed",value="是否介入(1:是,0:否)", required=true,paramType = "query",  dataType="Integer"),
    })
    public R setIsNeedInter(Integer eventId, Integer isNeed){
        if(eventId==null || isNeed==null){
            return R.nullValueError();
        }
        boolean b = eventInfoService.lambdaUpdate()
                .set(EventInfo::getIsNeedIntervene,isNeed==1?1:0)
                .eq(EventInfo::getId,eventId).update();
        return b?R.success():R.fail();
    }



}
