package com.nuwa.infrastructure.zeus.util;

import cn.hutool.core.util.StrUtil;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.nuwa.infrastructure.zeus.cache.GuavaCacheKit;
import com.nuwa.infrastructure.zeus.config.OssDomainConfigEx;
import com.nuwa.infrastructure.zeus.database.material.entity.Material;
import com.nuwa.infrastructure.zeus.database.material.mapper.MaterialMapper;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * MaterialSerializer 素材文件序列化
 *
 * @author hy
 * @date 2020/11/6 14:31
 * @since 1.0.0
 */
@Component
public class MaterialJson extends JsonSerializer<Object> {

    @Override
    public void serialize(Object materialId, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException {
        MaterialMapper materialMapper = SpringContextKit.getBean(MaterialMapper.class);
        if (Objects.isNull(materialId) || materialId.equals(0) || StrUtil.isBlank(materialId + "")) {
            jsonGenerator.writeObject(null);
            return;
        }
        String materialIdStr = materialId.toString();
        List<Long> materialIds = Arrays.stream(materialIdStr.split(",")).map(Long::parseLong).collect(Collectors.toList());
        String cacheKey = "material:" + materialIds.stream().map(String::valueOf).collect(Collectors.joining(","));
        Object cacheData = GuavaCacheKit.get(cacheKey);
        if (Objects.nonNull(cacheData)) {
            jsonGenerator.writeObject(cacheData);
        } else {
            List<Material> materialListData = materialMapper.lambdaQueryChain()
                    .in(Material::getId, materialIds)
                    .eq(Material::getStatus, 1)
                    .list();
            Set<Long> setMaterialId = materialListData.stream().map(Material::getId).collect(Collectors.toSet());
            List<Material> materialItems = new ArrayList<>(materialListData);
            materialIds.forEach(id -> {
                if (!setMaterialId.contains(id)) {
                    Material material = materialMapper.selectById(1L); //默认图片
                    materialItems.add(material);
                }
            });

            List<MaterialVO> materialList = materialItems.stream()
                    .map(this::urlConvert)
                    .map(MaterialVO::toVO)
                    .collect(Collectors.toList());

            GuavaCacheKit.put(cacheKey, materialList);
            jsonGenerator.writeObject(materialList);
        }
    }

    public Material urlConvert(Material material) {
        OssDomainConfigEx ossDomainConfig = SpringContextKit.getBean(OssDomainConfigEx.class);
        if (StrUtil.isBlank(ossDomainConfig.getOssDomain())) {
            return material;
        }
        material.setUrl(material.getUrl());
        return material;
    }

  /*  public Material getOutSource(Material material) {
        if (material.getOutSourceId() == null) {
            return material;
        }
        String body = HttpUtil.createGet("https://data.ctpaas.com/api/material/source/" + material.getOutSourceId()).execute().body();
        return JSON.parseObject(body, Material.class);
    }*/

    @Data
    @AllArgsConstructor
    public static class MaterialVO {

        @ApiModelProperty(value = "素材id")
        private Long id;

        @ApiModelProperty(value = "图片url")
        private String url;

        @ApiModelProperty(value = "文件类型")
        private Integer fileType;

        public static MaterialVO toVO(Material entity) {
            return new MaterialVO(entity.getId(), entity.getUrl(), entity.getFileType());
        }
    }
}
