package com.nuwa.app.attract.command.Auth;

import javax.annotation.Resource;

import cn.hutool.core.lang.Assert;
import cn.hutool.crypto.digest.MD5;
import com.alibaba.cola.dto.SingleResponse;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.nuwa.client.attract.dto.clientobject.auth.RegisteredCmd;
import com.nuwa.framework.cola.starter.cmd.AbstractCmdExe;
import com.nuwa.infrastructure.attract.database.user.entity.AttractUser;
import com.nuwa.infrastructure.attract.database.user.mapper.AttractUserMapper;
import com.nuwa.infrastructure.attract.database.user.service.AttractUserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.util.List;

/**
 * 注册用户exe
 *
 * @author nanHuang @南皇
 * @version 2022/9/6 10:57:13 nanHuang Exp $
 */
@Slf4j
@Component
public class CreateMerchantCmdExe extends AbstractCmdExe<RegisteredCmd, SingleResponse<?>> {

    @Resource
    private AttractUserService  attractUserService;
    @Resource
    private StringRedisTemplate stringRedisTemplate;
    @Autowired
    private AttractUserMapper attractUserMapper;
    @Override
    protected SingleResponse<?> handle(RegisteredCmd param) {

        QueryWrapper<AttractUser> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("username", param.getUsername());
        // 判断用户名状态，审核拒绝可再次注册, 删除原审核账户
        List<AttractUser> attractUserList = attractUserMapper.selectList(queryWrapper);
        if (!CollectionUtils.isEmpty(attractUserList)) {
            for (AttractUser attractUser : attractUserList) {
                if (attractUser.getReviewStatus().equals(3) || attractUser.getReviewStatus().equals(0) || attractUser.getReviewStatus().equals(1)) {
                    Assert.isNull(param.getUsername(), "该用户名已存在");
                }
                if (attractUser.getUsername().equals(param.getUsername()) && attractUser.getReviewStatus().equals(2)) {
                    attractUser.setDeleteFlag(1);
                    attractUserMapper.updateById(attractUser);
                }
            }
        }


        String code = stringRedisTemplate.opsForValue().get(param.getUuid());
        Assert.isTrue(param.getCode().equals(code), "验证码不正确");
        AttractUser newUser = new AttractUser();
        BeanUtils.copyProperties(param, newUser);
        newUser.setPassword(MD5.create().digestHex(param.getPassword(), "utf-8"));
        newUser.insert();
        //清除code
        stringRedisTemplate.delete(param.getUuid());
        return SingleResponse.buildSuccess();
    }
}
