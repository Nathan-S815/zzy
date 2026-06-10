package com.zzy.api.service.eventdepart.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.StrUtil;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.util.TypeUtils;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.zzy.api.dto.EventAddParam;
import com.zzy.api.dto.EventListParam;
import com.zzy.api.service.eventdepart.IEventInfoService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zzy.core.utils.MapUtil;
import com.zzy.core.utils.MinioUtil;
import com.zzy.core.utils.PhoneMsgUtil;
import com.zzy.db.dao.eventdepart.*;
import com.zzy.db.entity.eventdepart.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collector;
import java.util.stream.Collectors;

/**
 * <p>
 * 事件任务表 服务实现类
 * </p>
 *
 * @author zzy
 * @since 2020-04-13
 */
@Slf4j
@Service
public class EventInfoServiceImpl extends ServiceImpl<EventInfoMapper, EventInfo> implements IEventInfoService {

    @Autowired
    EventInfoMapper eventInfoMapper;

    @Autowired
    EventDepartMemberMapper eventDepartMemberMapper;

    @Autowired
    DepartmentInfoMapper departmentInfoMapper;

    @Autowired
    DepartmentMemberMapper departmentMemberMapper;

    @Autowired
    EventMediaInfoMapper eventMediaInfoMapper;


    @Transactional(transactionManager = "primaryTransactionManager")
    @Override
    public boolean addEventByParam(EventAddParam info,  MultipartFile pic1, MultipartFile pic2, MultipartFile pic3, MultipartFile video,MultipartFile otherFiles) {
        EventInfo ei = new EventInfo();
        ei.setCreateTime(LocalDateTime.now())
                .setEventContent(info.getEventContent())
                .setEventLevel(info.getEventLevel()).setReportStatus(info.getReportStatus())
                .setEventName(info.getEventName()).setEventTypeId(info.getEventTypeId())
                .setIsDelete(0).setReportMemberId(info.getMemeberId())
                .setEventPublisher(info.getEventPublisher())
                .setLat(info.getLat()).setLng(info.getLng())
                .setIsSingle(info.getIsSingle())
        ;
        if(StrUtil.isNotEmpty(info.getHappenTime())){
            ei.setHappenTime(TypeUtils.castToDate(info.getHappenTime()));
        }
        if(info.getReportStatus().equals(2)){
            if(StrUtil.isBlankOrUndefined(info.getDepartIds())){
                ei.setEventStatus(1);
            }else{
                ei.setEventStatus(2);
            }
            ei.setExpireDate(TypeUtils.castToDate(info.getExpireDate()));
        }
        boolean flag = eventInfoMapper.insert(ei)>0;
        if(!flag) return false;
        EventMediaInfo emi = new EventMediaInfo();
        emi.setEventId(ei.getId());
        try {
            if(!MinioUtil.isNullEmpty(pic1)){
                emi.setPic1Path(MinioUtil.uploadFile(MinioUtil.FileType.pic.name(), pic1, MinioUtil.generateFilePathName(pic1)));
            }
            if(!MinioUtil.isNullEmpty(pic2)){
                emi.setPic2Path(MinioUtil.uploadFile(MinioUtil.FileType.pic.name(), pic2, MinioUtil.generateFilePathName(pic2)));
            }
            if(!MinioUtil.isNullEmpty(pic3)){
                emi.setPic3Path(MinioUtil.uploadFile(MinioUtil.FileType.pic.name(), pic3, MinioUtil.generateFilePathName(pic3)));
            }
            if(!MinioUtil.isNullEmpty(video)){
                emi.setVideoPath(MinioUtil.uploadFile(MinioUtil.FileType.video.name(), video, MinioUtil.generateFilePathName(video)));
            }
            if(!MinioUtil.isNullEmpty(otherFiles)){
                emi.setFilePath(MinioUtil.uploadFile(MinioUtil.FileType.other.name(),otherFiles,MinioUtil.generateFilePathName(otherFiles)));
            }
            if(StrUtil.isBlank(emi.getPic1Path())
                    && StrUtil.isBlank(emi.getPic2Path())
                    && StrUtil.isBlank(emi.getPic3Path())
                    && StrUtil.isBlank(emi.getVideoPath())
                    && StrUtil.isBlank(emi.getFilePath())
            ){

            }else{
                flag = emi.insert();
            }
        } catch (Exception e) {
            log.error("创建事件图片上传异常",e);
        }
        if( ei.getReportStatus()==1 || ei.getEventStatus()==1 ){
            return flag;
        }
        String[] ids = info.getDepartIds().split(",");
        if(ids.length > 0){
            List<DepartmentInfo> l = departmentInfoMapper.selectBatchIds(Arrays.asList(ids));
            List<String> phones = l.stream().map(o->o.getPhone()).collect(Collectors.toList());
            PhoneMsgUtil.sendGroupMsg("您负责的部门接收到最新任务指定，请及时处理", phones);
            log.info("您负责的部门接收到最新任务指定，请及时处理>phones:{}", phones);
            List<Integer> memberIds = l.stream().map(o->o.getLeader()).collect(Collectors.toList());
            EventDepartMember departMember = null;
            List<EventDepartMember> list = new ArrayList<>();
            for (Integer id : memberIds) {
                departMember = new EventDepartMember();
                departMember.setEventId(ei.getId()).setDepartMemberId(id)
                        .setIsNotify(1).setIsAssigned(0).setCreateTime(new Date())
                        .setNotifyTime(new Date());
                list.add(departMember);
            }
            flag = eventDepartMemberMapper.batchInsert(list);
        }
        return flag;
    }


