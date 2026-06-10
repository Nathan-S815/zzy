package com.nuwa.attract.start.api.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * DateConverterConfig 格式化前端日期
 *
 * @author hy
 * @date 2020/9/17 10:30
 * @since 1.0.0
 */
@Slf4j
@Component
public class DateConverterConfig implements Converter<String, Date> {
    private static final List<String> DATE_FORMAT = new ArrayList<>(4);

    static {
        DATE_FORMAT.add("yyyy-MM");
        DATE_FORMAT.add("yyyy-MM-dd");
        DATE_FORMAT.add("yyyy-MM-dd hh:mm");
        DATE_FORMAT.add("yyyy-MM-dd hh:mm:ss");
    }

    @Override
    public Date convert(String source) {
        String value = source.trim();
        if ("".equals(value)) {
            return null;
        }
        if (source.matches("^\\d{4}-\\d{1,2}$")) {
            return parseDate(source, DATE_FORMAT.get(0));
        } else if (source.matches("^\\d{4}-\\d{1,2}-\\d{1,2}$")) {
            return parseDate(source, DATE_FORMAT.get(1));
        } else if (source.matches("^\\d{4}-\\d{1,2}-\\d{1,2} {1}\\d{1,2}:\\d{1,2}$")) {
            return parseDate(source, DATE_FORMAT.get(2));
        } else if (source.matches("^\\d{4}-\\d{1,2}-\\d{1,2} {1}\\d{1,2}:\\d{1,2}:\\d{1,2}$")) {
            return parseDate(source, DATE_FORMAT.get(3));
        } else {
            throw new IllegalArgumentException("Invalid boolean value '" + source + "'");
        }
    }

    /**
     * 格式化日期
     *
     * @param dateStr String 字符型日期
     * @param format  String 格式
     * @return Date 日期
     */
    public Date parseDate(String dateStr, String format) {
        Date date = null;
        try {
            DateFormat dateFormat = new SimpleDateFormat(format);
            date = dateFormat.parse(dateStr);
        } catch (Exception e) {
            log.error("格式化日期异常", e);
        }
        return date;
    }

}
