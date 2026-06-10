package com.nuwa.ticket.start.api.controller.conf;

import com.alibaba.cola.dto.SingleResponse;
import com.alibaba.cola.exception.Assert;
import com.nuwa.infrastructure.ticket.database.mchconfig.entity.MerchantAppBaseConf;
import com.nuwa.infrastructure.ticket.database.mchconfig.service.MerchantAppBaseConfService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * @author hy
 */
@Api(tags = {"小程序配置相关"})
@Slf4j
@RestController
@RequestMapping("/conf")
public class AppConfController {

    @Autowired
    private MerchantAppBaseConfService merchantAppBaseConfService;

    @ApiOperation(value = "根据appId获取商户配置信息")
    @GetMapping(value = "/getByAppId")
    public SingleResponse<MerchantAppBaseConf> getCurrentUser(@RequestParam(value = "appId", required = true) String appId) {
        List<MerchantAppBaseConf> listAppConf = merchantAppBaseConfService.lambdaQuery().eq(MerchantAppBaseConf::getOutAppId, appId).list();
        Assert.isTrue(listAppConf.size() == 1, "appId配置错误");
        MerchantAppBaseConf merchantAppBaseConf = listAppConf.get(0);
        if ("PLATE".equalsIgnoreCase(merchantAppBaseConf.getType())) {
            merchantAppBaseConf.setPoiId(null);
        }
        return SingleResponse.of(merchantAppBaseConf);
    }
}
