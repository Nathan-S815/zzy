package com.zzy.security.filter;

import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
@Order(1)
public class AllowOriginFilter extends CorsFilter {


    public AllowOriginFilter(CorsConfigurationSource configSource) {
        super(configSource);
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        response.setHeader("Access-Control-Allow-Origin", "*");
        response.setHeader("Access-Control-Allow-Methods", "POST,GET,PUT,OPTIONS,DELETE");
        response.setHeader("Access-Control-Max-Age", "3600");
        response.setHeader("Access-Control-Allow-Headers", "Origin,X-Requested-With,Content-Type,Accept,Authorization,token");
        response.setHeader("Access-Control-Allow-Headers", "*");
        response.setHeader("Access-Control-Allow-Credentials", "true");
        String Authorization=request.getHeader("Authorization");
        filterChain.doFilter(request,response);
    }


    @Override
    public void destroy() {}


}
