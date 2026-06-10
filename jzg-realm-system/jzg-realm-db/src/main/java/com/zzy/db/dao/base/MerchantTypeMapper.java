package com.zzy.db.dao.base;

import com.zzy.db.entity.base.MerchantType;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import java.util.List;
import java.util.Map;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author zzy
 * @since 2020-05-18
 */
public interface MerchantTypeMapper extends BaseMapper<MerchantType> {

    Map<String, Object> selectBaseSpotInfoByName(Map<String, Object> para);

    int addBaseInfoByName(Map<String, Object> para);

    int updateBaseMerchantInfo(Map<String, Object> para);

    int delMerchantInfo(Map<String, Object> m);

    List<String> selectMerchantTypeCodeByRoleName(Map<String, Object> m);

    Map<String, Object> selectBaseInfoIdByNameAndUserId(Map<String, Object> m);
}
