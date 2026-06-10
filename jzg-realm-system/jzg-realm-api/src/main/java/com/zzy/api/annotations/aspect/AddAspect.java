package com.zzy.api.annotations.aspect;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

import cn.hutool.http.HttpUtil;
import com.alibaba.fastjson.JSON;
import com.zzy.api.annotations.AddLog;
import com.zzy.security.common.JwtUtil;
import com.zzy.security.dto.CustomUser;
import org.apache.http.params.CoreConnectionPNames;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@Component
@Aspect
public class AddAspect {
    @Pointcut("@annotation(com.zzy.api.annotations.AddLog)")
    private void MyValid() {
    }


    @After("MyValid()")
    public void after(JoinPoint joinPoint) {
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();

        CustomUser loginUser = JwtUtil.getFromJwt(request);
        Integer userId = loginUser.getUserId();

        //请求的IP
        String ip = request.getRemoteAddr();

        try {

            String targetName = joinPoint.getTarget().getClass().getName();
            String methodName = joinPoint.getSignature().getName();
            Object[] arguments = joinPoint.getArgs();
            Class targetClass = Class.forName(targetName);
            Method[] methods = targetClass.getMethods();
            String operContent = "";
            for (Method method : methods) {
                if (method.getName().equals(methodName)) {
                    Class[] clazzs = method.getParameterTypes();
                    if (clazzs.length == arguments.length) {
                        operContent = method.getAnnotation(AddLog.class).operContent();
                        break;
                    }
                }
            }
            String url = "http://172.20.18.3:7777/loginCenter/sysLog/insert";
            Map<String ,Object> param = new HashMap<>();
            param.put("operUser","26d4612ef26611eabdb09afa7f8fbd05");
            param.put("logType",2);
            param.put("operType",3);
            param.put("logPlatform",2);
            param.put("operContent",operContent);
            param.put("remoteIp",ip);
            HttpUtil.createPost(url).form("operUser","26d4612ef26611eabdb09afa7f8fbd05").body(JSON.toJSONString(param)).setConnectionTimeout(3000).execute();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
