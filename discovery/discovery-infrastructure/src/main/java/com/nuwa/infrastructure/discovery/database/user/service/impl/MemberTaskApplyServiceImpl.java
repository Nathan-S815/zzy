package com.nuwa.infrastructure.discovery.database.user.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.nuwa.infrastructure.discovery.database.member.entity.MemberGmvRecordDouyin;
import com.nuwa.infrastructure.discovery.database.user.entity.MemberTaskApply;
import com.nuwa.infrastructure.discovery.database.user.mapper.MemberTaskApplyMapper;
import com.nuwa.infrastructure.discovery.database.user.service.MemberTaskApplyService;
import com.nuwa.framework.database.supper.SuperServiceImpl;
import com.nuwa.infrastructure.discovery.database.user.vo.MemberTaskApplyVO;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * 达人任务报名表 服务实现类
 *
 * @author huyonghack@163.com
 * @since 2021-11-10
 */
@Slf4j
@Service
public class MemberTaskApplyServiceImpl extends SuperServiceImpl<MemberTaskApplyMapper, MemberTaskApply> implements MemberTaskApplyService {

    @Autowired
    private MemberTaskApplyMapper memberTaskApplyMapper;

    /**
     * 分页查询出视频列表
     * @param pageSize
     * @param pageNum
     * @param taskId
     * @param name
     * @param beginDate
     * @param endDate
     * @return
     */
    @Override
    public IPage<MemberTaskApplyVO> listVideoList(Long pageSize, Long pageNum,Long taskId, String name, String beginDate, String endDate) {
        Page<MemberTaskApplyVO> page = new Page<>();
        if(pageSize != null && pageNum != null){
            page.setSize(pageSize);
            page.setCurrent(pageNum);
        }

        if (StringUtils.isNotBlank(beginDate)) {
            beginDate = beginDate + " 00:00:00";
        }
        if (StringUtils.isNotBlank(endDate)) {
            endDate = endDate + " 23:59:59";
        }

        IPage<MemberTaskApplyVO> record = memberTaskApplyMapper.selectApplyPage(page, taskId,name,beginDate,endDate);
        return record;
    }
}
