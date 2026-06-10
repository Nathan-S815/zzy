package com.nuwa.infrastructure.json.serializer;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import javax.annotation.Resource;

import cn.hutool.core.util.StrUtil;
import com.alibaba.cola.dto.SingleResponse;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.nuwa.client.zeus.api.order.MaterialClientI;
import com.nuwa.infrastructure.attract.database.common.entity.Material;
import com.nuwa.infrastructure.attract.database.common.service.MaterialService;
import com.nuwa.infrastructure.cache.GuavaCacheKit;

import com.nuwa.infrastructure.util.util.SpringContextKit;
import io.swagger.annotations.ApiModelProperty;
import jodd.util.CollectionUtil;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

/**
 * MaterialSerializer 素材文件序列化
 *
 * @author hy
 * @date 2020/11/6 14:31
 * @since 1.0.0
 */
@Component
public class MaterialJson extends JsonSerializer<Object> {

    @Resource
    private MaterialService materialService;
    @Override
    public void serialize(Object materialId, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException {

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
            List<MaterialVO> materialClientResult = materialService.getMaterialByIds(materialIds);

            if (!CollectionUtils.isEmpty(materialClientResult)) {
                GuavaCacheKit.put(cacheKey, materialClientResult);
                jsonGenerator.writeObject(materialClientResult);
            }
        }
    }

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
