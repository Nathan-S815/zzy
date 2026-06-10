package com.nuwa.app.zeus.command.base.qry;

import cn.hutool.core.bean.BeanUtil;
import com.alibaba.cola.dto.SingleResponse;
import com.nuwa.client.zeus.dto.clientobject.base.qry.BaseAppListQry;
import com.nuwa.client.zeus.dto.clientobject.base.qry.BaseUserLoginListQry;
import com.nuwa.framework.cola.starter.cmd.AbstractCmdExe;
import com.nuwa.infrastructure.zeus.database.base.entity.CnRegionInfo;
import com.nuwa.infrastructure.zeus.database.base.service.CnRegionInfoService;
import com.nuwa.infrastructure.zeus.database.mch.entity.Merchant;
import com.nuwa.infrastructure.zeus.database.mch.entity.MerchantApp;
import com.nuwa.infrastructure.zeus.database.mch.service.MerchantAppService;
import com.nuwa.infrastructure.zeus.database.mch.service.MerchantService;
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
public class BaseMerchantQryExe extends AbstractCmdExe<BaseUserLoginListQry, SingleResponse<BaseMerchantQryExe.BaseMerchantVO>> {

    @Autowired
    private MerchantService merchantService;

    @Autowired
    private CnRegionInfoService cnRegionInfoService;

    @Override
    protected SingleResponse<BaseMerchantVO> handle(BaseUserLoginListQry cmd) {
        Merchant merchant = merchantService.getById(cmd.getUserAware().getMchId());
        BaseMerchantVO vo = new BaseMerchantVO();
        BeanUtil.copyProperties(merchant,vo);
        vo.setProvince(cnRegionInfoService.getById(merchant.getProvince()).getCriName());
        vo.setCity(cnRegionInfoService.getById(merchant.getCity()).getCriName());
        vo.setCounty(cnRegionInfoService.getById(merchant.getCounty()).getCriName());
        return SingleResponse.of(vo);
    }

    @Data
    public static class BaseMerchantVO {
        @ApiModelProperty("账号类型 1:个人,2企业,3:政府组织")
        private Integer mchType;
        @ApiModelProperty("联系电话")
        private String contentPhone;
        @ApiModelProperty("商户名称")
        private String mchName;
        @ApiModelProperty("所在地省")
        private String province;
        @ApiModelProperty("所在地市")
        private String city;
        @ApiModelProperty("所在地区")
        private String county;
        @ApiModelProperty("具体地址")
        private String address;
    }
}
