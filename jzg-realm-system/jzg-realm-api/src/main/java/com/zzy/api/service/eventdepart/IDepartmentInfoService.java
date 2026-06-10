package com.zzy.api.service.eventdepart;

import com.baomidou.mybatisplus.extension.service.IService;
import com.github.pagehelper.PageInfo;
import com.zzy.db.dto.DepartmentInfoMemberInfo;
import com.zzy.db.entity.eventdepart.DepartmentInfo;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * <p>
 * 部门表 服务类
 * </p>
 *
 * @author zzy
 * @since 2020-04-13
 */
public interface IDepartmentInfoService extends IService<DepartmentInfo> {

    List<Map<String,Object>> getAllDepartments();

    PageInfo<Map<String,Object>> getPageDepartments(Integer pageNo, Integer pageSize, Map<String, Object> para);

    boolean leaderSetWithLoginAccount(DepartmentInfo di, Integer departId, Integer memberId, String userName, String pwd);

    List<DepartmentInfoMemberInfo> getLeaderInfoByMemberId(Set<Integer> ids);
}
