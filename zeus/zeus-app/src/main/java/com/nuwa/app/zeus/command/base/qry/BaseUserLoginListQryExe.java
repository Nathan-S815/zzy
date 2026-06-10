package com.nuwa.app.zeus.command.base.qry;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.date.DateUtil;
import com.alibaba.cola.dto.SingleResponse;
import com.nuwa.client.zeus.dto.clientobject.base.qry.BaseUserLoginListQry;
import com.nuwa.client.zeus.dto.clientobject.base.qry.PlatUpgradeListQry;
import com.nuwa.framework.cola.starter.cmd.AbstractCmdExe;
import com.nuwa.infrastructure.zeus.database.base.entity.BaseUser;
import com.nuwa.infrastructure.zeus.database.base.entity.LoginLog;
import com.nuwa.infrastructure.zeus.database.base.entity.PlatUpgrade;
import com.nuwa.infrastructure.zeus.database.base.entity.PlatUpgradeDetails;
import com.nuwa.infrastructure.zeus.database.base.service.BaseUserService;
import com.nuwa.infrastructure.zeus.database.base.service.LoginLogService;
import com.nuwa.infrastructure.zeus.database.base.service.PlatUpgradeDetailsService;
import com.nuwa.infrastructure.zeus.database.base.service.PlatUpgradeService;
import com.nuwa.infrastructure.zeus.enums.DeleteFlagEnum;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
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
public class BaseUserLoginListQryExe extends AbstractCmdExe<BaseUserLoginListQry, SingleResponse<List<BaseUserLoginListQryExe.BaseUserLoginVO>>> {

    @Autowired
    private BaseUserService baseUserService;

    @Autowired
    private LoginLogService loginLogService;

    @Override
    protected SingleResponse<List<BaseUserLoginVO>> handle(BaseUserLoginListQry cmd) {
        List<Integer> userIds = baseUserService.lambdaQuery()
                .eq(BaseUser::getTenantId, cmd.getUserAware().getMchId())
                .eq(BaseUser::getStatus, 0)
                .list().stream().map(BaseUser::getId).collect(Collectors.toList());

        List<BaseUserLoginVO> result = new ArrayList<>();
        loginLogService.lambdaQuery()
                .in(LoginLog::getUserId, userIds)
                .list().stream().collect(Collectors.groupingBy(LoginLog::getUserName, Collectors.counting())).forEach((key,value)->{
            BaseUserLoginVO vo = new BaseUserLoginVO();
            vo.setUsername(key);
            vo.setLoginTimes(value);
            result.add(vo);
        });
        return SingleResponse.of(result);
    }

    @Data
    public static class BaseUserLoginVO {
        @ApiModelProperty("用户名")
        private String username;
        @ApiModelProperty("登录次数")
        private Long loginTimes;
    }
}
