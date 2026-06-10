package com.nuwa.infrastructure.discovery.database.task.mapper;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.nuwa.framework.database.supper.SuperMapper;
import com.nuwa.infrastructure.discovery.database.task.entity.ScenicTask;

import com.nuwa.infrastructure.discovery.database.user.vo.MyTaskVO;
import com.nuwa.infrastructure.discovery.database.user.vo.NewTaskVO;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;


/**
 * 景区任务表 Mapper 接口
 *
 * @author huyonghack@163.com
 * @since 2021-11-29
 */
@Repository
public interface ScenicTaskMapper extends SuperMapper<ScenicTask> {


    IPage<NewTaskVO> selectNewTaskList(IPage<NewTaskVO> page,@Param("mchId") String mchId);


    IPage<MyTaskVO> getMyPublishTaskList(IPage<MyTaskVO> page,@Param("userId") Long userId
                                                             ,@Param("sourceCode") Integer sourceCode);
}
