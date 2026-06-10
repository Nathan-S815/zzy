package com.nuwa.app.zeus.command.base;

import com.alibaba.cola.dto.SingleResponse;
import com.nuwa.app.zeus.service.GroupBiz;
import com.nuwa.client.zeus.dto.clientobject.base.CreateBaseGroupCmd;
import com.nuwa.framework.cola.starter.cmd.AbstractCmdExe;
import com.nuwa.infrastructure.zeus.database.base.entity.BaseGroup;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Component;

import java.sql.SQLIntegrityConstraintViolationException;
import java.util.Date;

/**
 * CreateBaseGroupCmdExe
 *
 * @author hy
 * @date 2021/5/25 17:19
 * @since 1.0.0
 */
@Slf4j
@Component
public class CreateBaseGroupCmdExe extends AbstractCmdExe<CreateBaseGroupCmd, SingleResponse> {

    @Autowired
    private GroupBiz groupBiz;

    @Override
    protected SingleResponse handle(CreateBaseGroupCmd cmd) {
        BaseGroup group = new BaseGroup();
        BeanUtils.copyProperties(cmd, group);
        group.setCreateHost(cmd.getUserAware().getHostIp());
        group.setCreateTime(new Date());
        group.setCreateUserId(cmd.getUserAware().getUserId() + "");
        group.setCreateUserName(cmd.getUserAware().getUserName());
        try {
            groupBiz.save(group);
        } catch (Exception ex) {
            log.error("code 编码重复", ex);
            return SingleResponse.buildFailure("5601", "code编码重复");
        }
        return SingleResponse.buildSuccess();
    }
}
