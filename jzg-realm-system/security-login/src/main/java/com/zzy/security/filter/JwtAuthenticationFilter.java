package com.zzy.security.filter;

import cn.hutool.core.util.StrUtil;
import com.zzy.core.utils.CookieUtil;
import com.zzy.security.common.JwtUtil;
import com.zzy.security.dto.CustomUser;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.security.web.authentication.preauth.PreAuthenticatedAuthenticationToken;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;



@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    @Value("${jwt.header}")
    private String tokenHeader;

    private static final String appOrginToken = "8d969eef6ecad3c29a3a629280e686cf0c3f5d5a86aff3ca12020c923adc6c92";


    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws ServletException, IOException {
//        Cookie cookie = CookieUtil.getCookie(request, "username");
//        if (cookie == null) {
//            SecurityContextHolder.clearContext();
//            chain.doFilter(request, response);
//            return;
//        }
        String appToken = request.getHeader("appToken");
        if(!StrUtil.isBlankOrUndefined(appToken)){
            if(appOrginToken.equals(appToken)) {
                UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken("test1", null, null);
                authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                logger.info(String.format("App-Authenticated user %s, setting security context", "小程序"));
                SecurityContextHolder.getContext().setAuthentication(authentication);
                chain.doFilter(request, response);
                return;
            }
        }
        String auth_token = request.getHeader(tokenHeader);
        if(StrUtil.isBlankOrUndefined(auth_token)){
            HttpSession session =  request.getSession(true);
            if(session!=null){
                SecurityContext ctx = (SecurityContext) session.getAttribute("SPRING_SECURITY_CONTEXT");
                if(ctx!=null &&ctx.getAuthentication()!=null){
                    auth_token = (String) session.getAttribute("sessionToken"+ctx.getAuthentication().getName());
                }

            }

        }
        final String auth_token_start = "Bearer ";
        if (StrUtil.isNotEmpty(auth_token) && auth_token.startsWith(auth_token_start)) {
            auth_token = auth_token.substring(auth_token_start.length());
        }else{
            SecurityContextHolder.clearContext();
            chain.doFilter(request, response);
            return;
        }
        CustomUser user = JwtUtil.getUserFromToken(auth_token);
        if(user==null) {
//            CookieUtil.removeCookie(request,response,"username");
            SecurityContextHolder.clearContext();
            chain.doFilter(request, response);
            return;
        }
        String username = JwtUtil.getUsernameFromToken(auth_token);
//        logger.info(String.format("Checking authentication for user %s.", username));
        if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            if (JwtUtil.validateToken(auth_token, user)) {
                user.setEnable(true);
                UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(user, null, user.getAuthorities());
                authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
//                logger.info(String.format("Authenticated user %s, setting security context", username));
                SecurityContextHolder.getContext().setAuthentication(authentication);
            }else{
//                CookieUtil.removeCookie(request,response,"username");
                SecurityContextHolder.clearContext();
                chain.doFilter(request, response);
                return;
            }
        }
        if(StrUtil.isNotBlank(username) && SecurityContextHolder.getContext().getAuthentication()!=null){
            JwtUtil.refreshToken(auth_token);
        }
        chain.doFilter(request, response);
    }


}
