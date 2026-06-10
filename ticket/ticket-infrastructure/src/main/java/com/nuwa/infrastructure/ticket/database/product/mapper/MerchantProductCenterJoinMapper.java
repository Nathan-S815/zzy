package com.nuwa.infrastructure.ticket.database.product.mapper;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.nuwa.framework.database.tk.join.mapper.JoinPagingMapper;
import com.nuwa.infrastructure.ticket.database.product.mapper.query.PageByMerchantProductCenterQuery;
import com.nuwa.infrastructure.ticket.database.product.mapper.query.PageByMerchantProductQuery;
import com.nuwa.infrastructure.ticket.database.product.vo.MerchantProductPageVO;
import com.nuwa.infrastructure.ticket.database.scenicspot.JoinSqlConstant;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.Map;


/**
 * MerchantProductCenterJoinMapper 商户产品中心分页查询
 *
 * @author huyonghack@163.com
 * @since 2021-07-08
 */
@Repository
public interface MerchantProductCenterJoinMapper extends
        JoinPagingMapper<PageByMerchantProductCenterQuery, MerchantProductPageVO> {

    String MERCHANT_SCENICSPOT_SQL = "SELECT" +
            " scenicspot_product.*," +
            " scenicspot.`name` AS scenicspotName," +
            " merchant_product_distribute.id as 'productDistributeId', " +
            " merchant_product_distribute.audit_status as 'auditStatus' " +
            "FROM" +
            " scenicspot_product scenicspot_product" +
            " LEFT JOIN merchant_product_distribute merchant_product_distribute ON ( scenicspot_product.id = merchant_product_distribute.product_id AND merchant_product_distribute.distribute_merchant_id = ${extMap.distributeMerchantId} )" +
            " LEFT JOIN scenicspot scenicspot ON scenicspot_product.scenicspot_id = scenicspot.id  " +
            JoinSqlConstant.EW_CUSTOM_SQL_SEGMENT;

    /**
     * 分页查询
     *
     * @param page    IPage
     * @param wrapper PageByMerchantProductCenterQuery
     * @param extMap  Map<String, String>
     * @return Page<MerchantProductPageVO>
     */
    @Override
    @Select(MERCHANT_SCENICSPOT_SQL)
    Page<MerchantProductPageVO> page(IPage<?> page, @Param(Constants.WRAPPER) Wrapper<PageByMerchantProductCenterQuery> wrapper, Map<String, String> extMap);

}
