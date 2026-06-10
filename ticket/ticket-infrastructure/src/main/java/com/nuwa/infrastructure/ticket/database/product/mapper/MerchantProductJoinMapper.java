package com.nuwa.infrastructure.ticket.database.product.mapper;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.nuwa.framework.database.tk.join.mapper.JoinListMapper;
import com.nuwa.framework.database.tk.join.mapper.JoinPagingMapper;
import com.nuwa.infrastructure.ticket.database.product.mapper.query.PageByMerchantProductQuery;
import com.nuwa.infrastructure.ticket.database.product.vo.MerchantProductPageVO;
import com.nuwa.infrastructure.ticket.database.scenicspot.JoinSqlConstant;
import com.nuwa.infrastructure.ticket.database.scenicspot.mapper.query.ListByMerchantScenicspotByJoinQuery;
import com.nuwa.infrastructure.ticket.database.scenicspot.mapper.query.PageByMerchantScenicspotJoinQuery;
import com.nuwa.infrastructure.ticket.database.scenicspot.vo.MerchantScenicspotPageVO;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;


/**
 * MerchantProductJoinMapper 供应商产品分页查询
 *
 * @author huyonghack@163.com
 * @since 2021-07-08
 */
@Repository
public interface MerchantProductJoinMapper extends
        JoinPagingMapper<PageByMerchantProductQuery, MerchantProductPageVO> {

    String MERCHANT_SCENICSPOT_SQL = "SELECT " +
            "scenicspot_product.*," +
            "scenicspot.`name` as scenicspotName " +
            "FROM " +
            "scenicspot_product scenicspot_product " +
            "LEFT JOIN scenicspot scenicspot ON scenicspot_product.scenicspot_id = scenicspot.id  " +
            JoinSqlConstant.EW_CUSTOM_SQL_SEGMENT;

    @Override
    @Select(MERCHANT_SCENICSPOT_SQL)
    Page<MerchantProductPageVO> page(IPage<?> page, @Param(Constants.WRAPPER) Wrapper<PageByMerchantProductQuery> wrapper, Map<String, String> extMap);

}
