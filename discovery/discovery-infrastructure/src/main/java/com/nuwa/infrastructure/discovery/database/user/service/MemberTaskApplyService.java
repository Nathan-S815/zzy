package com.nuwa.infrastructure.discovery.database.user.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.nuwa.infrastructure.discovery.database.user.entity.MemberTaskApply;
import com.nuwa.framework.database.supper.SuperService;
import com.nuwa.infrastructure.discovery.database.user.vo.MemberTaskApplyVO;

/**
 * 达人任务报名表 服务类
 *
 * @author huyonghack@163.com
 * @since 2021-11-10
 */
public interface MemberTaskApplyService extends SuperService<MemberTaskApply> {

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
    public IPage<MemberTaskApplyVO> listVideoList(Long pageSize, Long pageNum,Long taskId, String name, String beginDate, String endDate);
}
