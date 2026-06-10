package com.nuwa.hotel.start.api.aop;

import cn.hutool.core.util.StrUtil;
import com.nuwa.framework.base.UserAware;
import com.nuwa.framework.cola.starter.dto.NuwaCommand;
import com.nuwa.infrastructure.ticket.common.exception.NoLoginException;
import com.nuwa.infrastructure.ticket.enums.ErrorEnum;
import com.nuwa.hotel.start.api.aop.annotation.IgnoreAuth;
import com.nuwa.hotel.start.api.constants.RedisKeyConstant;
import com.nuwa.hotel.start.api.util.MemberJwtUtil;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Stream;

/**
 * UserAwareAspect
 *
 * @author hy
 * @date 2020/11/4 11:16
 * @since 1.0.0
 */
@Slf4j
@Aspect
@Order(-999) // 控制多个Aspect的执行顺序，越小越先执行
@Component
public class UserAwareAspect {

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    public UserAwareAspect() {
        log.info("UserAwareAspect init");
    }

    @Before("authParam()")
    public void before(JoinPoint point) {
        Method method = ((MethodSignature) point.getSignature()).getMethod();
        IgnoreAuth ignoreAuth = method.getAnnotation(IgnoreAuth.class);
        if (Objects.nonNull(ignoreAuth)) {
            return;
        }

        Stream<Object> streamArgs = Arrays.stream(point.getArgs());
        Optional<Object> userAwareOptional = Arrays.stream(point.getArgs()).filter(x -> x instanceof UserAware).findFirst();
        boolean isNeedAuth = userAwareOptional.isPresent();
        if (!isNeedAuth) {
            return;
        }

        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();
        String token = request.getHeader("Authorization");
        if (StrUtil.isBlank(token)) {
            token = request.getParameter("Authorization");
        }
        log.info("token:[{}]", token);
        if (StrUtil.isBlank(token)) {
            throw new NoLoginException(ErrorEnum.NOT_LOGIN_FAILED, "用户未登录异常");
        }

        if (!MemberJwtUtil.verify(token)) {
            throw new NoLoginException(ErrorEnum.NOT_LOGIN_FAILED, "无效的token");
        }

        String tokenId = MemberJwtUtil.getTokenId(token);
        String tokenIdRedis = stringRedisTemplate.opsForValue().get(RedisKeyConstant.REDIS_KEY_TOKEN + ":" + tokenId);
        if (StrUtil.isBlank(tokenIdRedis)) {
            throw new NoLoginException(ErrorEnum.NOT_LOGIN_FAILED, "token已过期");
        }

        UserAware userAware = (UserAware) userAwareOptional.get();
        userAware.setUserId(MemberJwtUtil.getUserId(token));
        userAware.setMchId(MemberJwtUtil.getMchId(token));
        userAware.setUserName(MemberJwtUtil.getUserName(token));
        userAware.setHostIp(getIP(request));
        Optional<Object> nuwaCommandOptional = streamArgs.filter(x -> x instanceof NuwaCommand).findFirst();
        if (nuwaCommandOptional.isPresent()) {
            NuwaCommand command = (NuwaCommand) nuwaCommandOptional.get();
            command.setUserAware(userAware);
        }
    }

    @Pointcut("execution(* com.nuwa.hotel.start.api.controller..*.*(..))")
    public void authParam() {
    }

    private String getIP(HttpServletRequest request) {
        String ip = request.getHeader("x-forwarded-for");
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("WL-Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("X-Real-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
        }
        return ip;
    }
}