    @Override
    public PageInfo<Map<String, Object>> getEventList(Integer pageNo, Integer pageSize, EventListParam param) {
        Map<String,Object> m = BeanUtil.beanToMap(param);
        m = MapUtil.filterBlankMap(m);
        m.put("reportStatus", 2);
        if(!StrUtil.isBlankOrUndefined(param.getEventStatus())){
            String stats = param.getEventStatus().replace("[", "").replace("]", "");
            String[] ids = stats.split(",");
            if(ids.length>0){
                List<Integer> ds = Arrays.asList(ids).stream().map(o->Integer.parseInt(o)).collect(Collectors.toList());
                m.put("eventStatus",ds);
            }
        }
        if(!StrUtil.isBlankOrUndefined(param.getStartTime())){
            m.put("startTime", TypeUtils.castToDate(param.getStartTime()));
        }
        if(!StrUtil.isBlankOrUndefined(param.getEndTime())){
            m.put("endTime", TypeUtils.castToDate(param.getEndTime()));
        }
        PageHelper.startPage(pageNo,pageSize);
        List<Map<String,Object>> list = eventInfoMapper.selectPageEventList(m);
        return new PageInfo<>(list);
    }


    /**
     * 任务认领
     * m(key:eventId,memberId)
     * @return
     */
    @Override
    public boolean claimTask(Integer eventId, Integer departMemberId,boolean isExist) {
        EventDepartMember ed = new EventDepartMember();
        ed.setDepartMemberId(departMemberId).setEventId(eventId)
                .setIsAssigned(1).setAssignedTime(new Date());
        boolean r = false;
        if (!isExist) {
            ed.setCreateTime(new Date());
             r = ed.insert();
        }else{
             r = eventDepartMemberMapper.update(ed,new QueryWrapper<EventDepartMember>()
                    .eq("event_id", eventId).eq("depart_member_id", departMemberId))>0;
        }
        if(r){
            EventInfo ei = new EventInfo();
            ei.setId(eventId).setEventStatus(3).setUpdateTime(LocalDateTime.now());
            r = ei.updateById();
        }
        return r;
    }

    @Override
    public boolean designateEvent(Integer eventId, Integer departId) {
        DepartmentMember dm = departmentMemberMapper.selectById(departmentInfoMapper.selectById(departId).getLeader());
        EventDepartMember ed = new EventDepartMember();
        ed.setEventId(eventId).setDepartMemberId(dm.getId()).setIsAssigned(0)
                .setIsNotify(1).setNotifyTime(new Date()).setCreateTime(new Date());
//        PhoneMsgUtil.sendMobileMsg("您的部门收到最新事件指派，请及时处理.", dm.getPhoneNumber());
        log.info("您的部门收到最新事件指派，请及时处理.mobile:{}",dm.getPhoneNumber());
        return ed.insert();
    }


    @Override
    public Map<String, Object> getEventDetailByEventId(Integer eventId) {
        return eventInfoMapper.selectEventDetailByeventId(eventId);
    }

