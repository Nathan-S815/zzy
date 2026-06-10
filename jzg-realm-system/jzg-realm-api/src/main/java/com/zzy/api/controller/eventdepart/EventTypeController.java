package com.zzy.api.controller.eventdepart;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.zzy.api.dto.EventFeedbackUpdatePara;
import com.zzy.api.service.eventdepart.*;
import com.zzy.core.dto.R;
import com.zzy.db.entity.eventdepart.*;
import com.zzy.security.annotations.RequiredPermission;
import com.zzy.security.common.JwtUtil;
import com.zzy.security.dto.CustomUser;
import com.zzy.security.lib.entity.UserInfo;
import com.zzy.security.service.UsersService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@CrossOrigin
@Api(tags = "事件类型与反馈接口")
@RestController
@RequestMapping("/eventType")
public class EventTypeController {


    @Autowired
    private IEventTypeInfoService iEventTypeInfoService;

    @Autowired
    private IEventFeedbackService iEventFeedbackService;

    @Autowired
    private IDepartmentInfoService departmentInfoService;

    @Autowired
    private IDepartmentMemberService departmentMemberService;

    @Autowired
    private IEventPunishService eventPunishService;

    @Autowired
    private UsersService usersService;

    @Autowired
    private IEventInfoService eventInfoService;

    @Autowired
    private IEventFeedbackFileService eventFeedbackFileService;


    @GetMapping("/getCheckedFeedbackDetailByEventId")
    @ApiOperation(value = "根据事件Id获取已通过的反馈详情")
    public R getCheckedFeedbackDetailByEventId(Integer evenId){
        if(evenId==null){
            return R.nullValueError();
        }
        Map<String,Object> m = iEventFeedbackService.findCheckedFeedbackDetailByEventId(evenId);
        return R.ok(m);
    }

    @GetMapping("/getFeedbackDetailByMemberIdWithEventId")
    @ApiOperation(value = "根据部员Id和事件Id获取对应的反馈详情")
    public R getFeedbackDetailByMemberId(Integer evenId,Integer memberId){
        if(evenId==null || memberId==null){
            return R.nullValueError();
        }
        Map<String,Object> m = iEventFeedbackService.findDetailsByIds(evenId,memberId);
        return R.ok(m);
    }

    @GetMapping("/listEventTypeInfo")
    @ApiOperation(value = "获取事件类型列表")
    public R listEventTypeInfo(){
        List<EventTypeInfo> list = iEventTypeInfoService.list();
        if(list!=null){
            return R.ok(list);
        }
        return R.ok("暂无数据");
    }

    @GetMapping("/getEventTypeById")
    @ApiOperation(value = "根据id获取事件类型")
    public R getEventTypeById(Integer id){
        EventTypeInfo byId = iEventTypeInfoService.getById(id);
        if(byId!=null){
            return R.ok(byId);
        }
        return R.ok("暂无数据");
    }

    @PostMapping("/saveEventType")
    @ApiOperation(value = "新增事件类型")
    public R saveEventType(String typeName){
        EventTypeInfo eventTypeInfo=new EventTypeInfo();
        eventTypeInfo.setTypeName(typeName);
        boolean insert = eventTypeInfo.insert();
        if(insert){
            return R.ok("添加成功");
        }
        return R.ok("添加失败");
    }
    @PostMapping("/updateEventType")
    @ApiOperation(value = "修改事件类型")
    public R updateEventType(Integer typeId, String typeName){
        if(typeId==null || StrUtil.isBlankOrUndefined(typeName)){
            return R.nullValueError();
        }
        EventTypeInfo eventTypeInfo = new EventTypeInfo();
        eventTypeInfo.setId(typeId).setTypeName(typeName);
        boolean b = eventTypeInfo.updateById();
        if(b){
            return R.ok("修改成功");
        }
        return R.ok("修改失败");
    }

    @DeleteMapping("/deleteEventType")
    @ApiOperation(value = "根据id删除事件类型")
    public R deleteEventType(Integer id){
        return R.ok(iEventTypeInfoService.removeById(id));
    }

    @PostMapping("/updateEventFeedback")
    @ApiOperation(value = "修改执法事件反馈")
    public R updateEventFeedback(Integer feedBackId, String processMsg,String pic1, String pic2,String pic3,String video,String otherFile,HttpServletRequest request){
        if(feedBackId==null || StrUtil.isBlankOrUndefined(processMsg)){
            return R.nullValueError();
        }
        EventFeedback eventFeedback = new EventFeedback();
        eventFeedback.setId(feedBackId)
                .setProcessMsg(processMsg).setUpdateTime(new Date());
        if(!StrUtil.isBlankOrUndefined(video)){
            eventFeedback.setVideoContentPath(video);
        }
        eventFeedback.setCheckStatus(0);
        boolean b = eventFeedback.updateById();
        EventFeedbackFile eff = new EventFeedbackFile();
        eff.setFeedBackId(feedBackId);
        if(!StrUtil.isBlankOrUndefined(pic1)){
            eff.setPic1Path(pic1);
        }
        if(!StrUtil.isBlankOrUndefined(pic2)){
            eff.setPic2Path(pic2);
        }
        if(!StrUtil.isBlankOrUndefined(pic3)){
            eff.setPic3Path(pic3);
        }
        if(!StrUtil.isBlankOrUndefined(otherFile)){
            eff.setFilePath(otherFile);
        }
        eff.setUpdateTime(new Date());
        b = eff.updateById();
        if(b){
            return R.ok("修改成功");
        }
        return R.ok("修改失败");
    }

