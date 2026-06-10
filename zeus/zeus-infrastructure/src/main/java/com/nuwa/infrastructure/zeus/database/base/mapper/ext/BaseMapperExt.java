package com.nuwa.infrastructure.zeus.database.base.mapper.ext;

import com.nuwa.infrastructure.zeus.database.app.entity.AppInfo;
import com.nuwa.infrastructure.zeus.database.base.entity.BaseElement;
import com.nuwa.infrastructure.zeus.database.base.entity.BaseMenu;
import com.nuwa.infrastructure.zeus.database.base.entity.BaseResourceAuthority;
import com.nuwa.infrastructure.zeus.database.base.entity.BaseUser;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public interface BaseMapperExt {
    public List<BaseUser> selectMemberByGroupId(@Param("groupId") int groupId);

    public List<BaseUser> selectLeaderByGroupId(@Param("groupId") int groupId);

    public List<BaseMenu> selectMenuByAuthorityId(@Param("authorityId") String authorityId, @Param("authorityType") String authorityType);


    /**
     * 根据用户和组的权限关系查找用户可访问菜单
     *
     * @param userId
     * @return
     */
    public List<BaseMenu> selectAuthorityMenuByUserId(@Param("userId") int userId);

    /**
     * 获取用户指定分组的菜单列表[主账号使用]
     * @param userId 用户id
     * @param groupId 分组id
     * @return
     */
    public List<BaseMenu> selectUserAuthorityMenuByUserIdAndGroupId(@Param("userId") int userId,@Param("groupId") int groupId);

    /**
     * 获取指定用户指定应用菜单列表[子账号使用]
     * @param userId 用户id
     * @param appId 应用id
     * @return
     */
    public List<BaseMenu> selectUserAuthorityMenuByUserIdAndAppId(@Param("userId") int userId,@Param("appId") int appId);

    /**
     * 根据用户和组的权限关系查找用户可访问的系统
     *
     * @param userId
     * @return
     */
    public List<BaseMenu> selectAuthoritySystemByUserId(@Param("userId") int userId);

    /**
     * 根据用户id获取指定用户的权限列表[主账号使用]
     * @param userId
     * @return
     */
    public List<BaseElement> selectAuthorityElementByUserId(@Param("userId") String userId);

    /**
     * 根据用户id获取指定用户的权限列表[子账号使用]
     * @param userId
     * @return
     */
    public List<BaseElement> selectAuthorityElementByUserIdAndAuthUser(@Param("userId") String userId);

    public List<BaseElement> selectAuthorityMenuElementByUserId(@Param("userId") String userId, @Param("menuId") String menuId);

    public List<BaseElement> selectAuthorityMenuElementByMenuId(@Param("menuId") String menuId);

    public List<BaseElement> selectAllElementPermissions();

    public List<Map<String, Object>> getAppTree(@Param("merchantId") Long merchantId);


    public List<AppInfo> selectUserRootApps(@Param("userId") Long userId);

    /**
     * 获取用户核心系统列表
     * @param userId
     * @return
     */
    public List<AppInfo> selectUserCoreRootApps(@Param("userId") Long userId);

    public List<AppInfo> selectUserSubApps(@Param("userId") Long userId, @Param("parentGroupId") Long parentGroupId);

    public List<BaseResourceAuthority> listAuthorityByAppId(@Param("appId") Long appId);

    public List<Map<String, Object>> selectMerchantChildApps(@Param("userId") Long userId);
}


