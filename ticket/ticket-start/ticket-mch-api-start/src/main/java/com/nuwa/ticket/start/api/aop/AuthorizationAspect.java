package com.nuwa.ticket.start.api.aop;

import cn.hutool.core.util.StrUtil;
import com.nuwa.framework.base.UserAware;
import com.nuwa.framework.cola.starter.dto.NuwaCommand;
import com.nuwa.infrastructure.ticket.common.exception.NoLoginException;
import com.nuwa.infrastructure.ticket.enums.ErrorEnum;
import com.nuwa.ticket.start.api.aop.annotation.IgnoreAuth;
import com.nuwa.ticket.start.api.aop.annotation.JWTUtil;
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
 * AuthorizationAspect
 *
 * @author hy
 * @date 2020/11/4 11:16
 * @since 1.0.0
 */
@Slf4j
@Aspect
@Order(-999)
@Component
public class AuthorizationAspect {

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    public AuthorizationAspect() {
        log.info("AuthorizationAspect init");
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
        if (StrUtil.isBlank(token)) {
            throw new NoLoginException(ErrorEnum.NOT_LOGIN_FAILED, "用户未登录异常");
        }

        if (!JWTUtil.verify(token)) {
            throw new NoLoginException(ErrorEnum.NOT_LOGIN_FAILED, "无效的token");
        }

  /*    String tokenId = JWTUtil.getTokenId(token);
        String tokenIdRedis = stringRedisTemplate.opsForValue().get(RedisKeyConstant.REDIS_KEY_TOKEN + ":" + tokenId);
        if (StrUtil.isBlank(tokenIdRedis)) {
            throw new NoLoginException(ErrorEnum.NOT_LOGIN_FAILED, "无效的token");
        }*/

        UserAware userAware = (UserAware) userAwareOptional.get();
        userAware.setMchUserId(JWTUtil.getMchUserId(token));
        userAware.setMchId(JWTUtil.getMchId(token));
        userAware.setUserName(JWTUtil.getUsername(token));
        userAware.setHostIp(getIp(request));
        userAware.setTokenId(JWTUtil.getTokenId(token));
        Optional<Object> nuwaCommandOptional = streamArgs.filter(x -> x instanceof NuwaCommand).findFirst();
        if (nuwaCommandOptional.isPresent()) {
            NuwaCommand command = (NuwaCommand) nuwaCommandOptional.get();
            command.setUserAware(userAware);
        }
    }

    @Pointcut("execution(* com.nuwa.ticket.start.api.controller..*.*(..))")
    public void authParam() {
    }

    private String getIp(HttpServletRequest request) {
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
