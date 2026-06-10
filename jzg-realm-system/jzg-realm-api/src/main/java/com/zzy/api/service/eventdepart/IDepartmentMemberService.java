package com.zzy.api.service.eventdepart;

import com.baomidou.mybatisplus.extension.service.IService;
import com.github.pagehelper.PageInfo;
import com.zzy.db.entity.eventdepart.DepartmentMember;
import com.zzy.security.lib.entity.UserInfo;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 部员表 服务类
 * </p>
 *
 * @author zzy
 * @since 2020-04-13
 */
public interface IDepartmentMemberService extends IService<DepartmentMember> {

    List<Map<String,Object>> getAllMemberByDepartId(Integer departmentId);

    PageInfo<Map<String,Object>> getPageDepartmentMembers(Integer pageNo, Integer pageSize, Map<String, Object> para);

    boolean batchAddMemberLoginUsers(List<UserInfo> list, int roleId);

    boolean updateLoginEnableById(int isLoginEnable, List<Integer> ids);
}
