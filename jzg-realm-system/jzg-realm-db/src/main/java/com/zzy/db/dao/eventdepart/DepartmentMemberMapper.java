package com.zzy.db.dao.eventdepart;

import com.zzy.db.entity.eventdepart.DepartmentMember;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 部员表 Mapper 接口
 * </p>
 *
 * @author zzy
 * @since 2020-04-13
 */
public interface DepartmentMemberMapper extends BaseMapper<DepartmentMember> {

    List<Map<String, Object>> selectAllMemeberByDepartId(Integer departmentId);

    List<Map<String, Object>> selectPageMembers(Map<String, Object> para);

    int updateLoginEnableById(Map<String, Object> m);
}
