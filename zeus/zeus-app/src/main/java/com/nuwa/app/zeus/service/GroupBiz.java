package com.nuwa.app.zeus.service;

import com.alibaba.cola.exception.Assert;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.nuwa.app.zeus.service.dto.ModifyMenuAuthorityDTO;
import com.nuwa.app.zeus.vo.AuthorityMenuTree;
import com.nuwa.app.zeus.vo.GroupUsers;
import com.nuwa.infrastructure.zeus.constant.AdminCommonConstant;
import com.nuwa.infrastructure.zeus.database.base.entity.*;
import com.nuwa.infrastructure.zeus.database.base.mapper.*;
import com.nuwa.infrastructure.zeus.database.base.mapper.ext.BaseMapperExt;
import com.nuwa.infrastructure.zeus.database.base.service.BaseGroupService;
import com.nuwa.infrastructure.zeus.database.base.service.BaseMenuService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.*;
import java.util.stream.Collectors;

/**
 * GroupBiz
 *
 * @author hy
 * @date 2021/5/25 9:32
 * @since 1.0.0
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class GroupBiz {
    @Autowired
    private BaseUserMapper baseUserMapper;

    @Autowired
    private BaseResourceAuthorityMapper baseResourceAuthorityMapper;

    @Autowired
    private BaseMenuMapper baseMenuMapper;

    @Autowired
    private BaseGroupMapper baseGroupMapper;

    @Autowired
    private BaseGroupLeaderMapper baseGroupLeaderMapper;

    @Autowired
    private BaseGroupMemberMapper baseGroupMemberMapper;

    @Autowired
    private BaseMapperExt baseMapperExt;

    @Autowired
    private BaseMenuService baseMenuService;

    public void save(BaseGroup entity) {
        if (AdminCommonConstant.ROOT == entity.getParentId()) {
            entity.setPath("/" + entity.getCode());
        } else {
            BaseGroup parent = baseGroupMapper.selectById(entity.getParentId());
            entity.setPath(parent.getPath() + "/" + entity.getCode());
        }
        boolean insertGroup = entity.insert();
        Assert.isTrue(insertGroup, "save BaseGroup is failed");

        BaseGroupLeader baseGroupLeader = new BaseGroupLeader();
        baseGroupLeader.setGroupId(entity.getId() + "");
        baseGroupLeader.setUserId(entity.getCreateUserId());
        baseGroupLeader.setCreateUserId(entity.getCreateUserId());
        baseGroupLeader.setCreateUserName(entity.getCreateUserName());
        baseGroupLeader.setCreateTime(new Date());
        baseGroupLeader.setCreateHost(entity.getCreateHost());
        boolean insertGroupLeader = baseGroupLeader.insert();
        Assert.isTrue(insertGroupLeader, "save BaseGroupLeader is failed");
    }

    public void modify(BaseGroup entity) {
        if (AdminCommonConstant.ROOT == entity.getParentId()) {
            entity.setPath("/" + entity.getCode());
        } else {
            BaseGroup parent = baseGroupMapper.selectById(entity.getParentId());
            entity.setPath(parent.getPath() + "/" + entity.getCode());
        }
        baseGroupMapper.updateById(entity);
    }

    /**
     * 获取群组关联用户
     *
     * @param groupId
     * @return
     */
    public GroupUsers getGroupUsers(int groupId) {
        return new GroupUsers(baseMapperExt.selectMemberByGroupId(groupId), baseMapperExt.selectLeaderByGroupId(groupId));
    }

    /**
     * 变更群主所分配用户
     *
     * @param groupId
     * @param members
     * @param leaders
     */
    public void modifyGroupUsers(int groupId, String members, String leaders) {
        QueryWrapper<BaseGroupMember> queryGroup = Wrappers.<BaseGroupMember>query();
        queryGroup.eq(BaseGroupMember.GROUP_ID, groupId);
        baseGroupMemberMapper.delete(queryGroup);

        QueryWrapper<BaseGroupLeader> queryGroupLeader = Wrappers.<BaseGroupLeader>query();
        queryGroupLeader.eq(BaseGroupMember.GROUP_ID, groupId);
        baseGroupLeaderMapper.delete(queryGroupLeader);

        if (!StringUtils.isEmpty(members)) {
            String[] mem = members.split(",");
            for (String m : mem) {
                BaseGroupMember groupMember = new BaseGroupMember();
                groupMember.setGroupId(groupId + "");
                groupMember.setUserId(m);
                groupMember.insert();
            }
        }
        if (!StringUtils.isEmpty(leaders)) {
            String[] mem = leaders.split(",");
            for (String m : mem) {
                // mapper.insertGroupLeadersById(groupId, Integer.parseInt(m));
                BaseGroupLeader groupLeader = new BaseGroupLeader();
                groupLeader.setGroupId(groupId + "");
                groupLeader.setUserId(m);
                groupLeader.insert();
            }
        }
    }

    /**
     * 变更群组关联的菜单
     *
     * @param groupId
     * @param menus
     */
    @Transactional(rollbackFor = Exception.class)
    public void modifyAuthorityMenu(int groupId, ModifyMenuAuthorityDTO dto) {
        baseResourceAuthorityMapper.delete(Wrappers.<BaseResourceAuthority>query()
                .eq(BaseResourceAuthority.AUTHORITY_ID, groupId)
                .eq(BaseResourceAuthority.RESOURCE_TYPE, AdminCommonConstant.RESOURCE_TYPE_MENU));

        List<BaseMenu> menuList = baseMenuMapper.selectList((Wrappers.<BaseMenu>query()));
        Map<String, String> map = new HashMap<String, String>();
        for (BaseMenu menu : menuList) {
            map.put(menu.getId().toString(), menu.getParentId().toString());
        }

        dto.getMenus().forEach(x -> {
            BaseResourceAuthority authority = new BaseResourceAuthority(AdminCommonConstant.AUTHORITY_TYPE_GROUP, AdminCommonConstant.RESOURCE_TYPE_MENU);
            authority.setAuthorityId(groupId + "");
            authority.setResourceId(x.getMenuId());
            authority.setParentId(Long.parseLong(x.getParentMenuId()));
            authority.insert();
        });
    }

    /**
     * 分配资源权限
     *
     * @param groupId
     * @param menuId
     * @param elementId
     */
    public void modifyAuthorityElement(int groupId, int menuId, int elementId) {
        BaseResourceAuthority authority = new BaseResourceAuthority(AdminCommonConstant.AUTHORITY_TYPE_GROUP, AdminCommonConstant.RESOURCE_TYPE_BTN);
        authority.setAuthorityId(groupId + "");
        authority.setResourceId(elementId + "");
        authority.setParentId(-1L);
        authority.insert();
    }

    /**
     * 移除资源权限
     *
     * @param groupId
     * @param menuId
     * @param elementId
     */
    public void removeAuthorityElement(int groupId, int menuId, int elementId) {
        baseResourceAuthorityMapper.delete(Wrappers.<BaseResourceAuthority>query()
                .eq(BaseResourceAuthority.AUTHORITY_ID, groupId)
                .eq(BaseResourceAuthority.RESOURCE_ID, elementId)
                .eq(BaseResourceAuthority.PARENT_ID, -1L));
    }


    /**
     * 获取App关联的菜单
     *
     * @param appId appId
     * @return List<AuthorityMenuTree>
     */
    public List<AuthorityMenuTree> getAppAuthorityMenu(int appId) {
        List<BaseMenu> menus = baseMenuService.lambdaQuery()
                .eq(BaseMenu::getAppId, appId)
                .orderByAsc(BaseMenu::getOrderNum)
                .list();
        List<AuthorityMenuTree> trees = new ArrayList<AuthorityMenuTree>();
        AuthorityMenuTree node = null;
        for (BaseMenu menu : menus) {
            node = new AuthorityMenuTree();
            node.setText(menu.getTitle());
            BeanUtils.copyProperties(menu, node);
            trees.add(node);
        }
        return trees.stream().sorted(Comparator.comparing(AuthorityMenuTree::getOrderNum)).collect(Collectors.toList());
    }

    /**
     * 获取群主关联的菜单
     *
     * @param groupId
     * @return
     */
    public List<AuthorityMenuTree> getAuthorityMenu(int groupId) {
        List<BaseMenu> menus = baseMapperExt.selectMenuByAuthorityId(String.valueOf(groupId), AdminCommonConstant.AUTHORITY_TYPE_GROUP);
        List<AuthorityMenuTree> trees = new ArrayList<AuthorityMenuTree>();
        AuthorityMenuTree node = null;
        for (BaseMenu menu : menus) {
            node = new AuthorityMenuTree();
            node.setText(menu.getTitle());
            BeanUtils.copyProperties(menu, node);
            trees.add(node);
        }
        return trees;
    }

    /**
     * 获取群组关联的资源
     *
     * @param groupId
     * @return
     */
    public List<Integer> getAuthorityElement(int groupId) {
        List<BaseResourceAuthority> authorities = baseResourceAuthorityMapper.selectList(Wrappers.<BaseResourceAuthority>query()
                .eq(BaseResourceAuthority.AUTHORITY_ID, groupId)
                .eq(BaseResourceAuthority.RESOURCE_TYPE, AdminCommonConstant.RESOURCE_TYPE_BTN)
                .eq(BaseResourceAuthority.AUTHORITY_TYPE, AdminCommonConstant.AUTHORITY_TYPE_GROUP));
        List<Integer> ids = new ArrayList<Integer>();
        for (BaseResourceAuthority auth : authorities) {
            ids.add(Integer.parseInt(auth.getResourceId()));
        }
        return ids;
    }

    private void findParentID(Map<String, String> map, Set<String> relationMenus, String id) {
        String parentId = map.get(id);
        if (String.valueOf(AdminCommonConstant.ROOT).equals(id)) {
            return;
        }
        relationMenus.add(parentId);
        findParentID(map, relationMenus, parentId);
    }
}
