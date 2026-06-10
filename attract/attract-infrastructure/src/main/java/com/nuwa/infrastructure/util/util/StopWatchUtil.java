package com.nuwa.infrastructure.util.util;

import java.text.NumberFormat;

import cn.hutool.core.date.StopWatch;
import cn.hutool.core.io.FileUtil;

/**
 * @author hy
 */
public class StopWatchUtil {
    public static String prettyPrint(StopWatch stopWatch) {
        StringBuilder sb = new StringBuilder(stopWatch.shortSummary());
        sb.append(FileUtil.getLineSeparator());
        if (null == stopWatch.getTaskInfo()) {
            sb.append("No task info kept");
        } else {
            sb.append("---------------------------------------------").append(FileUtil.getLineSeparator());
            sb.append("ms         %     Task name").append(FileUtil.getLineSeparator());
            sb.append("---------------------------------------------").append(FileUtil.getLineSeparator());

            final NumberFormat nf = NumberFormat.getNumberInstance();
            nf.setMinimumIntegerDigits(4);
            nf.setGroupingUsed(false);

            final NumberFormat pf = NumberFormat.getPercentInstance();
            pf.setMinimumIntegerDigits(3);
            pf.setGroupingUsed(false);
            for (StopWatch.TaskInfo task : stopWatch.getTaskInfo()) {
                sb.append(nf.format(task.getTimeMillis())).append("  ");
                sb.append(pf.format((double) task.getTimeNanos() / stopWatch.getTotalTimeNanos())).append("  ");
                sb.append(task.getTaskName()).append(FileUtil.getLineSeparator());
            }
        }
        return sb.toString();
    }
}
