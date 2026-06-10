package com.nuwa.ticket.start.api.controller.diy;

import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONUtil;
import com.alibaba.cola.dto.SingleResponse;
import com.nuwa.framework.base.UserAware;
import com.nuwa.infrastructure.ticket.database.diy.entity.DiyTemplate;
import com.nuwa.infrastructure.ticket.database.diy.entity.MerchantDiyTemplate;
import com.nuwa.infrastructure.ticket.database.diy.service.MerchantDiyTemplateService;
import com.nuwa.ticket.start.api.controller.diy.param.GetAppPageTemplateParam;
import com.nuwa.ticket.start.api.controller.diy.vo.TemplateJsonVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;
import java.util.Objects;

/**
 * @author hy
 */
@Slf4j
@Controller
@RequestMapping("diy")
@Api(tags = {"装修相关"})
public class DiyController {

    @Autowired
    private MerchantDiyTemplateService merchantDiyTemplateService;

    @ApiOperation(value = "获取商户页面装修数据")
    @RequestMapping(value = "/getAppPageTemplate", method = RequestMethod.GET)
    @ResponseBody
    public SingleResponse<TemplateJsonVO> getAppTemplateJson(GetAppPageTemplateParam param) {
        List<MerchantDiyTemplate> merchantDiyTemplates = merchantDiyTemplateService.lambdaQuery()
                .eq(MerchantDiyTemplate::getMerchantId, param.getMchId())
                .eq(MerchantDiyTemplate::getAppId, param.getAppId())
                .eq(StrUtil.isNotEmpty(param.getType()), MerchantDiyTemplate::getType, param.getType())
                .eq(MerchantDiyTemplate::getSnapshoot, "PUBLIC")
                .list();
        if (!merchantDiyTemplates.isEmpty()) {
            MerchantDiyTemplate merchantDiyTemplate = merchantDiyTemplates.get(0);
            if (Objects.nonNull(merchantDiyTemplate)) {
                String jsonVal = merchantDiyTemplate.getValue();
                if (JSONUtil.isJson(jsonVal)) {
                    return SingleResponse.of(new TemplateJsonVO(JSONUtil.parseObj(jsonVal), merchantDiyTemplate.getId().longValue()));
                } else {
                    return SingleResponse.of(new TemplateJsonVO(JSONUtil.parseArray(jsonVal), merchantDiyTemplate.getId().longValue()));
                }
            }
        }
        return null;
    }
}
