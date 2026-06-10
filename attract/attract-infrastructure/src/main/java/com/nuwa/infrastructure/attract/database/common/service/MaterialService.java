package com.nuwa.infrastructure.attract.database.common.service;

import java.util.List;

import com.nuwa.infrastructure.attract.database.common.entity.Material;
import com.nuwa.framework.database.supper.SuperService;
import com.nuwa.infrastructure.json.serializer.MaterialJson;

/**
 *  服务类
 *
 * @author nanhuang @南皇
 * @since 2022-09-08
 */
public interface MaterialService extends SuperService<Material> {
    /**
     * 通过ids获取
     * @param ids ids
     * @return
     */
    List<MaterialJson.MaterialVO> getMaterialByIds(List<Long> ids);
}
