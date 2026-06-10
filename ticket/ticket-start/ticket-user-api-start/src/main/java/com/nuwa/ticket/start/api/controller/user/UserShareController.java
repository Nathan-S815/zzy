package com.nuwa.ticket.start.api.controller.user;

import cn.hutool.core.util.StrUtil;
import com.alibaba.cola.dto.SingleResponse;
import com.alibaba.cola.exception.Assert;
import com.nuwa.framework.base.UserAware;
import com.nuwa.infrastructure.ticket.database.member.entity.Member;
import com.nuwa.infrastructure.ticket.database.member.service.MemberService;
import com.nuwa.infrastructure.ticket.database.order.entity.ChannelPaymentOrder;
import com.nuwa.infrastructure.ticket.database.order.entity.TicketOrder;
import com.nuwa.infrastructure.ticket.enums.ChannelPaymentOrderEnum;
import com.nuwa.infrastructure.ticket.enums.TicketOrderEnum;
import com.nuwa.ticket.start.api.constants.RedisKeyConstant;
import com.nuwa.ticket.start.api.controller.order.param.OrderCancelParam;
import com.nuwa.ticket.start.api.controller.user.param.BindShareUserParam;
import com.nuwa.ticket.start.api.controller.user.param.UpdateShareCodeParam;
import com.nuwa.ticket.start.api.util.MemberJwtUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Date;
import java.util.concurrent.TimeUnit;

/**
 * @author hy
 */
@Api(tags = {"C端分享相关"})
@Slf4j
@RestController
@RequestMapping("/share")
public class UserShareController {

    @Autowired
    private MemberService memberService;

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @ApiOperation(value = "设置用户分享码")
    @PostMapping(value = "/updateShareCode")
    public SingleResponse<?> updateShareCode(@Validated @RequestBody UpdateShareCodeParam param, UserAware userAware) {
        boolean update = memberService.lambdaUpdate()
                .set(Member::getShareCode, param.getShareCode())
                .eq(Member::getUserId, userAware.getUserId())
                .update();
        return SingleResponse.buildSuccess();
    }

    @ApiOperation(value = "获取用户分享码")
    @GetMapping(value = "/getShareCode")
    public SingleResponse<?> getShareCode(UserAware userAware) {
        Member member = memberService.getById(userAware.getUserId());
        return SingleResponse.of(member.getShareCode());
    }

    @ApiOperation(value = "绑定分享人")
    @RequestMapping(value = "/bindShareUser", method = RequestMethod.POST)
    @ResponseBody
    public SingleResponse<?> bindShareUser(@RequestBody @Valid BindShareUserParam param, UserAware userAware) {
        log.info(">>>> param  [{}]", param);
        Long userId = userAware.getUserId();
        if (!StrUtil.isNullOrUndefined(param.getShareCode())) {
            stringRedisTemplate.opsForValue().set(RedisKeyConstant.REDIS_KEY_SHARE_USER + ":" + userId, param.getShareCode(), 1, TimeUnit.HOURS);
        }
        return SingleResponse.buildSuccess();
    }

}
