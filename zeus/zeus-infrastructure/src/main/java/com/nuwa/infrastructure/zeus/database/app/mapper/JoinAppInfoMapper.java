package com.nuwa.infrastructure.zeus.database.app.mapper;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.nuwa.framework.database.tk.join.mapper.JoinListMapper;
import com.nuwa.framework.database.tk.join.mapper.JoinPagingMapper;
import com.nuwa.infrastructure.zeus.database.JoinSqlConstant;
import com.nuwa.infrastructure.zeus.database.app.entity.MerchantAppPageVO;
import com.nuwa.infrastructure.zeus.database.app.query.ListAppByJoinQuery;
import com.nuwa.infrastructure.zeus.database.app.query.PageAppJoinQuery;
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
public interface JoinAppInfoMapper extends
        JoinPagingMapper<PageAppJoinQuery, MerchantAppPageVO>
        , JoinListMapper<ListAppByJoinQuery, MerchantAppPageVO> {

    public static final String MERCHANT_APP_JOIN_APP_INFO_SQL = "SELECT merchant_app.*,app_info.logo from merchant_app LEFT JOIN app_info  ON merchant_app.app_id = app_info.id" +
            JoinSqlConstant.EW_CUSTOM_SQL_SEGMENT;

    @Override
    @Select(MERCHANT_APP_JOIN_APP_INFO_SQL)
    Page<MerchantAppPageVO> page(IPage<?> page, @Param(Constants.WRAPPER) Wrapper<PageAppJoinQuery> wrapper, Map<String, String> extMap);

    @Override
    @Select(MERCHANT_APP_JOIN_APP_INFO_SQL)
    List<MerchantAppPageVO> list(@Param(Constants.WRAPPER) Wrapper<ListAppByJoinQuery> wrapper);
}
