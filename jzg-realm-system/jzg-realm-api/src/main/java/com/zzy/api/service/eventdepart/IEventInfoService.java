package com.zzy.api.service.eventdepart;

import com.baomidou.mybatisplus.extension.service.IService;
import com.github.pagehelper.PageInfo;
import com.zzy.api.dto.EventAddParam;
import com.zzy.api.dto.EventListParam;
import com.zzy.db.entity.eventdepart.DepartmentInfo;
import com.zzy.db.entity.eventdepart.EventInfo;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 事件任务表 服务类
 * </p>
 *
 * @author zzy
 * @since 2020-04-13
 */
public interface IEventInfoService extends IService<EventInfo> {

    boolean addEventByParam(EventAddParam info, MultipartFile pic1, MultipartFile pic2, MultipartFile pic3, MultipartFile video,MultipartFile otherFiles);

    EventInfo addEventByParamCatchId(EventAddParam info, MultipartFile pic1, MultipartFile pic2, MultipartFile pic3, MultipartFile video,MultipartFile otherFiles);

    PageInfo<Map<String,Object>> getEventList(Integer pageNo, Integer pageSize, EventListParam param);

    boolean claimTask(Integer eventId, Integer departMemberId, boolean isExist);

    boolean designateEvent(Integer eventId, Integer departId);

    Map<String,Object> getEventDetailByEventId(Integer eventId);

    /**
     *
     * @param m{departId#必填,eventStatus,title,startTime,endTime}
     * @param pageNo
     * @param pageSize
     * @return
     */
    PageInfo<Map<String, Object>> getDepartmentTaskList(Map<String,Object> m, Integer pageNo, Integer pageSize);

    boolean editEventByParam(EventAddParam info, MultipartFile pic1, MultipartFile pic2, MultipartFile pic3, MultipartFile video, MultipartFile otherFiles);

    PageInfo<Map<String,Object>> getAssignedTaskList(Integer pageNo, Integer pageSize, Map<String, Object> m);

    boolean addEventByParam(EventAddParam info);


    /**
     * 获取事件进度数据
     * */
    Map<String, Object> getschedule(Integer id);

    boolean editEventByParam(EventAddParam info);

    PageInfo<Map<String, Object>> getReDoEventList(Integer pageNo, Integer pageSize, EventListParam param);
}
