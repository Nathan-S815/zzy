package com.nuwa.infrastructure.discovery.database.user.mapper;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.nuwa.framework.database.supper.SuperMapper;
import com.nuwa.infrastructure.discovery.database.user.entity.MemberTaskApply;

import com.nuwa.infrastructure.discovery.database.user.vo.MemberTaskApplyVO;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;




/**
 * 达人任务报名表 Mapper 接口
 *
 * @author huyonghack@163.com
 * @since 2021-11-11
 */
@Repository
public interface MemberTaskApplyMapper extends SuperMapper<MemberTaskApply> {


    IPage<MemberTaskApplyVO> selectApplyPage(IPage<MemberTaskApplyVO> page, @Param("taskId")Long taskId,
                                             @Param("name") String name,
                                             @Param("beginDate") String beginDate,
                                             @Param("endDate") String endDate);

}
