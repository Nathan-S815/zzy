package com.zzy.security.handler;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.zzy.core.dto.R;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;

@Component
public class CustomAuthenticationEntryPoint implements AuthenticationEntryPoint {
    @Autowired
    private ObjectMapper objectMapper;
    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException e) throws IOException, ServletException {
        String path = request.getServletPath();
        response.setStatus(302);
        response.setContentType("application/json;charset=UTF-8");
        response.getWriter().append(objectMapper.writeValueAsString(R.error("no auth")));
//        response.sendRedirect("http://localhost:8081/");
    }
}
