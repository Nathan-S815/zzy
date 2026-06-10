package com.nuwa.attract.start.api.controller.unit;

import java.util.List;

import javax.annotation.Resource;
import javax.validation.Valid;

import cn.hutool.core.lang.Assert;
import cn.hutool.crypto.digest.MD5;
import com.alibaba.cola.dto.SingleResponse;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.kitfox.svg.A;
import com.nuwa.attract.start.api.controller.unit.param.AuditParam;
import com.nuwa.client.attract.dto.clientobject.user.qry.AttractUserPageQry;
import com.nuwa.framework.base.UserAware;
import com.nuwa.infrastructure.attract.database.user.entity.AttractUser;
import com.nuwa.infrastructure.attract.database.user.param.AttractUserPageParam;
import com.nuwa.infrastructure.attract.database.user.service.AttractUserService;
import com.nuwa.infrastructure.enums.UserReviewStatusEnum;
import com.nuwa.infrastructure.vo.AttractUserPageVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author nanHuang @南皇
 * @version com.nuwa.attract.start.api.controller.unit:UnitController.java,v1.0.0 2022-09-08 09:30:39 nanHuang Exp $
 */
@Api(tags = {"单位管理"})
@RestController
@RequestMapping("/unit")
public class UnitController {

    @Resource
    private AttractUserService attractUserService;

    @ApiOperation(value = "酒店/景区/旅行社列表")
    @RequestMapping(value = "/page", method = RequestMethod.GET)
    public SingleResponse<IPage<AttractUserPageVO>> getUserInfo(@Valid AttractUserPageQry qry, UserAware userAware)
        throws Exception {
        AttractUserPageParam param = new AttractUserPageParam(qry);
        IPage<AttractUserPageVO> page = attractUserService.paginateAndConvert(param, AttractUserPageVO::toVO);
        return SingleResponse.of(page);
    }

    @ApiOperation(value = "酒店/景区/旅行社 审核")
    @RequestMapping(value = "/audit", method = RequestMethod.POST)
    public SingleResponse<?> audit(@RequestBody @Validated AuditParam param, UserAware userAware) throws Exception {
        AttractUser user = attractUserService.getById(param.getUserId());
        Assert.notNull(user, "用户信息不正确");
        user.setLastUpdateById(userAware.getUserId());
        user.setLastUpdateByName(userAware.getUserName());
        user.setReviewStatus(
            param.getAudit() ? UserReviewStatusEnum.AUDIT_PASS.getCode() : UserReviewStatusEnum.AUDIT_REJECT.getCode());
        user.updateById();
        return SingleResponse.buildSuccess();
    }

    @ApiOperation(value = "酒店/景区/旅行社 禁用/解禁")
    @RequestMapping(value = "/ban/{userId}", method = RequestMethod.GET)
    public SingleResponse<?> ban(@PathVariable("userId") Long userId, UserAware userAware) throws Exception {
        AttractUser user = attractUserService.getById(userId);
        Assert.notNull(user, "用户信息不正确");
        user.setLastUpdateById(userAware.getUserId());
        user.setLastUpdateByName(userAware.getUserName());
        user.setReviewStatus(
            user.getReviewStatus().equals(UserReviewStatusEnum.BAN.getCode()) ? UserReviewStatusEnum.AUDIT_PASS.getCode()
                : UserReviewStatusEnum.BAN.getCode());
        user.updateById();

        return SingleResponse.buildSuccess();
    }

    @ApiOperation(value = "酒店/景区/旅行社 重置密码")
    @RequestMapping(value = "/resetPassword/{userId}", method = RequestMethod.GET)
    public SingleResponse<?> resetPassword(@PathVariable("userId") Long userId, UserAware userAware) throws Exception {
        AttractUser user = attractUserService.getById(userId);
        Assert.notNull(user, "用户信息不正确");
        //固定123456密码
        user.setPassword(MD5.create().digestHex("123456", "utf-8"));
        user.setLastUpdateById(userAware.getUserId());
        user.setLastUpdateByName(userAware.getUserName());
        user.updateById();

        return SingleResponse.buildSuccess();
    }

    @ApiOperation(value = "酒店/景区/旅行社 删除")
    @RequestMapping(value = "/del/{userId}", method = RequestMethod.GET)
    public SingleResponse<?> del(@PathVariable("userId") Long userId, UserAware userAware) throws Exception {
        AttractUser user = attractUserService.getById(userId);
        Assert.notNull(user, "用户信息不正确");
        Assert.isTrue(!user.getReviewStatus().equals(UserReviewStatusEnum.AUDIT_PASS.getCode()), "当前账号状态不能删除");
        user.deleteById(userId);
        return SingleResponse.buildSuccess();
    }
}
