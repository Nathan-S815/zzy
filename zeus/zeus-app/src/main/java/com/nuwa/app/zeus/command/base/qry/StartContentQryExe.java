package com.nuwa.app.zeus.command.base.qry;

import com.alibaba.cola.dto.SingleResponse;
import com.nuwa.app.zeus.util.TreeUtil;
import com.nuwa.app.zeus.vo.TreeNode;
import com.nuwa.client.zeus.dto.clientobject.base.qry.GettingStartedQry;
import com.nuwa.client.zeus.dto.clientobject.base.qry.GettingStartedTreeQry;
import com.nuwa.framework.cola.starter.cmd.AbstractCmdExe;
import com.nuwa.infrastructure.zeus.database.base.entity.GettingStarted;
import com.nuwa.infrastructure.zeus.database.base.service.GettingStartedService;
import com.nuwa.infrastructure.zeus.enums.DeleteFlagEnum;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

/**
 * GetBaseGroupByIdQryExe
 *
 * @author hy
 * @date 2021/5/25 16:32
 * @since 1.0.0
 */
@Slf4j
@Component
public class StartContentQryExe extends AbstractCmdExe<GettingStartedQry, SingleResponse> {

    @Autowired
    private GettingStartedService gettingStartedService;

    @Override
    protected SingleResponse handle(GettingStartedQry cmd) {
        return SingleResponse.of(gettingStartedService.getById(cmd.getId()));
    }

}
