package com.nuwa.infrastructure.ticket.database.scenicspot.mapper;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.nuwa.framework.database.tk.join.mapper.JoinListMapper;
import com.nuwa.framework.database.tk.join.mapper.JoinPagingMapper;
import com.nuwa.infrastructure.ticket.database.scenicspot.JoinSqlConstant;
import com.nuwa.infrastructure.ticket.database.scenicspot.mapper.query.ListScenicspotByJoinQuery;
import com.nuwa.infrastructure.ticket.database.scenicspot.mapper.query.PageScenicspotJoinQuery;
import com.nuwa.infrastructure.ticket.database.scenicspot.vo.MerchantScenicspotPageVO;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;


/**
 * JoinAppInfoMapper 关联查询
 *
 * @author huyonghack@163.com
 * @since 2021-07-08
 */
@Repository
public interface ScenicspotJoinMapper extends
        JoinPagingMapper<PageScenicspotJoinQuery, MerchantScenicspotPageVO>
        , JoinListMapper<ListScenicspotByJoinQuery, MerchantScenicspotPageVO> {

    public static final String MERCHANT_SCENICSPOT_SQL = "SELECT distinct scenicspot.* from scenicspot  " +
            "LEFT JOIN scenicspot_type ON scenicspot.id = scenicspot_type.scenic_spot_id " +
            "LEFT JOIN scenicspot_label ON scenicspot.id = scenicspot_label.scenic_spot_id " +
            JoinSqlConstant.EW_CUSTOM_SQL_SEGMENT;

    @Override
    @Select(MERCHANT_SCENICSPOT_SQL)
    Page<MerchantScenicspotPageVO> page(IPage<?> page, @Param(Constants.WRAPPER) Wrapper<PageScenicspotJoinQuery> wrapper, Map<String, String> extMap);

    @Override
    @Select(MERCHANT_SCENICSPOT_SQL)
    List<MerchantScenicspotPageVO> list(@Param(Constants.WRAPPER) Wrapper<ListScenicspotByJoinQuery> wrapper);
}
