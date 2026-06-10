package com.nuwa.app.zeus.command.base.qry;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.date.DateUtil;
import com.alibaba.cola.dto.SingleResponse;
import com.nuwa.client.zeus.dto.clientobject.base.qry.BaseUserLoginListQry;
import com.nuwa.framework.cola.starter.cmd.AbstractCmdExe;
import com.nuwa.infrastructure.zeus.database.base.entity.BaseUser;
import com.nuwa.infrastructure.zeus.database.base.entity.LoginLog;
import com.nuwa.infrastructure.zeus.database.base.service.BaseUserService;
import com.nuwa.infrastructure.zeus.database.base.service.LoginLogService;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
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
public class BaseUserIPListQryExe extends AbstractCmdExe<BaseUserLoginListQry, SingleResponse<List<BaseUserIPListQryExe.BaseUserIPVO>>> {

    @Autowired
    private BaseUserService baseUserService;

    @Autowired
    private LoginLogService loginLogService;

    @Override
    protected SingleResponse<List<BaseUserIPVO>> handle(BaseUserLoginListQry cmd) {
        List<Integer> userIds = baseUserService.lambdaQuery()
                .eq(BaseUser::getTenantId, cmd.getUserAware().getMchId())
                .eq(BaseUser::getStatus, 0)
                .list().stream().map(BaseUser::getId).collect(Collectors.toList());

        List<BaseUserIPVO> result = new ArrayList<>();
        loginLogService.lambdaQuery()
                .in(LoginLog::getUserId, userIds)
                .list().stream().collect(Collectors.groupingBy(LoginLog::getLoginIp, Collectors.counting())).forEach((key,value)->{
            BaseUserIPVO vo = new BaseUserIPVO();
            vo.setIp(key);
            vo.setLoginTimes(value);
            result.add(vo);
        });
        return SingleResponse.of(result);
    }

    @Data
    public static class BaseUserIPVO {
        @ApiModelProperty("IP")
        private String ip;
        @ApiModelProperty("登录次数")
        private Long loginTimes;
    }
}
