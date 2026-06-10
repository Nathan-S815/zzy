package com.zzy.db.dao.eventdepart;

import com.zzy.db.dto.DepartmentInfoMemberInfo;
import com.zzy.db.entity.eventdepart.DepartmentInfo;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * <p>
 * 部门表 Mapper 接口
 * </p>
 *
 * @author zzy
 * @since 2020-04-13
 */
public interface DepartmentInfoMapper extends BaseMapper<DepartmentInfo> {

    List<Map<String, Object>> selectAllDepartments();

    List<Map<String, Object>> selectPageList(Map<String, Object> para);

    List<DepartmentInfoMemberInfo> selectleaderInfoByMemberIds(@Param("ids") Set<Integer> ids);
}