    @GetMapping("/getEventFeedbackById")
    @ApiOperation(value = "根据id获取执法事件反馈")
    public R getEventFeedbackById(Integer id){
        EventFeedback eventFeedback = iEventFeedbackService.getById(id);
        if(eventFeedback!=null){
            return R.ok(eventFeedback);
        }
        return R.ok("暂无数据");
    }

    @DeleteMapping("/deleteEventFeedback")
    @ApiOperation(value = "根据id删除执法事件反馈")
    public R deleteEventFeedback(Integer id){
        boolean result = iEventFeedbackService.removeById(id);
        if(result){
            eventFeedbackFileService.removeById(id);
            return R.ok("删除成功");
        }
        return R.ok("删除失败");
    }


    @ApiOperation(value = "创建执法反馈",notes = "")
    @ApiImplicitParams({
            @ApiImplicitParam(name="eventId",value="反馈所属事件ID", required=true,paramType = "query",  dataType="string"),
            @ApiImplicitParam(name="apiType",value="接口来源(h5/pc)", required=true,paramType = "query",  dataType="string"),

    })
    @PostMapping("/createEventFeedBack")
    public R createEventFeedBack(String apiType, EventFeedbackUpdatePara para,String pic1, String pic2,String pic3,String video,String otherFile,HttpServletRequest request){
        if(para.getEventId()==null) return R.nullValueError();
        if(StrUtil.isBlankOrUndefined(apiType)) return R.nullValueError();
        DepartmentMember dm = null;
        CustomUser cu = JwtUtil.getFromJwt(request);
        Integer userId = cu.getUserId();
        if("pc".equals(apiType)){
            DepartmentInfo di = departmentInfoService.lambdaQuery().eq(DepartmentInfo::getLeaderUserId,userId).one();
            if(di==null){
                return R.error("无权限");
            }
            dm = departmentMemberService.lambdaQuery().eq(DepartmentMember::getId,di.getLeader()).one();
            if(dm==null){
                return R.error("无权限");
            }
        }else{
            dm = departmentMemberService.lambdaQuery().eq(DepartmentMember::getUserId,userId).one();
            if(dm==null){
                return R.error("部员不存在或部员请求需要通过h5发送");
            }
        }
        EventFeedback efb = iEventFeedbackService.lambdaQuery().eq(EventFeedback::getEventId,para.getEventId())
                .eq(EventFeedback::getDepartmemberId,dm.getId()).one();
        if(efb!=null){
            return R.error("当前用户已存在反馈勿重复创建");
        }
        EventFeedback ef = new EventFeedback();
        ef.setEventId(para.getEventId()).setProcessMsg(para.getProcessMsg())
                .setReportStatus(apiType.toLowerCase().equals("pc")?2:1)
                .setCheckStatus(apiType.toLowerCase().equals("pc")?3:0)
                .setCreateTime(new Date())
                .setDepartmemberId(dm.getId())
                .setCreateUser(dm.getName())
        ;
        try {
            Boolean r = iEventFeedbackService.addEventFeedBack(ef,pic1,pic2,pic3,video,otherFile);
            Map<String,Object> m = new HashMap<>();
            m.put("feedbackId", ef.getId());
            return r?R.ok(m):R.fail();
        } catch (Exception e) {
            return  R.error(e.getMessage());
        }
    }


    @RequiredPermission(hasRoleCode = "ADMIN")
    @PostMapping("adminCheckFeedBack")
    @ApiOperation(value = "管理员审核反馈")
    @ApiImplicitParams({
            @ApiImplicitParam(name="feedbackId",value="反馈ID", required=true,paramType = "query",  dataType="string"),
            @ApiImplicitParam(name="isAdminChecked", value="1管理员审核通过,2管理员审核不通过", required=true,paramType = "query",  dataType="string"),
            @ApiImplicitParam(name="isNeedInter", value="1需要其他部门介入,2不需要介入", required=true,paramType = "query",  dataType="string"),
    })
    public R adminCheckFeedBack(Integer feedbackId, Integer isAdminChecked,Integer isNeedInter, HttpServletRequest request){
        if(feedbackId==null || isAdminChecked==null){
            return R.nullValueError();
        }
        EventFeedback ef = iEventFeedbackService.getById(feedbackId);
        EventFeedback ef2 = new EventFeedback();
        ef2.setId(feedbackId).setUpdateTime(new Date());
        if(isAdminChecked==1){
            ef2.setCheckStatus(4);
        }else{
            ef2.setCheckStatus(5);
        }
        ef2.setReportStatus(2);
        boolean r = ef2.updateById();
        EventInfo ei = new EventInfo();
        ei.setId(ef.getEventId());
        if(isAdminChecked==1){
            ei.setEventStatus(9);
        }
//        else{
//            ei.setEventStatus(8);
//        }
        ei.setUpdateTime(LocalDateTime.now());
        r = ei.updateById();
        return r?R.success():R.fail();
    }


