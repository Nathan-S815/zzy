package com.nuwa.ticket.start.dispatch.util;

import cn.hutool.core.util.StrUtil;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;

/**
 * @author hy
 */
@Slf4j
public class JsonKit {

    public static ObjectMapper objectMapper;

    static {
        objectMapper = new ObjectMapper();
        objectMapper.configure(JsonGenerator.Feature.IGNORE_UNKNOWN, true);
        objectMapper.configure(JsonGenerator.Feature.WRITE_BIGDECIMAL_AS_PLAIN, true);
        objectMapper.configure(JsonParser.Feature.ALLOW_MISSING_VALUES, true);
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        objectMapper.configure(MapperFeature.ACCEPT_CASE_INSENSITIVE_PROPERTIES, true);
    }


    /**
     * Object转json字符串
     */
    public static <T> String toJson(T obj) {
        try {
            if (obj == null) {
                return null;

            } else if (obj instanceof String) {
                return (String) obj;

            } else {
                return objectMapper.writeValueAsString(obj);
            }

        } catch (Exception e) {
            log.error("Parse object to String error", e);
            return null;
        }
    }


    /**
     * Object转json字符串并格式化美化
     */
    public static <T> String toJsonPretty(T obj) {
        try {
            if (obj == null) {
                return null;
            } else if (obj instanceof String) {
                return (String) obj;
            } else {
                return objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(obj);
            }
        } catch (Exception e) {
            log.error("Parse object to String Pretty error", e);
            return null;
        }
    }


    /**
     * json转object
     */
    @SuppressWarnings("unchecked")
    public static <T> T toBean(String json, Class<T> clazz) {
        try {
            if (StrUtil.isBlank(json) || clazz == null) {
                return null;
            } else if (clazz.equals(String.class)) {
                return (T) json;
            } else {
                return objectMapper.readValue(json, clazz);
            }
        } catch (IOException e) {
            log.error("Parse String to bean error", e);
            return null;
        }
    }


    /**
     * string转object 用于转为集合对象
     *
     * @param json            Json字符串
     * @param collectionClass 被转集合的类
     *                        <p>List.class</p>
     * @param elementClasses  被转集合中对象类型的类
     *                        <p>User.class</p>
     */
    public static <T> T toBean(String json, Class<?> collectionClass, Class<?>... elementClasses) {
        try {
            JavaType javaType = objectMapper.getTypeFactory().constructParametricType(collectionClass, elementClasses);
            return objectMapper.readValue(json, javaType);

        } catch (IOException e) {
            log.error("Parse String to Bean error", e);
            return null;
        }
    }
}
