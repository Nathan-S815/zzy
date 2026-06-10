package com.nuwa.infrastructure.attract.database.teamuserref.mapper;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.nuwa.framework.database.supper.SuperMapper;
import com.nuwa.infrastructure.attract.database.teamuserref.entity.TeamUserRef;

import com.nuwa.infrastructure.vo.MchTravelTeamPageVO;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;


/**
 *  Mapper 接口
 *
 * @author nanhuang @南皇
 * @since 2022-11-08
 */
@Repository
public interface TeamUserRefMapper extends SuperMapper<TeamUserRef> {


    IPage<MchTravelTeamPageVO> qryAuditList(IPage<MchTravelTeamPageVO> page,
                                      @Param("userId") Long userId,
                                      @Param("travelAgencyId") Long travelAgencyId,
                                      @Param("teamId") Long teamId,
                                      @Param("mchName") String mchName,
                                      @Param("startDate") String startDate,
                                      @Param("endDate") String endDate,
                                      @Param("status") Integer status);
}
