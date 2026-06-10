package com.nuwa.infrastructure.discovery.database.user.mapper;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.nuwa.framework.database.tk.join.mapper.JoinPagingMapper;
import com.nuwa.infrastructure.discovery.database.JoinSqlConstant;
import com.nuwa.infrastructure.discovery.database.user.mapper.query.UserTaskJoinPageQuery;
import com.nuwa.infrastructure.discovery.database.user.vo.UserTaskApplyPageVO;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.Map;


/**
 * UserTaskApplyJoinMapper 任务报名记录分页查询
 *
 * @author huyonghack@163.com
 * @since 2021-07-08
 */
@Repository
public interface UserTaskApplyJoinMapper extends
        JoinPagingMapper<UserTaskJoinPageQuery, UserTaskApplyPageVO> {

    String SQL = "SELECT " +
            "member_account_bind.user_id," +
            "member.user_img," +
            "member_account_bind.account_id," +
            "member_account_bind.nick," +
            "member.user_nike as 'userNick'," +
            "member_account_bind.`level`," +
            "member_account_bind.fans_count," +
            "member_task_apply.sex," +
            "member_task_apply.member_tag as 'memberTag'," +
            "member_task_apply.user_level_id as 'userLevelId'," +
            "member_integral_level.level_name as 'levelName'," +
            "member_task_apply.region," +
            "member_task_apply.video," +
            "member_task_apply.create_time AS 'applyTime' " +
            " FROM " +
            "member_task_apply member_task_apply    " +
            "LEFT JOIN scenic_task scenic_task ON member_task_apply.task_id = scenic_task.id    " +
            "LEFT JOIN member member ON member.user_id = member_task_apply.user_id    " +
            "LEFT JOIN member_integral_level member_integral_level ON member_integral_level.level = member_task_apply.user_level_id    " +
            "LEFT JOIN member_account_bind member_account_bind ON scenic_task.platform_code = member_account_bind.channel_code  AND member_account_bind.user_id = member_task_apply.user_id " +
            JoinSqlConstant.EW_CUSTOM_SQL_SEGMENT;

    /**
     * 分页查询
     *
     * @param page    分页参数
     * @param wrapper Wrapper<PageByUserTaskJoinQuery>
     * @param extMap  扩展参数
     * @return Page<UserTaskApplyPageVO>
     */
    @Override
    @Select(SQL)
    Page<UserTaskApplyPageVO> page(IPage<?> page, @Param(Constants.WRAPPER) Wrapper<UserTaskJoinPageQuery> wrapper, Map<String, String> extMap);
}
