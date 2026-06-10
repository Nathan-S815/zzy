package com.nuwa.zeus.start.api.controller.plat.base;

import com.alibaba.cola.dto.SingleResponse;
import com.alibaba.cola.exception.Assert;
import com.mzt.logapi.starter.annotation.LogRecordAnnotation;
import com.nuwa.framework.base.UserAware;
import com.nuwa.infrastructure.zeus.constant.AdminCommonConstant;
import com.nuwa.infrastructure.zeus.database.base.entity.BaseElement;
import com.nuwa.infrastructure.zeus.database.base.mapper.ext.BaseMapperExt;
import com.nuwa.infrastructure.zeus.database.base.service.BaseElementService;
import com.nuwa.infrastructure.zeus.enums.ErrorEnum;
import com.nuwa.zeus.start.api.constants.LogCategoryType;
import com.nuwa.zeus.start.api.constants.LogRecordType;
import com.nuwa.zeus.start.api.controller.plat.base.param.CreateElementParam;
import com.nuwa.zeus.start.api.controller.plat.base.param.ModifyElementParam;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Date;
import java.util.List;

/**
 * ElementController
 *
 * @author hy
 * @date 2021/5/26 17:10
 * @since 1.0.0
 */
@Controller
@RequestMapping("element")
@Api(tags = {"权限管理"})
public class ElementController {

    @Autowired
    private BaseMapperExt baseMapperExt;

    @Autowired
    private BaseElementService elementService;

    @LogRecordAnnotation(
            fail = "创建权限失败，原因：「{{#_errorMsg}}」",
            category = LogCategoryType.PLAT,
            success = "创建权限成功",
            prefix = LogRecordType.MERCHANT_APP,
            bizNo = "{{#cmd.merchantId}}",
            detail = "{{#cmd.toJson()}}")
    @ApiOperation(value = "创建权限")
    @RequiresRoles(AdminCommonConstant.ADMIN_ROLE)
    @RequestMapping(value = "/create", method = RequestMethod.POST)
    @ResponseBody
    public SingleResponse<BaseElement> create(@Validated @RequestBody CreateElementParam param, UserAware userAware) {
        Integer count = elementService.lambdaQuery().eq(BaseElement::getCode, param.getCode()).count();
        Assert.isTrue(count.equals(0), ErrorEnum.BaseElement_CodeIsEXIST, "权限编码已存在");
        BaseElement element = new BaseElement();
        BeanUtils.copyProperties(param, element);
        element.setCreateHost(userAware.getHostIp());
        element.setCreateTime(new Date());
        element.setCreateUserName(userAware.getUserName());
        element.setCreateUserId(userAware.getMchUserId() + "");
        boolean insert = element.insert();
        if (insert) {
            return SingleResponse.of(element);
        }
        return ErrorEnum.DATA_FAIL.buildSuccess();
    }

    @LogRecordAnnotation(
            fail = "修改权限失败，原因：「{{#_errorMsg}}」",
            category = LogCategoryType.PLAT,
            success = "修改权限成功",
            prefix = LogRecordType.MERCHANT_APP,
            bizNo = "{{#cmd.merchantId}}",
            detail = "{{#cmd.toJson()}}")
    @ApiOperation(value = "修改权限")
    @RequiresRoles(AdminCommonConstant.ADMIN_ROLE)
    @RequestMapping(value = "/modify", method = RequestMethod.POST)
    @ResponseBody
    public SingleResponse<BaseElement> modify(@Validated @RequestBody ModifyElementParam param, UserAware userAware) {
        Integer count = elementService.lambdaQuery().eq(BaseElement::getCode, param.getCode()).count();
        Assert.isTrue(count.equals(0), ErrorEnum.BaseElement_CodeIsEXIST, "权限编码已存在");
        BaseElement element = elementService.getById(param.getId());
        BeanUtils.copyProperties(param, element);
        boolean update = element.updateById();
        if (update) {
            return SingleResponse.of(element);
        }
        return ErrorEnum.DATA_FAIL.buildSuccess();
    }

    @ApiOperation(value = "获取指定菜单权限列表")
    @RequiresRoles(AdminCommonConstant.ADMIN_ROLE)
    @RequestMapping(value = "/user", method = RequestMethod.GET)
    @ResponseBody
    public SingleResponse<List<BaseElement>> getAuthorityElement(String menuId, UserAware userAware) {
        List<BaseElement> elements = baseMapperExt.selectAuthorityMenuElementByUserId(userAware.getMchUserId() + "", menuId);
        return SingleResponse.of(elements);
    }

    @ApiOperation(value = "获取当前用户所有权限")
    @RequiresRoles(AdminCommonConstant.ADMIN_ROLE)
    @RequestMapping(value = "/user/menu", method = RequestMethod.GET)
    @ResponseBody
    public SingleResponse<List<BaseElement>> getAuthorityElement(UserAware userAware) {
        List<BaseElement> elements = baseMapperExt.selectAuthorityElementByUserId(userAware.getMchUserId() + "");
        return SingleResponse.of(elements);
    }
}
