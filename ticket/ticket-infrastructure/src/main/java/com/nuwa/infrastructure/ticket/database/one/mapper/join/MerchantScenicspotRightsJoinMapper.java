package com.nuwa.infrastructure.ticket.database.one.mapper.join;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.nuwa.framework.database.tk.join.mapper.JoinPagingMapper;
import com.nuwa.infrastructure.ticket.database.one.mapper.join.query.MerchantScenicsportRightsJoinPageJoinQuery;
import com.nuwa.infrastructure.ticket.database.one.mapper.vo.MerchantScenicsportRightsPageVO;
import com.nuwa.infrastructure.ticket.database.scenicspot.JoinSqlConstant;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.Map;


/**
 * MerchantScenicspotRightsJoinMapper 商户景区权益分页查询
 *
 * @author huyonghack@163.com
 * @since 2021-07-08
 */
@Repository
public interface MerchantScenicspotRightsJoinMapper extends
        JoinPagingMapper<MerchantScenicsportRightsJoinPageJoinQuery, MerchantScenicsportRightsPageVO> {

    String SQL = "SELECT " +
            "  one_rights_conf.*,one_merchant_scenicspot_rights.id as 'scenicspotRightsId',one_merchant_scenicspot_rights.scenicspot_id as 'mch_scenicspot_id' " +
            "FROM " +
            "  one_merchant_scenicspot_rights one_merchant_scenicspot_rights " +
            "  LEFT JOIN one_rights_conf one_rights_conf ON one_merchant_scenicspot_rights.rights_id = one_rights_conf.id " +
            JoinSqlConstant.EW_CUSTOM_SQL_SEGMENT;


    /**
     * 分页查询
     *
     * @param page    分页参数
     * @param wrapper 查询条件
     * @param extMap  扩展参数
     * @return Page<MerchantScenicsportRightsPageVO>
     */
    @Override
    @Select(SQL)
    Page<MerchantScenicsportRightsPageVO> page(IPage<?> page, @Param(Constants.WRAPPER) Wrapper<MerchantScenicsportRightsJoinPageJoinQuery> wrapper, Map<String, String> extMap);

}
