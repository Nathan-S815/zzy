package com.zzy.api.service.base.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.zzy.db.entity.base.BaseAdmin;
import com.zzy.db.dao.base.BaseAdminMapper;
import com.zzy.api.service.base.IBaseAdminService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author zzy
 * @since 2020-07-16
 */
@Service
public class BaseAdminServiceImpl extends ServiceImpl<BaseAdminMapper, BaseAdmin> implements IBaseAdminService {

    @Autowired
    BaseAdminMapper baseAdminMapper;
    @Override
    public PageInfo<Map<String, Object>> getBaseAdminList(Integer pageNo, Integer pageSize, String keyWord) {
        PageHelper.startPage(pageNo,pageSize);
        List<Map<String,Object>> list = baseAdminMapper.selectBaseSpotListByPara(keyWord);
        return new PageInfo<>(list);
    }
}
