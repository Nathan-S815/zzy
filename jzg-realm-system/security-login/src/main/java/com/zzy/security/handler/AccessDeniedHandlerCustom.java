package com.zzy.security.handler;

import com.zzy.core.dto.R;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@Component("accessDeniedHandlerCustom")
public class AccessDeniedHandlerCustom implements AccessDeniedHandler {


    @Override
    public void handle(HttpServletRequest httpServletRequest, HttpServletResponse response, AccessDeniedException e) throws IOException, ServletException {
        //登陆状态下，权限不足执行该方法
        response.setStatus(200);
        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/json; charset=utf-8");
        PrintWriter printWriter = response.getWriter();
        String body = R.error("permission denied").toString();
        printWriter.write(body);
        printWriter.flush();
    }






}
