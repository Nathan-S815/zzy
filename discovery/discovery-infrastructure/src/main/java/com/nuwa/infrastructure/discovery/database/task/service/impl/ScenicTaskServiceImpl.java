package com.nuwa.infrastructure.discovery.database.task.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.nuwa.framework.base.UserAware;
import com.nuwa.infrastructure.discovery.database.task.entity.ScenicTask;
import com.nuwa.infrastructure.discovery.database.task.mapper.ScenicTaskMapper;
import com.nuwa.infrastructure.discovery.database.task.service.ScenicTaskService;
import com.nuwa.framework.database.supper.SuperServiceImpl;
import com.nuwa.infrastructure.discovery.database.user.entity.Industry;
import com.nuwa.infrastructure.discovery.database.user.mapper.IndustryMapper;
import com.nuwa.infrastructure.discovery.database.user.service.IndustryService;
import com.nuwa.infrastructure.discovery.database.user.vo.MyTaskVO;
import com.nuwa.infrastructure.discovery.database.user.vo.NewTaskVO;
import com.nuwa.infrastructure.discovery.enums.SourceEnum;
import io.swagger.models.auth.In;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 景区任务表 服务实现类
 *
 * @author huyonghack@163.com
 * @since 2021-11-08
 */
@Slf4j
@Service
public class ScenicTaskServiceImpl extends SuperServiceImpl<ScenicTaskMapper, ScenicTask> implements ScenicTaskService {

    @Autowired
    private ScenicTaskMapper scenicTaskMapper;

    @Autowired
    private IndustryService industryService;

    @Autowired
    private IndustryMapper industryMapper;

    /**
     * 我发布的任务列表
     * @param pageSize
     * @param pageNum
     * @param userAware
     * @return
     */
    @Override
    public IPage<MyTaskVO> getMyPublishTaskList(Long pageSize, Long pageNum, UserAware userAware) {
        Long userId = userAware.getUserId();
        Page<MyTaskVO> page = new Page<>();
        if (pageSize != null && pageNum != null) {
            page.setSize(pageSize);
            page.setCurrent(pageNum);
        }
        IPage<MyTaskVO> record = scenicTaskMapper.getMyPublishTaskList(page, userId, SourceEnum.CONSUMER.getCode());
        return record;
    }


    @Override
    public IPage<NewTaskVO> getNewestTask(Long pageSize, Long pageNum, String mchId) {
        Page<NewTaskVO> page = new Page<>();
        if (pageSize != null && pageNum != null) {
            page.setSize(pageSize);
            page.setCurrent(pageNum);
        }
        IPage<NewTaskVO> record = scenicTaskMapper.selectNewTaskList(page, mchId);
        for (NewTaskVO newTaskVO : record.getRecords()) {
            if (newTaskVO.getIndustryCode() != null) {
                QueryWrapper<Industry> queryWrapper = new QueryWrapper<>();
                queryWrapper.in("industry_code", Arrays.asList(newTaskVO.getIndustryCode().split(",")));
                List<Industry> industryList = industryMapper.selectList(queryWrapper);
                newTaskVO.setIndustryName(industryList.stream().map(Industry::getIndustryName).collect(Collectors.toList()));
            }
        }
        return record;
    }
}
