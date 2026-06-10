package com.nuwa.attract.start.api.config;

import com.p6spy.engine.spy.appender.MessageFormattingStrategy;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author hy
 */
public class P6SpyLoggerConfig implements MessageFormattingStrategy {

    private final SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss:SSS");

    @Override
    public String formatMessage(int connectionId, String now, long elapsed, String category, String prepared, String sql) {
        return !"".equals(sql.trim()) ? this.format.format(new Date()) + " | took " + elapsed + "ms | " + category + " | connection " + connectionId + "\n " + sql + ";" : "";
    }

}
