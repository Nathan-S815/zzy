package com.nuwa.infrastructure.attract.database.travel.mapper;

import java.util.Date;
import java.util.List;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.nuwa.framework.database.supper.SuperMapper;
import com.nuwa.infrastructure.attract.database.travel.entity.TeamCustomerRef;

import com.nuwa.infrastructure.vo.TeamCustomerPageQueryVO;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

/**
 * 团队-客户关联表 Mapper 接口
 *
 * @author nanhuang @南皇
 * @since 2022-09-15
 */
@Repository
public interface TeamCustomerRefMapper extends SuperMapper<TeamCustomerRef> {

    /**
     * 获取团队下景区详情
     * @param pageQry
     * @param teamId
     * @param userId
     * @param travelDate
     * @return
     */
    IPage<TeamCustomerPageQueryVO> queryTeamCustomerPage(IPage<?> pageQry,
                                                         @Param("teamId") Long teamId,
                                                         @Param("userId") Long userId,
                                                         @Param("travelDate") Date travelDate);

    IPage<TeamCustomerPageQueryVO> queryTeamCustomer(IPage<?> pageQry,
                                                     @Param("teamId") Long teamId);


//    void updateteamCustomerStatus(@Param("teamId")Long teamId, @Param("status")Integer status);
}
