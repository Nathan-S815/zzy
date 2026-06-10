package com.nuwa.infrastructure.discovery.database.user.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.nuwa.infrastructure.discovery.database.user.entity.MemberTaskPrizeRecord;
import com.nuwa.framework.database.supper.SuperService;
import com.nuwa.infrastructure.discovery.database.user.vo.EquityManagerVO;

/**
 * 达人任务权益提交表 服务类
 *
 * @author huyonghack@163.com
 * @since 2021-11-18
 */
public interface MemberTaskPrizeRecordService extends SuperService<MemberTaskPrizeRecord> {

    /**
     * 权利管理(探店免票)
     * @param pageSize
     * @param pageNum
     * @param prizeTitle
     * @param beginDate
     * @param endDate
     * @return
     */
    IPage<EquityManagerVO> equityManagerList(Long pageSize, Long pageNum, String prizeTitle, String beginDate, String endDate);
}
