package com.zzy.api.service.eventdepart.impl;

import cn.hutool.core.util.StrUtil;
import com.zzy.core.utils.MinioUtil;
import com.zzy.db.dao.eventdepart.EventFeedbackFileMapper;
import com.zzy.db.entity.eventdepart.EventFeedbackFile;
import com.zzy.db.entity.eventdepart.EventFeedback;
import com.zzy.db.dao.eventdepart.EventFeedbackMapper;
import com.zzy.api.service.eventdepart.IEventFeedbackService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zzy.db.entity.eventdepart.EventInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

/**
 * <p>
 * 事件执法反馈 服务实现类
 * </p>
 *
 * @author zzy
 * @since 2020-04-13
 */
@Service
public class EventFeedbackServiceImpl extends ServiceImpl<EventFeedbackMapper, EventFeedback> implements IEventFeedbackService {


    @Autowired
    EventFeedbackMapper eventFeedbackMapper;

    @Autowired
    EventFeedbackFileMapper eventFeedbackFileMapper;


    @Transactional(transactionManager = "primaryTransactionManager")
    @Override
    public boolean addEventFeedBack(EventFeedback ef, MultipartFile pic1, MultipartFile pic2, MultipartFile pic3, MultipartFile video, MultipartFile otherFile) throws Exception {
            if(!MinioUtil.isNullEmpty(video)){
                ef.setVideoContentPath(MinioUtil.uploadFile(MinioUtil.FileType.video.name(), video, MinioUtil.generateFilePathName(video)));
            }
            boolean flag = eventFeedbackMapper.insert(ef)>0;
            if(flag){
                EventFeedbackFile eff = new EventFeedbackFile();
                eff.setFeedBackId(ef.getId());
                if(!MinioUtil.isNullEmpty(pic1)){
                    eff.setPic1Path(MinioUtil.uploadFile(MinioUtil.FileType.pic.name(), pic1, MinioUtil.generateFilePathName(pic1)));
                }
                if(!MinioUtil.isNullEmpty(pic2)){
                    eff.setPic2Path(MinioUtil.uploadFile(MinioUtil.FileType.pic.name(), pic2, MinioUtil.generateFilePathName(pic2)));
                }
                if(!MinioUtil.isNullEmpty(pic3)){
                    eff.setPic3Path(MinioUtil.uploadFile(MinioUtil.FileType.pic.name(), pic3, MinioUtil.generateFilePathName(pic3)));
                }
                if(!MinioUtil.isNullEmpty(otherFile)){
                    eff.setFilePath(MinioUtil.uploadFile(MinioUtil.FileType.other.name(),otherFile,MinioUtil.generateFilePathName(otherFile)));
                }
                if(StrUtil.isBlank(eff.getPic1Path())
                        && StrUtil.isBlank(eff.getPic2Path())
                        && StrUtil.isBlank(eff.getPic3Path())
                        && StrUtil.isBlank(eff.getFilePath())
                ){

                }else{
                    flag = eventFeedbackFileMapper.insert(eff)>0;
                }
            }
        return flag;
    }

    @Override
    public Map<String, Object> findCheckedFeedbackDetailByEventId(Integer evenId) {
        Map<String,Object> m = new HashMap<>();
        m.put("eventId", evenId);
        return eventFeedbackMapper.selectCheckedFeedbackDetailByEventId(m);
    }

    @Override
    public Map<String, Object> findDetailsByIds(Integer evenId, Integer memberId) {
        Map<String,Object> m = new HashMap<>();
        m.put("eventId", evenId);
        m.put("memberId", memberId);
        return eventFeedbackMapper.selectFeedbackDetailByIds(m);
    }

    @Override
    public boolean addEventFeedBack(EventFeedback ef, String pic1, String pic2, String pic3, String video, String otherFile) {
        if(!StrUtil.isBlankOrUndefined(video)){
            ef.setVideoContentPath(video);
        }
        boolean flag = eventFeedbackMapper.insert(ef)>0;
        if(flag){
            EventFeedbackFile eff = new EventFeedbackFile();
            eff.setFeedBackId(ef.getId());
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
            if(StrUtil.isBlank(eff.getPic1Path())
                    && StrUtil.isBlank(eff.getPic2Path())
                    && StrUtil.isBlank(eff.getPic3Path())
                    && StrUtil.isBlank(eff.getFilePath())
            ){

            }else{
                flag = eventFeedbackFileMapper.insert(eff)>0;
            }
        }
        return flag;
    }
}
