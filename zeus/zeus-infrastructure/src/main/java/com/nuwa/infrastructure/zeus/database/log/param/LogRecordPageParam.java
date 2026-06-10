package com.nuwa.infrastructure.zeus.database.log.param;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.nuwa.infrastructure.zeus.database.log.entity.LogRecord;
import com.nuwa.client.zeus.dto.clientobject.log.qry.LogRecordPageQry;
import java.util.Objects;

import com.nuwa.framework.database.PageQry;

/**
 * <pre>
 * 操作日志表 分页参数对象
 * </pre>
 *
 * @author huyonghack@163.com
 * @date 2021-06-04
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "操作日志表分页参数")
public class LogRecordPageParam extends PageQry<LogRecord> {
    private static final long serialVersionUID = 1L;

    private LogRecordPageQry qry;

    public LogRecordPageParam(LogRecordPageQry qry) {
        super(qry);
        this.qry = qry;
    }

    @Override
    public LambdaQueryWrapper<LogRecord> toQueryWrapper() {
        LambdaQueryWrapper<LogRecord> queryWrapper = Wrappers.lambdaQuery();
        queryWrapper.select(LogRecord.class,
                t -> !t.getColumn().endsWith("_editor")
        );
            return queryWrapper;
    }
}