    @Override
    public PageInfo<Map<String, Object>> getDepartmentTaskList(Map<String,Object> m, Integer pageNo, Integer pageSize) {
//        Map<String,Object> m = new HashMap<>();
//        if(departmentInfo!=null){
//            m.put("departId",departmentInfo.getId());
//        }
        PageHelper.startPage(pageNo,pageSize);
        List<Map<String, Object>> l = eventInfoMapper.selectDepartmentTaskList(m);
        return new PageInfo<>(l);
    }

    @Override
    public boolean editEventByParam(EventAddParam info, MultipartFile pic1, MultipartFile pic2, MultipartFile pic3, MultipartFile video, MultipartFile otherFiles) {
        EventInfo ei = new EventInfo();
        ei.setId(info.getEventTmpId())
                .setEventContent(info.getEventContent())
                .setEventLevel(info.getEventLevel()).setReportStatus(info.getReportStatus())
                .setEventName(info.getEventName()).setEventTypeId(info.getEventTypeId())
                .setIsDelete(0).setReportMemberId(info.getMemeberId())
                .setEventPublisher(info.getEventPublisher())
                .setLat(info.getLat()).setLng(info.getLng())
                .setIsSingle(info.getIsSingle())
        ;
        if(StrUtil.isNotEmpty(info.getHappenTime())){
            ei.setHappenTime(TypeUtils.castToDate(info.getHappenTime()));
        }
        if(info.getReportStatus().equals(2)){
            if(StrUtil.isBlankOrUndefined(info.getDepartIds())){
                ei.setEventStatus(1);
            }else{
                ei.setEventStatus(2);
            }
            ei.setExpireDate(TypeUtils.castToDate(info.getExpireDate()));
        }
        boolean flag = eventInfoMapper.updateById(ei)>0;
        if(!flag) return false;
        EventMediaInfo emi = eventMediaInfoMapper.selectById(ei.getId());
        try {
            if(!MinioUtil.isNullEmpty(pic1)){
                emi.setPic1Path(MinioUtil.uploadFile(MinioUtil.FileType.pic.name(), pic1, MinioUtil.generateFilePathName(pic1)));
            }
            if(!MinioUtil.isNullEmpty(pic2)){
                emi.setPic2Path(MinioUtil.uploadFile(MinioUtil.FileType.pic.name(), pic2, MinioUtil.generateFilePathName(pic2)));
            }
            if(!MinioUtil.isNullEmpty(pic3)){
                emi.setPic3Path(MinioUtil.uploadFile(MinioUtil.FileType.pic.name(), pic3, MinioUtil.generateFilePathName(pic3)));
            }
            if(!MinioUtil.isNullEmpty(video)){
                emi.setVideoPath(MinioUtil.uploadFile(MinioUtil.FileType.video.name(), video, MinioUtil.generateFilePathName(video)));
            }
            if(!MinioUtil.isNullEmpty(otherFiles)){
                emi.setFilePath(MinioUtil.uploadFile(MinioUtil.FileType.other.name(),otherFiles,MinioUtil.generateFilePathName(otherFiles)));
            }
            if(StrUtil.isBlank(emi.getPic1Path())
                    && StrUtil.isBlank(emi.getPic2Path())
                    && StrUtil.isBlank(emi.getPic3Path())
                    && StrUtil.isBlank(emi.getVideoPath())
                    && StrUtil.isBlank(emi.getFilePath())
            ){

            }else{
                flag = emi.insert();
            }
        } catch (Exception e) {
            log.error("创建事件图片上传异常",e);
        }
        if( ei.getReportStatus()==1 || ei.getEventStatus()==1 ){
            return flag;
        }
        String[] ids = info.getDepartIds().split(",");
        if(ids.length > 0){
            List<DepartmentInfo> l = departmentInfoMapper.selectBatchIds(Arrays.asList(ids));
            List<String> phones = l.stream().map(o->o.getPhone()).collect(Collectors.toList());
            PhoneMsgUtil.sendGroupMsg("您负责的部门接收到最新任务指定，请登录PC查看处理", phones);
            log.info("您负责的部门接收到最新任务指定，请及时处理>phones:{}", phones);
            List<Integer> memberIds = l.stream().map(o->o.getLeader()).collect(Collectors.toList());
            EventDepartMember departMember = null;
            List<EventDepartMember> list = new ArrayList<>();
            for (Integer id : memberIds) {
                departMember = new EventDepartMember();
                departMember.setEventId(ei.getId()).setDepartMemberId(id)
                        .setIsNotify(1).setIsAssigned(0).setCreateTime(new Date())
                        .setNotifyTime(new Date());
                list.add(departMember);
            }
            flag = eventDepartMemberMapper.batchInsert(list);
        }
        return flag;
    }



