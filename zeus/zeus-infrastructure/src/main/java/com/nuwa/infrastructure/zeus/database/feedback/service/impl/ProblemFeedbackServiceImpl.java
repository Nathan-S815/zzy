package com.nuwa.infrastructure.zeus.database.feedback.service.impl;

import com.nuwa.infrastructure.zeus.database.feedback.entity.ProblemFeedback;
import com.nuwa.infrastructure.zeus.database.feedback.mapper.ProblemFeedbackMapper;
import com.nuwa.infrastructure.zeus.database.feedback.service.ProblemFeedbackService;
import com.nuwa.framework.database.supper.SuperServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * 商户问题反馈信息 服务实现类
 *
 * @author huyonghack@163.com
 * @since 2021-07-19
 */
@Slf4j
@Service
public class ProblemFeedbackServiceImpl extends SuperServiceImpl<ProblemFeedbackMapper, ProblemFeedback> implements ProblemFeedbackService {

    @Autowired
    private ProblemFeedbackMapper problemFeedbackMapper;

}
