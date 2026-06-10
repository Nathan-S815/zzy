package com.nuwa.infrastructure.ticket.database.product.mapper;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.nuwa.framework.database.tk.join.mapper.JoinPagingMapper;
import com.nuwa.infrastructure.ticket.database.product.mapper.query.PageByMerchantProductCenterQuery;
import com.nuwa.infrastructure.ticket.database.product.mapper.query.PageByMerchantProductDistributeQuery;
import com.nuwa.infrastructure.ticket.database.product.vo.MerchantProductDistributePageVO;
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
public interface MerchantProductDistributeJoinMapper extends
        JoinPagingMapper<PageByMerchantProductDistributeQuery, MerchantProductDistributePageVO> {

    String MERCHANT_SCENICSPOT_SQL = "SELECT " +
            "  scenicspot_product.id, " +
            "  merchant_product_distribute.id as 'productDistributeId', " +
            "  merchant_product_distribute.create_time, " +
            "  merchant_product_distribute.reject_reason, " +
            "  merchant_product_distribute.distribute_merchant_id, " +
            "  merchant_product_distribute.supplier_merchant_id, " +
            "  merchant_product_distribute.supplier_merchant_name, " +
            "  merchant_product_distribute.publish_status, " +
            "  merchant_product_distribute.publish_time, " +
            "  merchant_product_distribute.audit_status, " +
            "  merchant_product_distribute.weight, " +
            "  scenicspot_product.status as 'supplierPublishStatus', " +
            "  scenicspot_product.scenicspot_id, " +
            "  scenicspot_product.`name`, " +
            "  scenicspot_product.sell_order, " +
            "  scenicspot.province, " +
            "  scenicspot.city, " +
            "  scenicspot.area, " +
            "  scenicspot.`name` AS scenicspotName  " +
            "FROM " +
            "  merchant_product_distribute merchant_product_distribute " +
            "  LEFT JOIN scenicspot_product scenicspot_product ON merchant_product_distribute.product_id = scenicspot_product.id " +
            "  LEFT JOIN scenicspot scenicspot ON scenicspot_product.scenicspot_id = scenicspot.id  " +
            JoinSqlConstant.EW_CUSTOM_SQL_SEGMENT;

    /**
     * 分页查询
     *
     * @param page    IPage
     * @param wrapper PageByMerchantProductDistributeQuery
     * @param extMap  Map<String, String>
     * @return Page<MerchantProductPageVO>
     */
    @Override
    @Select(MERCHANT_SCENICSPOT_SQL)
    Page<MerchantProductDistributePageVO> page(IPage<?> page, @Param(Constants.WRAPPER) Wrapper<PageByMerchantProductDistributeQuery> wrapper, Map<String, String> extMap);

}