    @Override
    public PageInfo<Map<String, Object>> getAssignedTaskList(Integer pageNo, Integer pageSize, Map<String, Object> m) {
        PageHelper.startPage(pageNo,pageSize);
        List<Map<String,Object>> l = eventInfoMapper.selectAssignedTaskList(m);
        return new PageInfo<>(l);
    }

    @Override
    public boolean addEventByParam(EventAddParam info) {
        EventInfo ei = new EventInfo();
        ei.setCreateTime(LocalDateTime.now())
                .setEventContent(info.getEventContent())
                .setAddress(info.getAddress())
                .setEventLevel(info.getEventLevel()).setReportStatus(info.getReportStatus())
                .setEventName(info.getEventName()).setEventTypeId(info.getEventTypeId())
                .setIsDelete(0).setReportMemberId(info.getMemeberId())
                .setEventPublisher(info.getEventPublisher())
                .setLat(info.getLat()).setLng(info.getLng())
                .setEventForm(info.getEventForm())
                .setIsSingle(info.getIsSingle())
        ;
        if(StrUtil.isNotEmpty(info.getHappenTime())){
            ei.setHappenTime(TypeUtils.castToDate(info.getHappenTime()));
        }
        if(info.getReportStatus().equals(2)){
            if(info.getIsSingle().equals(1)){
                if(StrUtil.isBlankOrUndefined(info.getDepartIds())){
                    ei.setEventStatus(1);
                }else{
                    ei.setEventStatus(3);
                }
            }else{
                ei.setEventStatus(3);
            }
            ei.setExpireDate(TypeUtils.castToDate(info.getExpireDate()));
        }
        boolean flag = eventInfoMapper.insert(ei)>0;
        if(!flag) return false;
        EventMediaInfo emi = new EventMediaInfo();
        emi.setEventId(ei.getId());
        try {
            if(!StrUtil.isBlankOrUndefined(info.getPic1())){
                emi.setPic1Path(info.getPic1());
            }
            if(!StrUtil.isBlankOrUndefined(info.getPic2())){
                emi.setPic2Path(info.getPic2());
            }
            if(!StrUtil.isBlankOrUndefined(info.getPic3())){
                emi.setPic3Path(info.getPic3());
            }
            if(!StrUtil.isBlankOrUndefined(info.getVideo())){
                emi.setVideoPath(info.getVideo());
            }
            if(!StrUtil.isBlankOrUndefined(info.getOtherFiles())){
                emi.setFilePath(info.getOtherFiles());
            }
            if(StrUtil.isBlank(emi.getPic1Path())
                    && StrUtil.isBlank(emi.getPic2Path())
                    && StrUtil.isBlank(emi.getPic3Path())
                    && StrUtil.isBlank(emi.getVideoPath())
                    && StrUtil.isBlank(emi.getFilePath())
            ){

            }else{
                flag = emi.insert();
            }
        } catch (Exception e) {
            log.error("创建事件图片上传异常",e);
        }
        if( ei.getReportStatus()==1 || ei.getEventStatus()==1 ){
            return flag;
        }
        String[] ids = info.getDepartIds().split(",");
        if(ids.length > 0){
            List<DepartmentInfo> l = departmentInfoMapper.selectBatchIds(Arrays.asList(ids));
            List<Integer> memberIds = l.stream().map(o->o.getLeader()).collect(Collectors.toList());
            EventDepartMember departMember = null;
            List<EventDepartMember> list = new ArrayList<>();
            for (Integer id : memberIds) {
                departMember = new EventDepartMember();
                if(ei.getIsSingle()==0){
                    departMember.setIsAssigned(1);
                    departMember.setAssignedTime(new Date());
                }else{
                    departMember.setIsAssigned(0);
                }
                departMember.setEventId(ei.getId()).setDepartMemberId(id)
                        .setIsNotify(1).setCreateTime(new Date())
                        .setNotifyTime(new Date());
                list.add(departMember);
            }
            flag = eventDepartMemberMapper.batchInsert(list);
            List<String> phones = l.stream().map(o->o.getPhone()).collect(Collectors.toList());
            PhoneMsgUtil.sendGroupMsg("您负责的部门接收到最新任务指定，请登录PC查看处理", phones);
            log.info("您负责的部门接收到最新任务指定，请及时处理>phones:{}", phones);
        }
        return flag;
    }



