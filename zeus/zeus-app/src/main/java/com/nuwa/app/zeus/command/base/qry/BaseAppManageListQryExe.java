package com.nuwa.app.zeus.command.base.qry;

import com.alibaba.cola.dto.SingleResponse;
import com.nuwa.client.zeus.dto.clientobject.base.qry.BaseAppListQry;
import com.nuwa.framework.cola.starter.cmd.AbstractCmdExe;
import com.nuwa.infrastructure.zeus.database.mch.entity.MerchantApp;
import com.nuwa.infrastructure.zeus.database.mch.service.MerchantAppService;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * GetBaseGroupByIdQryExe
 *
 * @author hy
 * @date 2021/5/25 16:32
 * @since 1.0.0
 */
@Slf4j
@Component
public class BaseAppManageListQryExe extends AbstractCmdExe<BaseAppListQry, SingleResponse<List<BaseAppManageListQryExe.BaseAppManageVO>>> {

    @Autowired
    private MerchantAppService merchantAppService;

    @Override
    protected SingleResponse<List<BaseAppManageVO>> handle(BaseAppListQry cmd) {
        List<BaseAppManageVO> result = new ArrayList<>();
        BaseAppManageVO overdue = new BaseAppManageVO();
        BaseAppManageVO expiring = new BaseAppManageVO();
        BaseAppManageVO using = new BaseAppManageVO();
        BaseAppManageVO paid = new BaseAppManageVO();
        overdue.setManageType("已逾期");
        expiring.setManageType("将到期");
        using.setManageType("使用中");
        paid.setManageType("待支付");

        result.add(overdue);
        result.add(expiring);
        result.add(using);
        result.add(paid);

        merchantAppService.lambdaQuery()
                .eq(MerchantApp::getMerchantId, cmd.getUserAware().getMchId())
                .eq(MerchantApp::getStatus, 1)
                .list().stream().forEach(x -> {
            using.setAppNum(using.getAppNum() + 1);
        });
        return SingleResponse.of(result);
    }

    @Data
    public static class BaseAppManageVO {
        @ApiModelProperty("管理类型")
        private String manageType;
        @ApiModelProperty("应用数量")
        private Long appNum = 0l;
    }
}