    @PostMapping("leaderCheckFeedBack")
    @ApiOperation(value = "负责人审核反馈")
    @ApiImplicitParams({
            @ApiImplicitParam(name="feedbackId",value="反馈ID", required=true,paramType = "query",  dataType="string"),
            @ApiImplicitParam(name="isLeaderChecked", value="1负责人审核通过,2负责人审核不通过", required=true,paramType = "query",  dataType="string"),
    })
    public R checkFirstFeedBack(Integer feedbackId, Integer isLeaderChecked,HttpServletRequest request){
        if(feedbackId==null || isLeaderChecked==null){
            return R.nullValueError();
        }
        CustomUser cu = JwtUtil.getFromJwt(request);
        DepartmentInfo dm = departmentInfoService.lambdaQuery().eq(DepartmentInfo::getLeaderLoginName,cu.getUsername()).one();
        if(dm==null){
            return R.error("无权限");
        }
        EventFeedback ef = new EventFeedback();
        ef.setId(feedbackId).setUpdateTime(new Date());
        if(isLeaderChecked==1){
            ef.setCheckStatus(2);
            eventInfoService.lambdaUpdate()
                    .set(EventInfo::getEventStatus,9).set(EventInfo::getUpdateTime, LocalDateTime.now())
                    .eq(EventInfo::getId,iEventFeedbackService.getById(feedbackId).getEventId()).update();
        }else{
            ef.setCheckStatus(1);
        }
        ef.setCheckTime(new Date());
        ef.setReportStatus(2);
        boolean r = ef.updateById();
        return r?R.success():R.fail();
    }


    @PostMapping("appointPunishment")
    @ApiOperation(value = "指定惩罚部门")
    @ApiImplicitParams({
            @ApiImplicitParam(name="eventId",value="事件Id", required=true,paramType = "query",  dataType="string"),
            @ApiImplicitParam(name="departId", value="部门Id", required=true,paramType = "query",  dataType="string"),
    })
    public R appointPunishment(Integer eventId, Integer departId,HttpServletRequest request){
        if(eventId==null || departId==null){
            return R.nullValueError();
        }
        if(eventPunishService.lambdaQuery().eq(EventPunish::getEventId,eventId)
                .eq(EventPunish::getExeuteDepartId,departId).one()!=null){
            return R.error("该部门已指定该事件勿重复指定");
        }
        eventInfoService.lambdaUpdate()
                .eq(EventInfo::getId,eventId)
                .set(EventInfo::getUpdateTime,LocalDateTime.now())
                .set(EventInfo::getEventStatus,10).update();
        EventPunish ep = new EventPunish();
        ep.setEventId(eventId).setExeuteDepartId(departId);
        return ep.insert()?R.success():R.fail();
    }


    @PostMapping("editPunishment")
    @ApiOperation(value = "惩罚部门编写惩罚内容")
    @ApiImplicitParams({
            @ApiImplicitParam(name="eventId",value="事件Id", required=true,paramType = "query",  dataType="string"),
            @ApiImplicitParam(name="message",value="惩罚内容", required=true,paramType = "query",  dataType="string"),
    })
    public R editPunishment(Integer eventId, String message,HttpServletRequest request){
        if(eventId==null || StrUtil.isBlankOrUndefined(message)){
            return R.nullValueError();
        }
        CustomUser cu = JwtUtil.getFromJwt(request);
        DepartmentInfo dm = departmentInfoService.lambdaQuery().eq(DepartmentInfo::getLeaderLoginName,cu.getUsername()).one();
        if(dm==null){
            return R.error("无权限");
        }
        EventPunish ep = eventPunishService.lambdaQuery().eq(EventPunish::getEventId,eventId).eq(EventPunish::getExeuteDepartId,dm.getId()).one();
        if(ep==null){
            return R.error("无权限");
        }
        ep.setPunishRemark(message);
        boolean r = ep.update(new UpdateWrapper<EventPunish>()
                .eq("event_id", eventId)
                .eq("exeute_depart_id", ep.getExeuteDepartId())
        );
        return r?R.success():R.fail();
    }



    @GetMapping("getInterDetailByEventId")
    @ApiOperation(value = "根据事件Id获取介入详情")
    @ApiImplicitParams({
            @ApiImplicitParam(name="eventId",value="事件Id", required=true,paramType = "query",  dataType="Integer"),
    })
    public R getInterDetailByEventId(Integer eventId){
        if(eventId==null){
            return R.nullValueError();
        }
        List<EventPunish> l = eventPunishService.lambdaQuery()
                .eq(EventPunish::getEventId,eventId).list();
        return R.ok(l);
    }



}