    @Override
    public EventInfo addEventByParamCatchId(EventAddParam info,  MultipartFile pic1, MultipartFile pic2, MultipartFile pic3, MultipartFile video,MultipartFile otherFiles) {
        EventInfo ei = new EventInfo();
        ei.setCreateTime(LocalDateTime.now())
                .setEventContent(info.getEventContent())
                .setEventLevel(info.getEventLevel()).setReportStatus(info.getReportStatus())
                .setEventName(info.getEventName()).setEventTypeId(info.getEventTypeId())
                .setIsDelete(0).setReportMemberId(info.getMemeberId())
                .setEventPublisher(info.getEventPublisher())
                .setLat(info.getLat()).setLng(info.getLng())
                .setIsSingle(info.getIsSingle())
        ;
        if (StrUtil.isNotEmpty(info.getHappenTime())) {
            ei.setHappenTime(TypeUtils.castToDate(info.getHappenTime()));
        }
        if (info.getReportStatus().equals(2)) {
            if (StrUtil.isBlankOrUndefined(info.getDepartIds())) {
                ei.setEventStatus(1);
                ei.setEventForm(2);
            } else {
                ei.setEventStatus(3);
                ei.setEventForm(1);
            }
            ei.setExpireDate(TypeUtils.castToDate(info.getExpireDate()));
        }
        eventInfoMapper.insertCatchId(ei);
        String[] ids = info.getDepartIds().split(",");
        if(ids.length > 0) {
            List<DepartmentInfo> l = departmentInfoMapper.selectBatchIds(Arrays.asList(ids));
            List<Integer> memberIds = l.stream().map(o -> o.getLeader()).collect(Collectors.toList());
            EventDepartMember departMember = null;
            List<EventDepartMember> list = new ArrayList<>();
            for (Integer id : memberIds) {
                departMember = new EventDepartMember();
                if (ei.getIsSingle() == 0) {
                    departMember.setIsAssigned(1);
                    departMember.setAssignedTime(new Date());
                } else {
                    departMember.setIsAssigned(0);
                }
                departMember.setEventId(ei.getId()).setDepartMemberId(id)
                        .setIsNotify(1).setCreateTime(new Date())
                        .setNotifyTime(new Date());
                list.add(departMember);
            }

            eventDepartMemberMapper.batchInsert(list);
            List<String> phones = l.stream().map(o -> o.getPhone()).collect(Collectors.toList());
            PhoneMsgUtil.sendGroupMsg("您负责的部门接收到最新任务指定，请登录PC查看处理", phones);
            //log.info("您负责的部门接收到最新任务指定，请及时处理>phones:{}", phones);
        }
            return ei;



    }


    @Override
    public Map<String, Object> getschedule(Integer id) {
        List<Map<String, Object>> getschedule = eventInfoMapper.getschedule1(id);
        List<Map<String, Object>> stringObjectMap = eventInfoMapper.getschedule2(id);
        List<Map<String, Object>> maps = eventInfoMapper.getschedule3(id);
        List<Map<String, Object>> stringObjectMap1 = eventInfoMapper.getschedule4(id);
      /* List<Map<String, Object>> list=new ArrayList<>();
        maps.add(getschedule);
        maps.add(stringObjectMap1);
        for (Map<String, Object> map : maps) {
            list.add(map);
        }
        for (Map<String, Object> objectMap : stringObjectMap) {
            list.add(objectMap);
        }*/
        Map<String, Object> map = new LinkedHashMap<>();
        map.put("eventCreatTime",getschedule);
        map.put("eventClaim",stringObjectMap);
        map.put("eventDispose",maps);
        map.put("eventFeedback",stringObjectMap1);

        return map;
    }

