package com.nuwa.infrastructure.attract.database.common.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import cn.hutool.core.util.StrUtil;
import com.nuwa.infrastructure.attract.database.common.entity.Material;
import com.nuwa.infrastructure.attract.database.common.mapper.MaterialMapper;
import com.nuwa.infrastructure.attract.database.common.service.MaterialService;
import com.nuwa.framework.database.supper.SuperServiceImpl;
import com.nuwa.infrastructure.json.serializer.MaterialJson;
import com.nuwa.infrastructure.json.serializer.OssDomainConfigEx;
import com.nuwa.infrastructure.util.util.SpringContextKit;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * 服务实现类
 *
 * @author nanhuang @南皇
 * @since 2022-09-08
 */
@Slf4j
@Service
public class MaterialServiceImpl extends SuperServiceImpl<MaterialMapper, Material> implements MaterialService {

    @Autowired
    private MaterialMapper materialMapper;

    /**
     * 通过ids获取
     *
     * @param ids ids
     * @return
     */
    @Override
    public List<MaterialJson.MaterialVO> getMaterialByIds(List<Long> ids) {
        List<Material> materialListData = materialMapper.lambdaQueryChain()
            .in(Material::getId, ids)
            .eq(Material::getStatus, 1)
            .list();

        Set<Long> setMaterialId = materialListData.stream().map(Material::getId).collect(Collectors.toSet());
        List<Material> materialItems = new ArrayList<>(materialListData);
        ids.forEach(id -> {
            if (!setMaterialId.contains(id)) {
                Material material = materialMapper.selectById(1L); //默认图片
                materialItems.add(material);
            }
        });

        return materialItems.stream()
            .map(this::urlConvert)
            .map(MaterialJson.MaterialVO::toVO)
            .collect(Collectors.toList());
    }

    public Material urlConvert(Material material) {
        OssDomainConfigEx ossDomainConfig = SpringContextKit.getBean(OssDomainConfigEx.class);
        String ossChannel = material.getOssChannel();
        if ("minio".equalsIgnoreCase(ossChannel)) {
            if (StrUtil.isBlank(ossDomainConfig.getOssDomain())) {
                return material;
            }
            material.setUrl(ossDomainConfig.getOssDomain() + "/" + material.getBucketName() + "/" + material.getOssKey());
        } else if ("upYun".equalsIgnoreCase(ossChannel)) {
            material.setUrl(ossDomainConfig.getUpDomain() + "/" + material.getOssKey());
        }
        return material;
    }
}
