package com.nuwa.infrastructure.ticket.database.scenicspot.mapper;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.nuwa.framework.database.tk.join.mapper.JoinListMapper;
import com.nuwa.framework.database.tk.join.mapper.JoinPagingMapper;
import com.nuwa.infrastructure.ticket.database.scenicspot.JoinSqlConstant;
import com.nuwa.infrastructure.ticket.database.scenicspot.mapper.query.ListByMerchantScenicspotByJoinQuery;
import com.nuwa.infrastructure.ticket.database.scenicspot.mapper.query.PageByMerchantScenicspotJoinQuery;
import com.nuwa.infrastructure.ticket.database.scenicspot.mapper.query.PageByUserScenicspotJoinQuery;
import com.nuwa.infrastructure.ticket.database.scenicspot.vo.MerchantScenicspotPageVO;
import com.nuwa.infrastructure.ticket.database.scenicspot.vo.UserScenicspotPageVO;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;


/**
 * MerchantScenicspotJoinMapper 商户景区POI分页查询
 *
 * @author huyonghack@163.com
 * @since 2021-07-08
 */
@Repository
public interface UserScenicspotJoinMapper extends
        JoinPagingMapper<PageByUserScenicspotJoinQuery, UserScenicspotPageVO> {

    String MERCHANT_SCENICSPOT_SQL = "SELECT DISTINCT " +
            "scenicspot.id, " +
            "scenicspot.longitude, " +
            "scenicspot.latitude, " +
            "scenicspot.main_picture," +
            "merchant_scenicspot_poi.weight," +
            "scenicspot.price_min," +
            "scenicspot.NAME," +
            "scenicspot.city," +
            "scenicspot.grade," +
            "scenicspot.address," +
            "scenicspot.type_name," +
            "scenicspot.poi_type," +
            "scenicspot.salesman_telephone" +
            " FROM " +
            "merchant_scenicspot_poi merchant_scenicspot_poi " +
            "LEFT JOIN scenicspot ON scenicspot.id = merchant_scenicspot_poi.scenic_spot_id " +
            "LEFT JOIN scenicspot_type ON scenicspot.id = scenicspot_type.scenic_spot_id " +
            JoinSqlConstant.EW_CUSTOM_SQL_SEGMENT;

    /**
     * 分页查询
     *
     * @param page    页数
     * @param wrapper 查询条件
     * @param extMap  扩展参数
     * @return Page<UserScenicspotPageVO>
     */
    @Override
    @Select(MERCHANT_SCENICSPOT_SQL)
    Page<UserScenicspotPageVO> page(IPage<?> page, @Param(Constants.WRAPPER) Wrapper<PageByUserScenicspotJoinQuery> wrapper, Map<String, String> extMap);

}