    @Override
    public boolean editEventByParam(EventAddParam info) {
        EventInfo ei = new EventInfo();
        ei.setId(info.getEventTmpId())
                .setAddress(info.getAddress())
                .setEventContent(info.getEventContent())
                .setEventLevel(info.getEventLevel()).setReportStatus(info.getReportStatus())
                .setEventName(info.getEventName()).setEventTypeId(info.getEventTypeId())
                .setIsDelete(0).setReportMemberId(info.getMemeberId())
                .setEventPublisher(info.getEventPublisher())
                .setLat(info.getLat()).setLng(info.getLng())
                .setIsSingle(info.getIsSingle())
        ;
        if(StrUtil.isNotEmpty(info.getHappenTime())){
            ei.setHappenTime(TypeUtils.castToDate(info.getHappenTime()));
        }
        if(info.getReportStatus().equals(2)){
            if(StrUtil.isBlankOrUndefined(info.getDepartIds())){
                ei.setEventStatus(1);
            }else{
                ei.setEventStatus(3);
            }
            ei.setExpireDate(TypeUtils.castToDate(info.getExpireDate()));
        }
        boolean flag = eventInfoMapper.updateById(ei)>0;
        if(!flag) return false;
        EventMediaInfo emi = eventMediaInfoMapper.selectById(ei.getId());
        int emiId = -1;
        if(emi!=null){
            emiId = emi.getEventId();
        }else{
            emi = new EventMediaInfo();
        }
        try {
            if(!StrUtil.isBlankOrUndefined(info.getPic1())){
                emi.setPic1Path(info.getPic1());
            }
            if(!StrUtil.isBlankOrUndefined(info.getPic2())){
                emi.setPic1Path(info.getPic2());
            }
            if(!StrUtil.isBlankOrUndefined(info.getPic3())){
                emi.setPic1Path(info.getPic3());
            }
            if(!StrUtil.isBlankOrUndefined(info.getVideo())){
                emi.setPic1Path(info.getVideo());
            }
            if(!StrUtil.isBlankOrUndefined(info.getOtherFiles())){
                emi.setPic1Path(info.getOtherFiles());
            }
            if(StrUtil.isBlank(emi.getPic1Path())
                    && StrUtil.isBlank(emi.getPic2Path())
                    && StrUtil.isBlank(emi.getPic3Path())
                    && StrUtil.isBlank(emi.getVideoPath())
                    && StrUtil.isBlank(emi.getFilePath())
            ){

            }else{
                if(emiId==-1){
                    flag = emi.insert();
                }else{
                    flag = emi.updateById();
                }

            }
        } catch (Exception e) {
            log.error("创建事件图片上传异常",e);
        }
        if(ei.getReportStatus()!=null && ei.getEventStatus()!=null && (ei.getReportStatus()==1 || ei.getEventStatus()==1) ){
            return flag;
        }
        String[] ids = info.getDepartIds().split(",");
        if(ids.length > 0){
            List<DepartmentInfo> l = departmentInfoMapper.selectBatchIds(Arrays.asList(ids));
            List<String> phones = l.stream().map(o->o.getPhone()).collect(Collectors.toList());
            try {
                PhoneMsgUtil.sendGroupMsg("您负责的部门接收到最新任务指定，请及时处理", phones);
            } catch (Exception e) {
                log.error("短信发送异常", e);
            }
            log.info("您负责的部门接收到最新任务指定，请及时处理>phones:{}", phones);
            List<Integer> memberIds = l.stream().map(o->o.getLeader()).collect(Collectors.toList());
            EventDepartMember departMember = null;
            List<EventDepartMember> list = new ArrayList<>();
            for (Integer id : memberIds) {
                departMember = new EventDepartMember();
                departMember.setEventId(ei.getId()).setDepartMemberId(id)
                        .setIsNotify(1).setCreateTime(new Date())
                        .setNotifyTime(new Date());
                if(info.getIsSingle().equals(1)){
                    departMember.setIsAssigned(0);
                }else{
                    departMember.setIsAssigned(1);
                    departMember.setAssignedTime(new Date());
                }
                list.add(departMember);
            }
            flag = eventDepartMemberMapper.batchInsert(list);
        }
        return flag;
    }

    @Override
    public PageInfo<Map<String, Object>> getReDoEventList(Integer pageNo, Integer pageSize, EventListParam param) {
        Map<String,Object> m = BeanUtil.beanToMap(param);
        m = MapUtil.filterBlankMap(m);
        m.put("reportStatus", 2);
        m.put("eventStatus",10);
        if(!StrUtil.isBlankOrUndefined(param.getStartTime())){
            m.put("startTime", TypeUtils.castToDate(param.getStartTime()));
        }
        if(!StrUtil.isBlankOrUndefined(param.getEndTime())){
            m.put("endTime", TypeUtils.castToDate(param.getEndTime()));
        }
        PageHelper.startPage(pageNo,pageSize);
        List<Map<String,Object>> list = eventInfoMapper.selectPageRedoEventList(m);
        return new PageInfo<>(list);
    }
}
