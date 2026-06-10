package com.zzy.security.handler;

import cn.hutool.core.util.StrUtil;
import com.zzy.core.utils.CookieUtil;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.LogoutHandler;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class LogOutHandlers implements LogoutHandler {

    @Override
    public void logout(HttpServletRequest request, HttpServletResponse response, Authentication authentication) {
//        CookieUtil.removeCookie(request, response, "username");
        String auth_token = request.getHeader("Authorization");
        HttpSession session = request.getSession(true);
        if(StrUtil.isBlankOrUndefined(auth_token)){
            if(session!=null){
                SecurityContext ctx = (SecurityContext) session.getAttribute("SPRING_SECURITY_CONTEXT");
                if(ctx!=null &&ctx.getAuthentication()!=null){
                    session.removeAttribute("sessionToken"+ctx.getAuthentication().getName());
                    session.removeAttribute("SPRING_SECURITY_CONTEXT");
                }

            }

        }
        SecurityContextHolder.clearContext();
    }


}
