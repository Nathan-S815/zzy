package com.nuwa.infrastructure.ticket.database.callcenter.param;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.nuwa.client.ticket.dto.clientobject.callcenter.qry.TalkManagePageQry;
import com.nuwa.framework.database.PageQry;
import com.nuwa.infrastructure.ticket.database.callcenter.entity.TalkManage;
import io.swagger.annotations.ApiModel;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <pre>
 * 会话管理 分页参数对象
 * </pre>
 *
 * @author huyonghack@163.com
 * @date 2021-05-15
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "会话管理分页参数")
public class TalkManagePageParam extends PageQry<TalkManage> {
    private static final long serialVersionUID = 1L;

    private TalkManagePageQry qry;

    public TalkManagePageParam(TalkManagePageQry qry) {
        super(qry);
        this.qry = qry;
    }

    @Override
    public LambdaQueryWrapper<TalkManage> toQueryWrapper() {
        LambdaQueryWrapper<TalkManage> queryWrapper = Wrappers.lambdaQuery();
        queryWrapper.select(TalkManage.class,
                t -> !t.getColumn().endsWith("_editor")
        );
            return queryWrapper;
    }
}
