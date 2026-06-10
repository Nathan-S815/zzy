package com.nuwa.infrastructure.ticket.database.product.mapper;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.nuwa.framework.database.tk.join.mapper.JoinPagingMapper;
import com.nuwa.infrastructure.ticket.database.product.mapper.query.PageByUserProductQuery;
import com.nuwa.infrastructure.ticket.database.product.vo.UserProductPageVO;
import com.nuwa.infrastructure.ticket.database.scenicspot.JoinSqlConstant;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.Map;


/**
 * UserProductJoinMapper C端产品分页查询
 *
 * @author huyonghack@163.com
 * @since 2021-07-08
 */
@Repository
public interface UserProductJoinMapper extends
        JoinPagingMapper<PageByUserProductQuery, UserProductPageVO> {

    String MERCHANT_SCENICSPOT_SQL = "SELECT" +
            " scenicspot_product.id," +
            " scenicspot_product.NAME," +
            " scenicspot_product.price," +
            " scenicspot_product.sell_number," +
            " scenicspot_product_refund_rule_config.refund_mode," +
            " scenicspot_product_verification_config.entrance_mode " +
            "FROM" +
            " merchant_product_distribute merchant_product_distribute" +
            " LEFT JOIN scenicspot_product scenicspot_product ON merchant_product_distribute.product_id = scenicspot_product.id" +
            " LEFT JOIN scenicspot_product_refund_rule_config scenicspot_product_refund_rule_config ON scenicspot_product.id = scenicspot_product_refund_rule_config.scenicspot_product_id " +
            " AND scenicspot_product.version = scenicspot_product_refund_rule_config.version" +
            " LEFT JOIN scenicspot_product_verification_config scenicspot_product_verification_config ON scenicspot_product.id = scenicspot_product_verification_config.scenicspot_product_id " +
            " AND scenicspot_product.version = scenicspot_product_verification_config.version " +
            JoinSqlConstant.EW_CUSTOM_SQL_SEGMENT;

    /**
     * 产品分页查询
     *
     * @param page    页数
     * @param wrapper 条件
     * @param extMap  扩展参数
     * @return Page<UserProductPageVO>
     */
    @Override
    @Select(MERCHANT_SCENICSPOT_SQL)
    Page<UserProductPageVO> page(IPage<?> page, @Param(Constants.WRAPPER) Wrapper<PageByUserProductQuery> wrapper, Map<String, String> extMap);
}
