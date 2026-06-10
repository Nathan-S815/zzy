package com.zzy.security.handler;

import cn.hutool.core.util.ArrayUtil;
import com.alibaba.fastjson.JSON;
import com.zzy.core.dto.R;
import com.zzy.security.annotations.RequiredPermission;
import com.zzy.security.common.JwtUtil;
import com.zzy.security.dto.CustomUser;
import com.zzy.security.lib.dao.PermissionInfoMapper;
import com.zzy.security.lib.entity.PermissionInfo;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;


@Component
public class SecurityInterceptor implements HandlerInterceptor {


    @Autowired
    private PermissionInfoMapper permissionInfoMapper;


    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        // 验证权限
        if (this.hasPermission(handler,request)) {
            return true;
        }
        // 如果没有权限 则抛403异常 springboot会处理，跳转到 /error/403 页面
        response.setStatus(HttpStatus.OK.value());
        response.setContentType("application/json;charset=UTF-8");
        response.getWriter().append(R.error("no permission").toString());
        return false;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {

    }


    /**
     * 是否有权限
     *
     * @param handler
     * @return
     */
    private boolean hasPermission(Object handler,HttpServletRequest request) {
        if (handler instanceof HandlerMethod) {
            HandlerMethod handlerMethod = (HandlerMethod) handler;
            // 获取方法上的注解
            RequiredPermission requiredPermission = handlerMethod.getMethod().getAnnotation(RequiredPermission.class);
            // 如果方法上的注解为空 则获取类的注解
            if (requiredPermission == null) {
                requiredPermission = handlerMethod.getMethod().getDeclaringClass().getAnnotation(RequiredPermission.class);
            }
            // 如果标记了注解，则判断权限
            if (requiredPermission != null) {
                if(StringUtils.isNotBlank(requiredPermission.hasPermission())){
                    CustomUser cu = JwtUtil.getFromJwt(request);
                    if(cu==null) return false;
                    List<PermissionInfo> pers = permissionInfoMapper.selectPermissionByUserName(cu.getUsername());
                    // redis或数据库 中获取该用户的权限信息 并判断是否有权限
                    Set<String> permissionSet = pers.stream().map(o->o.getPermissionUrl()).collect(Collectors.toSet());
                    if (CollectionUtils.isEmpty(permissionSet) ){
                        return false;
                    }
                    return permissionSet.contains(requiredPermission.hasPermission());
                }
                if(StringUtils.isNotBlank(requiredPermission.hasRole())){
                    CustomUser cu = JwtUtil.getFromJwt(request);
                    if(cu==null) return false;
                    String roles = JSON.toJSONString(cu.getAuthorities());
                    if(roles.contains("ADMIN")){
                        return true;
                    }
                    if(roles.contains("ROOT")){
                        return true;
                    }
                    String tmp = requiredPermission.hasRole();
                    return roles.contains(tmp);
                }
                if(!ArrayUtil.isEmpty(requiredPermission.hasAnyRole())){
                    CustomUser cu = JwtUtil.getFromJwt(request);
                    if(cu==null) return false;
                    String roles = JSON.toJSONString(cu.getAuthorities());
                    if(roles.contains("ADMIN")){
                        return true;
                    }
                    if(roles.contains("ROOT")){
                        return true;
                    }
                    for (String tmp : requiredPermission.hasAnyRole()) {
                        if(roles.contains(tmp)){
                            return true;
                        }
                    }
                    return false;
                }
                if(StringUtils.isNotBlank(requiredPermission.hasRoleCode())){
                    CustomUser cu = JwtUtil.getFromJwt(request);
                    if(cu==null) return false;
                    if(cu.getAuths().contains("ROOT")){
                        return true;
                    }
                    return cu.getRoleCode().contains(requiredPermission.hasRoleCode());
                }
            }
        }
        return true;
    }



}
