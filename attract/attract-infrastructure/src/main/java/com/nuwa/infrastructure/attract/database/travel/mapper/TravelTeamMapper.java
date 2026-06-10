package com.nuwa.infrastructure.attract.database.travel.mapper;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.nuwa.framework.database.supper.SuperMapper;
import com.nuwa.infrastructure.attract.database.travel.entity.TravelTeam;

import com.nuwa.infrastructure.vo.TravelTeamPageVO;
import com.nuwa.infrastructure.vo.TravelTeamVO;
import io.swagger.models.auth.In;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;


/**
 * 旅行团 Mapper 接口
 *
 * @author nanhuang @南皇
 * @since 2022-09-15
 */
@Repository
public interface TravelTeamMapper extends SuperMapper<TravelTeam> {


    List<Map<String,Object>> qryTravelAgencyNum();

    List<Map<String,Object>> qrytravelTamAgencyNum();

    List<Map<String, Object>> qryAgencyNum(@Param("account_type") Integer account_type);

    List<Map<String, Object>> qryTeamAgencyNum(@Param("account_type") Integer account_type);

    List<Map<String, Object>> queryMonthVisitorNum();

    List<Map<String, Object>> qrylocalVisitorList(@Param("place_code") String place_code);

    List<Map<String, Object>> qryotherPlaceVisitorList(@Param("place_code") String place_code);

    List<Map<String, Object>> querylastMonthVisitorList();

    IPage<TravelTeamVO> qryTravelTeamPage(IPage<TravelTeamVO> page,
                                          @Param("teamId") Long teamId,
                                          @Param("leadName")String leadName,
                                          @Param("refMch")String refMch,
                                          @Param("teamStatus")String teamStatus,
                                          @Param("beginDate")String beginDate,
                                          @Param("endDate")String endDate,
                                          @Param("userId")Long userId);
}
