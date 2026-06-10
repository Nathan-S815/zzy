package com.zzy.api.service.base.impl;


import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zzy.api.service.base.IMerchantTypeService;
import com.zzy.db.dao.base.MerchantTypeMapper;
import com.zzy.db.entity.base.MerchantType;
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
 * @since 2020-05-18
 */
@Service
public class MerchantTypeServiceImpl extends ServiceImpl<MerchantTypeMapper, MerchantType> implements IMerchantTypeService {


    @Autowired
    private MerchantTypeMapper merchantTypeMapper;


    @Override
    public Map<String, Object> getBaseSpotInfoByName(Map<String, Object> para) {
        return merchantTypeMapper.selectBaseSpotInfoByName(para);
    }

    @Override
    public int addBaseMerchantInfo(Map<String, Object> para) {
        return merchantTypeMapper.addBaseInfoByName(para);
    }

    @Override
    public boolean editBaseMerchantInfo(Map<String, Object> para) {
        return merchantTypeMapper.updateBaseMerchantInfo(para)>0;
    }

    @Override
    public boolean delBaseMerchantInfo(Map<String, Object> m) {
        return merchantTypeMapper.delMerchantInfo(m)>0;
    }

    @Override
    public List<String> selectMerchantTypeCodeByRoleName(Map<String, Object> m) {
        return merchantTypeMapper.selectMerchantTypeCodeByRoleName(m);
    }

    @Override
    public Map<String, Object> selectBaseInfoIdByNameAndUserId(Map<String, Object> m) {
        return merchantTypeMapper.selectBaseInfoIdByNameAndUserId(m);
    }
}
