package com.zzy.api.service.base;


import com.baomidou.mybatisplus.extension.service.IService;
import com.zzy.db.entity.base.MerchantType;

import java.util.List;
import java.util.Map;

/**
 * <p>
 *  商户类型服务类
 * </p>
 *
 * @author zzy
 * @since 2020-05-18
 */
public interface IMerchantTypeService extends IService<MerchantType> {

    Map<String, Object> getBaseSpotInfoByName(Map<String, Object> para);

    int addBaseMerchantInfo(Map<String, Object> para);

    boolean editBaseMerchantInfo(Map<String, Object> para);

    boolean delBaseMerchantInfo(Map<String, Object> m);

    List<String> selectMerchantTypeCodeByRoleName(Map<String, Object> m);

    Map<String, Object> selectBaseInfoIdByNameAndUserId(Map<String, Object> m);
}
