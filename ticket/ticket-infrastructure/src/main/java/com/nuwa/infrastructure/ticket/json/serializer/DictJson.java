package com.nuwa.infrastructure.ticket.json.serializer;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.nuwa.infrastructure.ticket.SpringContextKit;
import com.nuwa.infrastructure.ticket.cache.GuavaCacheKit;
import com.nuwa.infrastructure.ticket.database.dict.entity.SysDictData;
import com.nuwa.infrastructure.ticket.database.dict.mapper.SysDictDataMapper;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * DictJson 字典序列化
 *
 * @author hy
 * @date 2020/11/6 14:31
 * @since 1.0.0
 */
public class DictJson extends JsonSerializer<Object> {

    @Override
    public void serialize(Object dictId, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException {
        SysDictDataMapper dictDataMapper = SpringContextKit.getBean(SysDictDataMapper.class);
        if (Objects.isNull(dictId)) {
            jsonGenerator.writeObject(null);
            return;
        }
        String currentName = jsonGenerator.getOutputContext().getCurrentName();
        String dictIdStr = dictId.toString();

        List<Long> dictIds = Arrays.stream(dictIdStr.split(",")).map(Long::valueOf).collect(Collectors.toList());
        String cacheKey = "dict:" + currentName + ":" + dictIds.stream().map(String::valueOf).collect(Collectors.joining(","));
        Object cacheData = GuavaCacheKit.get(cacheKey);
        if (Objects.nonNull(cacheData)) {
            jsonGenerator.writeObject(cacheData);
        } else {
            List<DictVO> dictVOList = dictDataMapper.lambdaQueryChain().eq(SysDictData::getDictColumn, currentName).in(SysDictData::getDictValue, dictIds).list().stream().map(DictVO::toVO).collect(Collectors.toList());
           /* if (dictVOList != null && dictVOList.size() == 1) {
                jsonGenerator.writeObject(dictVOList.get(0));
            }*/
            GuavaCacheKit.put(cacheKey, dictVOList);
            jsonGenerator.writeObject(dictVOList);
        }
    }

    @Data
    @AllArgsConstructor
    public static class DictVO {

        @ApiModelProperty(value = "value")
        private String value;

        @ApiModelProperty(value = "label")
        private String label;

        public static DictVO toVO(SysDictData entity) {
            return new DictVO(entity.getDictValue(), entity.getDictLabel());
        }
    }
}
