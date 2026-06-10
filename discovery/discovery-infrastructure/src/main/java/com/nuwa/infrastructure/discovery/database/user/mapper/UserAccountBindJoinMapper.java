package com.nuwa.infrastructure.discovery.database.user.mapper;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.nuwa.framework.database.tk.join.mapper.JoinPagingMapper;
import com.nuwa.infrastructure.discovery.database.JoinSqlConstant;
import com.nuwa.infrastructure.discovery.database.user.mapper.query.AccountBindJoinPageQuery;
import com.nuwa.infrastructure.discovery.database.user.vo.UserAccountBindPageVO;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.Map;


/**
 * UserAccountBindJoinMapper 达人认证分页查询
 *
 * @author huyonghack@163.com
 * @since 2021-07-08
 */
@Repository
public interface UserAccountBindJoinMapper extends
        JoinPagingMapper<AccountBindJoinPageQuery, UserAccountBindPageVO> {

    String SQL = "SELECT member_account_bind.*,member.user_phone FROM  member_account_bind LEFT JOIN member member on member_account_bind.user_id = member.user_id   " +
            JoinSqlConstant.EW_CUSTOM_SQL_SEGMENT;

    /**
     * 分页查询
     *
     * @param page    分页参数
     * @param wrapper Wrapper<PageByAccountBindJoinQuery>
     * @param extMap  扩展参数
     * @return Page<UserTaskApplyPageVO>
     */
    @Override
    @Select(SQL)
    Page<UserAccountBindPageVO> page(IPage<?> page, @Param(Constants.WRAPPER) Wrapper<AccountBindJoinPageQuery> wrapper, Map<String, String> extMap);
}
