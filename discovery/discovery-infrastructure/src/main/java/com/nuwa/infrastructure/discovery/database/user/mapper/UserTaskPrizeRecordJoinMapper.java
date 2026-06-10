package com.nuwa.infrastructure.discovery.database.user.mapper;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.nuwa.framework.database.tk.join.mapper.JoinPagingMapper;
import com.nuwa.infrastructure.discovery.database.JoinSqlConstant;
import com.nuwa.infrastructure.discovery.database.user.mapper.query.TaskPrizeJoinPageQuery;
import com.nuwa.infrastructure.discovery.database.user.mapper.query.TaskPrizeRecordJoinPageQuery;
import com.nuwa.infrastructure.discovery.database.user.vo.UserTaskPrizePageVO;
import com.nuwa.infrastructure.discovery.database.user.vo.UserTaskPrizeRecordPageVO;
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
public interface UserTaskPrizeRecordJoinMapper extends
        JoinPagingMapper<TaskPrizeRecordJoinPageQuery, UserTaskPrizeRecordPageVO> {

    String SQL = "SELECT    " +
            "member_task_prize_record.user_id," +
            "member_task_prize_record.id," +
            "member_task_prize_record.prize_type_id," +
            "member_task_prize_record.ext_data," +
            "task_prize.prize_title," +
            "member_task_prize_record.create_time," +
            "member_task_prize_record.submit_time," +
            "member_task_prize_record.audit_time AS 'auditTime'," +
            "member.user_phone," +
            "member_task_prize_record.prize_title AS 'prizeTitle'," +
            "member_task_prize_record.remark_text," +
            "member_task_prize_record.remark_pictures," +
            "member_account_bind.nick," +
            "scenic_task.`name` AS 'taskName'," +
            "member_task_prize_record.`status` " +
            "member_account_bind.`weixin_id` " +
            "FROM   " +
            "member_task_prize_record member_task_prize_record  " +
            "LEFT JOIN scenic_task scenic_task ON scenic_task.id = member_task_prize_record.task_id " +
            "LEFT JOIN task_prize task_prize ON task_prize.id = member_task_prize_record.task_prize_id  " +
            "LEFT JOIN task_prize_type task_prize_type ON task_prize_type.id = task_prize.prize_type    " +
            "LEFT JOIN member_account_bind member_account_bind ON member_account_bind.channel_code = member_task_prize_record.platform_code " +
            "AND member_account_bind.user_id = member_task_prize_record.user_id " +
            "LEFT JOIN member member ON member_task_prize_record.user_id = member.user_id  " +
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
    Page<UserTaskPrizeRecordPageVO> page(IPage<?> page, @Param(Constants.WRAPPER) Wrapper<TaskPrizeRecordJoinPageQuery> wrapper, Map<String, String> extMap);
}
