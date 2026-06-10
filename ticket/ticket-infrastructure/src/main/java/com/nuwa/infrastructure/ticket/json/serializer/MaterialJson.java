package com.nuwa.infrastructure.ticket.json.serializer;

import cn.hutool.core.util.StrUtil;
import com.alibaba.cola.dto.SingleResponse;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.nuwa.client.zeus.api.order.MaterialClientI;
import com.nuwa.infrastructure.ticket.SpringContextKit;
import com.nuwa.infrastructure.ticket.cache.GuavaCacheKit;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

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
        MaterialClientI materialClient = SpringContextKit.getBean(MaterialClientI.class);

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
            SingleResponse<?> materialClientResult = materialClient.getMaterialByIds(materialIds);

            if (materialClientResult.isSuccess()) {
                GuavaCacheKit.put(cacheKey, materialClientResult.getData());
                jsonGenerator.writeObject(materialClientResult.getData());
            }
        }
    }
}
