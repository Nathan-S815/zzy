package com.nuwa.ticket.start.api.controller.scenicspot;

import com.alibaba.cola.dto.SingleResponse;
import com.nuwa.framework.base.UserAware;
import com.nuwa.infrastructure.ticket.database.scenicspot.entity.ScenicspotBaseLabel;
import com.nuwa.infrastructure.ticket.database.scenicspot.entity.ScenicspotLabel;
import com.nuwa.infrastructure.ticket.database.scenicspot.service.ScenicspotBaseLabelService;
import com.nuwa.infrastructure.ticket.database.scenicspot.service.ScenicspotLabelService;
import com.nuwa.ticket.start.api.controller.param.AuditPassScenicspotPoiParam;
import com.nuwa.ticket.start.api.controller.param.SaveScenicspotLabelParam;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.validation.Valid;
import java.util.Objects;

/**
 * @author hy
 */
@Slf4j
@Controller
@RequestMapping("/scenicspot/diy/label")
@Api(tags = {"商户景点Label相关"})
public class ScenicspotLabelController {

    @Autowired
    private ScenicspotBaseLabelService scenicspotBaseLabelService;

    @ApiOperation(value = "景点保存标签")
    @RequestMapping(value = "/save", method = RequestMethod.POST)
    @ResponseBody
    public SingleResponse<ScenicspotBaseLabel> saveLabel(@RequestBody @Valid SaveScenicspotLabelParam form, UserAware userAware) {
        Integer count = scenicspotBaseLabelService.lambdaQuery().eq(ScenicspotBaseLabel::getLabelName, form.getLabelName()).count();
        if (count > 0) {
            return SingleResponse.buildFailure("9841", "标签名称重复");
        }
        ScenicspotBaseLabel label = new ScenicspotBaseLabel();
        label.setLabelName(form.getLabelName());
        label.setCreateById(Objects.isNull(form.getMchId()) ? userAware.getMchId() + "" : form.getMchId() + "");
        label.insert();
        return SingleResponse.of(label);
    }

}
