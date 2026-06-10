package com.zzy.security.service;


import com.zzy.security.dto.SysMenuDto;
import com.zzy.security.lib.dao.SysMenuMapper;
import com.zzy.security.lib.dao.SysRoleMenuMapper;
import com.zzy.security.lib.entity.SysMenu;
import com.zzy.security.lib.entity.SysRoleMenu;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;


@Service
public class MenuService {


    @Autowired
    private SysMenuMapper sysMenuMapper;

    @Autowired
    private SysRoleMenuMapper sysRoleMenuMapper;


    public List<SysMenuDto> listByUserId(Map<String, Object> m) {
        return sysMenuMapper.selectListByUserId(m);
    }

    public boolean addMenuInfo(SysMenu sm) {
        return sysMenuMapper.insertSelective(sm)>0;
    }

    public boolean batchSaveRoleMenu(List<SysRoleMenu> li) {
        return sysRoleMenuMapper.batchInsert(li)>0;
    }

    public boolean removeMenuByRoleMenuId(Map<String, Object> m) {
        return sysRoleMenuMapper.deleteByMenuIdsAndRoleId(m)>0;
    }

    public boolean editMenuInfo(SysMenu sm) {
        return sysMenuMapper.updateByPrimaryKeySelective(sm)>0;
    }

    public List<SysMenuDto> listAll(Map<String, Object> map) {
        return sysMenuMapper.selectAllList(map);
    }

    public boolean updateByPkId(SysMenu sm) {
        return sysMenuMapper.updateByPrimaryKeySelective(sm)>0;
    }

    public SysMenu getOneByPkId(Integer menuId) {
        return sysMenuMapper.selectByPrimaryKey(menuId);
    }

    public List<SysMenu> getMenuChildByParentId(Integer menuId) {
        return sysMenuMapper.selectMenuChildByParentId(menuId);

    }
}
