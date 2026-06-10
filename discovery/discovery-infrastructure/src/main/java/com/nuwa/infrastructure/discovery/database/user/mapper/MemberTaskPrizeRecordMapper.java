package com.nuwa.infrastructure.discovery.database.user.mapper;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.nuwa.framework.database.supper.SuperMapper;
import com.nuwa.infrastructure.discovery.database.user.entity.MemberTaskPrizeRecord;

import com.nuwa.infrastructure.discovery.database.user.vo.EquityManagerVO;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;


/**
 * 达人任务权益提交表 Mapper 接口
 *
 * @author huyonghack@163.com
 * @since 2021-11-18
 */
@Repository
public interface MemberTaskPrizeRecordMapper extends SuperMapper<MemberTaskPrizeRecord> {


    IPage<EquityManagerVO> selectEquityPage(IPage<EquityManagerVO> page,@Param("prizeTitle") String prizeTitle,
                                                                          @Param("beginDate") String beginDate,
                                                                          @Param("endDate") String endDate);
}
