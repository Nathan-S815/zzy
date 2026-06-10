package com.nuwa.attract.start.api.controller.auth;

import java.awt.image.BufferedImage;
import java.util.concurrent.TimeUnit;

import javax.annotation.Resource;
import javax.imageio.ImageIO;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cn.hutool.core.lang.Assert;
import cn.hutool.core.util.RandomUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.crypto.digest.MD5;
import cn.hutool.json.JSONUtil;
import com.alibaba.cola.dto.SingleResponse;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.google.code.kaptcha.impl.DefaultKaptcha;
import com.nuwa.app.attract.command.Auth.CreateMerchantCmdExe;
import com.nuwa.attract.start.api.aop.annotation.IgnoreAuth;
import com.nuwa.attract.start.api.aop.annotation.JWTUtil;
import com.nuwa.attract.start.api.config.User;
import com.nuwa.attract.start.api.constants.RedisKeyConstant;
import com.nuwa.attract.start.api.controller.auth.param.LoginParam;
import com.nuwa.attract.start.api.controller.auth.param.ModifyPasswordParam;
import com.nuwa.attract.start.api.controller.auth.param.ModifyUserInfoParam;
import com.nuwa.attract.start.api.util.IpAddressUtil;
import com.nuwa.attract.start.api.util.MemberJwtUtil;
import com.nuwa.client.attract.dto.clientobject.auth.RegisteredCmd;
import com.nuwa.client.zeus.dto.clientobject.auth.LoginCmd;
import com.nuwa.client.zeus.dto.domainevent.sms.LoginSucceededEvent;
import com.nuwa.framework.base.UserAware;
import com.nuwa.framework.shiro.starter.JWTToken;
import com.nuwa.infrastructure.attract.database.user.entity.AttractUser;
import com.nuwa.infrastructure.attract.database.user.mapper.AttractUserMapper;
import com.nuwa.infrastructure.attract.database.user.service.AttractUserService;
import com.nuwa.infrastructure.enums.UserReviewStatusEnum;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * 认证
 *
 * @author nanHuang @南皇
 * @version com.nuwa.attract.start.api.controller.auth:AuthController.java,v1.0.0 2022-09-06 09:38:46 nanHuang Exp $
 */
@Api(tags = {"登录注册"})
@RestController
@RequestMapping("/auth")
public class AuthController {
    @Resource
    private StringRedisTemplate  stringRedisTemplate;
    @Resource
    private DefaultKaptcha       defaultKaptcha;
    @Resource
    private CreateMerchantCmdExe createMerchantCmdExe;
    @Resource
    private AttractUserService   attractUserService;
    @Autowired
    private AttractUserMapper attractUserMapper;

    @ApiOperation(value = "注册")
    @IgnoreAuth
    @RequestMapping(value = "/registered", method = RequestMethod.POST)
    public SingleResponse<?> registered(@RequestBody @Validated RegisteredCmd cmd) throws Exception {
        return createMerchantCmdExe.execute(cmd);
    }

    @ApiOperation(value = "登录")
    @IgnoreAuth
    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public SingleResponse<?> login(@RequestBody @Validated LoginParam param, HttpServletRequest request)
        throws Exception {

        QueryWrapper<AttractUser> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("username",param.getUsername());
        queryWrapper.eq("delete_flag","0");
        AttractUser user = attractUserMapper.selectOne(queryWrapper);
    //    AttractUser user = attractUserService.getByUserName(param.getUsername());
        Assert.notNull(user, "该用户不存在");
        String password = MD5.create().digestHex(param.getPassword(), "utf-8");
        Assert.isTrue(user.getPassword().equals(password), "密码不正确");
        Assert.isTrue(!user.getReviewStatus().equals(UserReviewStatusEnum.BAN.getCode()), "当前账号已被禁用");
        Assert.isTrue(user.getReviewStatus().equals(UserReviewStatusEnum.AUDIT_PASS.getCode()), "当前账号未审核通过");
        Assert.isTrue(param.getAccountType().equals(user.getAccountType()), "账号类型不正确");
        String code = stringRedisTemplate.opsForValue().get(param.getUuid());
        Assert.isTrue(param.getCode().equals(code), "验证码不正确");
        String tokenStr = MemberJwtUtil.sign(user.getUserId(), user.getUsername(), user.getAccountType());
        stringRedisTemplate.opsForValue().set(RedisKeyConstant.REDIS_KEY_TOKEN + ":" + JWTUtil.getTokenId(tokenStr),
            tokenStr, JWTUtil.EXPIRE_HOUR_TIME, TimeUnit.HOURS);
        //清除code
        stringRedisTemplate.delete(param.getUuid());
        return SingleResponse.of(tokenStr);
    }

    @ApiOperation(value = "获取验证码-传入uuid唯一键,登录/注册的时候传回uuid校验")
    @IgnoreAuth
    @RequestMapping(value = "/captcha", method = RequestMethod.GET)
    public void getImageCode(HttpServletResponse response, @RequestParam("uuid") String uuid) throws Exception {
        //禁止缓存
        response.setDateHeader("Expires", 0);
        response.setHeader("Cache-Control", "no-store, no-cache, must-revalidate");
        response.addHeader("Cache-Control", "post-check=0, pre-check=0");
        response.setHeader("Pragma", "no-cache");
        //设置响应格式为png图片
        response.setContentType("image/png");

        //为验证码创建一个文本
        String codeText = RandomUtil.randomNumbers(4);
        //将验证码存到redis
        stringRedisTemplate.opsForValue().set(uuid, codeText, 60 * 1000, TimeUnit.SECONDS);
        // 用创建的验证码文本生成图片
        BufferedImage bi = defaultKaptcha.createImage(codeText);
        ServletOutputStream out = response.getOutputStream();
        //写出图片
        ImageIO.write(bi, "png", out);
        try {
            out.flush();
        } finally {
            out.close();
        }
    }

    @ApiOperation(value = "获取当前用户信息")
    @RequestMapping(value = "/getUserInfo", method = RequestMethod.GET)
    public SingleResponse<AttractUser> getUserInfo(UserAware userAware) throws Exception {
        return SingleResponse.of(attractUserService.getById(userAware.getUserId()));
    }

    @ApiOperation(value = "修改当前用户资料")
    @RequestMapping(value = "/modifyUserInfo", method = RequestMethod.POST)
    public SingleResponse<?> modifyUserInfo(@RequestBody @Validated ModifyUserInfoParam param, UserAware userAware) throws Exception {
        AttractUser attractUser = attractUserService.getById(userAware.getUserId());
        BeanUtils.copyProperties(param, attractUser);
        attractUser.updateById();
        return SingleResponse.buildSuccess();
    }

    @ApiOperation(value = "修改当前用户密码")
    @RequestMapping(value = "/modifyPassword", method = RequestMethod.POST)
    public SingleResponse<?> modifyPassword(@RequestBody @Validated ModifyPasswordParam param, UserAware userAware) throws Exception {
        AttractUser attractUser = attractUserService.getById(userAware.getUserId());
        String password = MD5.create().digestHex(param.getLastPassword(), "utf-8");
        Assert.isTrue(attractUser.getPassword().equals(password), "密码不正确");
        String nextPassword = MD5.create().digestHex(param.getNextPassword(), "utf-8");
        attractUser.setPassword(nextPassword);
        attractUser.updateById();
        return SingleResponse.buildSuccess();
    }
}
