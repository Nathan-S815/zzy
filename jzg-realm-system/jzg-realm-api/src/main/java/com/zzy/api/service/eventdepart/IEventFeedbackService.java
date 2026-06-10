package com.zzy.api.service.eventdepart;

import com.zzy.api.dto.EventFeedbackUpdatePara;
import com.zzy.db.entity.eventdepart.EventFeedback;
import com.baomidou.mybatisplus.extension.service.IService;
import org.springframework.web.multipart.MultipartFile;

import java.util.Map;

/**
 * <p>
 * 事件执法反馈 服务类
 * </p>
 *
 * @author zzy
 * @since 2020-04-13
 */
public interface IEventFeedbackService extends IService<EventFeedback> {

    boolean addEventFeedBack(EventFeedback ef, MultipartFile pic1, MultipartFile pic2, MultipartFile pic3, MultipartFile video, MultipartFile otherFile) throws Exception;

    Map<String, Object> findDetailsByIds(Integer evenId, Integer memberId);

    Map<String, Object> findCheckedFeedbackDetailByEventId(Integer evenId);

    boolean addEventFeedBack(EventFeedback ef, String pic1, String pic2, String pic3, String video, String otherFile);
}
