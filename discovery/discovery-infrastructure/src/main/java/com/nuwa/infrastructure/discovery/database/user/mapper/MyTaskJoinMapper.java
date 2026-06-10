package com.nuwa.infrastructure.discovery.database.user.mapper;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.nuwa.framework.database.tk.join.mapper.JoinPagingMapper;
import com.nuwa.infrastructure.discovery.database.JoinSqlConstant;
import com.nuwa.infrastructure.discovery.database.user.mapper.query.UserMyTaskPageJoinQuery;
import com.nuwa.infrastructure.discovery.database.user.vo.MyTaskPageVO;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.Map;


/**
 * MyTaskJoinMapper 我的任务分页查询
 *
 * @author huyonghack@163.com
 * @since 2021-07-08
 */
@Repository
public interface MyTaskJoinMapper extends
        JoinPagingMapper<UserMyTaskPageJoinQuery, MyTaskPageVO> {

    String SQL = "select scenic_task.* from member_task_apply member_task_apply LEFT JOIN scenic_task scenic_task on member_task_apply.task_id = scenic_task.id   " +
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
    Page<MyTaskPageVO> page(IPage<?> page, @Param(Constants.WRAPPER) Wrapper<UserMyTaskPageJoinQuery> wrapper, Map<String, String> extMap);
}
