package com.nuwa.infrastructure.discovery.database.user.mapper;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.nuwa.framework.database.tk.join.mapper.JoinPagingMapper;
import com.nuwa.infrastructure.discovery.database.JoinSqlConstant;
import com.nuwa.infrastructure.discovery.database.user.mapper.query.TaskPrizeJoinPageQuery;
import com.nuwa.infrastructure.discovery.database.user.vo.UserTaskPrizePageVO;
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
public interface UserTaskPrizeJoinMapper extends
        JoinPagingMapper<TaskPrizeJoinPageQuery, UserTaskPrizePageVO> {

    String SQL = "SELECT " +
            "member_task_prize.id," +
            "member_task_prize.task_id," +
            "member_task_prize.prize_type_id," +
            "member_task_prize.submit_time," +
            "member_task_prize.ext_data," +
            "task_prize.prize_title," +
            "task_prize.prize_content," +
            "member_task_prize.create_time," +
            "member_task_prize.last_update_time," +
            "member.user_phone," +
            "member.user_id," +
            "task_prize_type.prize_type_name," +
            "member_task_prize.remark_text," +
            "member_task_prize.remark_pictures," +
            "member_task_prize.pictures," +
            "member_account_bind.nick," +
            "member_account_bind.account_id," +
            "scenic_task.`name` AS 'taskName'," +
            "member_task_prize.`status` ," +
            "member_task_apply.`member_tag`, " +
            "member_task_apply.`sex` ," +
            "member_task_apply.`region`, " +
            "member_task_apply.`user_level_id`,  " +
            "member_integral_level.`level_name` " +
            "FROM " +
            "member_task_prize member_task_prize " +
            "LEFT JOIN scenic_task scenic_task ON scenic_task.id = member_task_prize.task_id " +
            "LEFT JOIN member_task_apply member_task_apply ON member_task_apply.task_id = scenic_task.id  AND member_task_apply.user_id = member_task_prize.user_id " +
            "LEFT JOIN member_integral_level member_integral_level ON member_integral_level.level = member_task_apply.user_level_id " +
            "LEFT JOIN task_prize task_prize ON task_prize.id = member_task_prize.task_prize_id " +
            "LEFT JOIN task_prize_type task_prize_type ON task_prize_type.id = task_prize.prize_type " +
            "LEFT JOIN member_account_bind member_account_bind ON member_account_bind.channel_code = member_task_prize.platform_code " +
            "AND member_account_bind.user_id = member_task_prize.user_id " +
            "LEFT JOIN member member ON member_task_prize.user_id = member.user_id   " +
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
    Page<UserTaskPrizePageVO> page(IPage<?> page, @Param(Constants.WRAPPER) Wrapper<TaskPrizeJoinPageQuery> wrapper, Map<String, String> extMap);
}
