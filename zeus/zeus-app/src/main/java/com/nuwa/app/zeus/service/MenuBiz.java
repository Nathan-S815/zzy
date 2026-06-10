package com.nuwa.app.zeus.service;

import com.nuwa.infrastructure.zeus.constant.AdminCommonConstant;
import com.nuwa.infrastructure.zeus.database.base.entity.BaseMenu;
import com.nuwa.infrastructure.zeus.database.base.mapper.BaseMenuMapper;
import com.nuwa.infrastructure.zeus.database.base.mapper.ext.BaseMapperExt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * MenuBiz
 *
 * @author hy
 * @date 2021/5/25 13:18
 * @since 1.0.0
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class MenuBiz {

    @Autowired
    private BaseMenuMapper baseMenuMapper;

    @Autowired
    private BaseMapperExt baseMapperExt;

    public void save(BaseMenu entity) {
        if (AdminCommonConstant.ROOT == entity.getParentId()) {
            entity.setPath("/" + entity.getCode());
        } else {
            BaseMenu parent = baseMenuMapper.selectById(entity.getParentId());
            entity.setPath(parent.getPath() + "/" + entity.getCode());
        }
        entity.insert();
    }

    public void updateById(BaseMenu entity) {
        if (AdminCommonConstant.ROOT == entity.getParentId()) {
            entity.setPath("/" + entity.getCode());
        } else {
            BaseMenu parent = baseMenuMapper.selectById(entity.getParentId());
            entity.setPath(parent.getPath() + "/" + entity.getCode());
        }
        entity.updateById();
    }

    /**
     * 获取用户可以访问的菜单
     *
     * @param id
     * @return
     */
    public List<BaseMenu> getUserAuthorityMenuByUserId(int id) {
        return baseMapperExt.selectAuthorityMenuByUserId(id);
    }

    /**
     * 根据用户获取可以访问的系统
     *
     * @param id
     * @return
     */
    public List<BaseMenu> getUserAuthoritySystemByUserId(int id) {
        return baseMapperExt.selectAuthoritySystemByUserId(id);
    }

}
