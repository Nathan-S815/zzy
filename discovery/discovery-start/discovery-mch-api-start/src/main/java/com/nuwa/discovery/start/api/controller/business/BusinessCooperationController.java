package com.nuwa.discovery.start.api.controller.business;

import cn.hutool.core.convert.Convert;
import com.alibaba.cola.dto.SingleResponse;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.nuwa.client.discovery.dto.clientobject.business.qry.BusinessCooperationQry;
import com.nuwa.framework.base.UserAware;
import com.nuwa.infrastructure.discovery.database.cooperation.entity.BusinessCooperation;
import com.nuwa.infrastructure.discovery.database.cooperation.service.BusinessCooperationService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@Slf4j
@Controller
@RequestMapping("/business/cooperation")
@Api(tags = {"商业合作相关"})
public class BusinessCooperationController {

    @Autowired
    private BusinessCooperationService businessCooperationService;

    @ApiOperation(value = "修改商业合作")
    @RequestMapping(value = "/update", method = RequestMethod.POST)
    @ResponseBody
    public SingleResponse<?> update(@RequestBody BusinessCooperation businessCooperation, UserAware userAware) {
        businessCooperationService.updateBusinessCooperation(businessCooperation);
        return SingleResponse.buildSuccess();
    }

    @ApiOperation(value = "根据id获取商业合作")
    @RequestMapping(value = "/get/{id}", method = RequestMethod.GET)
    @ResponseBody
    public SingleResponse<?> get(@PathVariable("id") Long id, UserAware userAware) {
        BusinessCooperation businessCooperationById = businessCooperationService.getBusinessCooperationById(id);
        return SingleResponse.of(businessCooperationById);
    }

    @ApiOperation(value = "获取商业合作分页")
    @RequestMapping(value = "/page", method = RequestMethod.GET)
    @ResponseBody
    public SingleResponse<?> page(BusinessCooperationQry qry, UserAware userAware) {
        IPage<BusinessCooperation> businessCooperationPage = businessCooperationService.getBusinessCooperationPage(qry, userAware, true);
        return SingleResponse.of(businessCooperationPage);
    }
}
