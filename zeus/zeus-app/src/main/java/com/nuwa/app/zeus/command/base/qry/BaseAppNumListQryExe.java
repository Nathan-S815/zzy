package com.nuwa.app.zeus.command.base.qry;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.date.DateUtil;
import com.alibaba.cola.dto.SingleResponse;
import com.nuwa.client.zeus.dto.clientobject.base.qry.BaseAppListQry;
import com.nuwa.client.zeus.dto.clientobject.base.qry.BaseUserLoginListQry;
import com.nuwa.framework.cola.starter.cmd.AbstractCmdExe;
import com.nuwa.infrastructure.zeus.database.base.entity.BaseUser;
import com.nuwa.infrastructure.zeus.database.base.entity.LoginLog;
import com.nuwa.infrastructure.zeus.database.base.service.BaseUserService;
import com.nuwa.infrastructure.zeus.database.base.service.LoginLogService;
import com.nuwa.infrastructure.zeus.database.mch.entity.MerchantApp;
import com.nuwa.infrastructure.zeus.database.mch.service.MerchantAppService;
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
public class BaseAppNumListQryExe extends AbstractCmdExe<BaseAppListQry, SingleResponse<List<BaseAppNumListQryExe.BaseAppNumVO>>> {

    @Autowired
    private MerchantAppService merchantAppService;

    @Override
    protected SingleResponse<List<BaseAppNumVO>> handle(BaseAppListQry cmd) {
        List<BaseAppNumVO> result = new ArrayList<>();
        BaseAppNumVO system = new BaseAppNumVO();
        BaseAppNumVO function = new BaseAppNumVO();
        system.setAppType("系统应用");
        function.setAppType("功能应用");
        result.add(system);
        result.add(function);

        merchantAppService.lambdaQuery()
                .eq(MerchantApp::getMerchantId, cmd.getUserAware().getMchId())
                .eq(MerchantApp::getStatus, 1)
                .list().stream().forEach(x->{
                    if (x.getParentAppId() == -1l){
                        system.setAppNum(system.getAppNum() + 1);
                    }else {
                        function.setAppNum(function.getAppNum() + 1);
                    }
                });
        return SingleResponse.of(result);
    }

    @Data
    public static class BaseAppNumVO {
        @ApiModelProperty("应用类型")
        private String appType;
        @ApiModelProperty("应用数量")
        private Long appNum = 0l;
    }
}
