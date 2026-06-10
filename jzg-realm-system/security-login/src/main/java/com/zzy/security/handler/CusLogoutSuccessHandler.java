package com.zzy.security.handler;

import com.zzy.core.dto.R;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

public class CusLogoutSuccessHandler  implements LogoutSuccessHandler {

    @Override
    public void onLogoutSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        PrintWriter printWriter = response.getWriter();
        response.setHeader("Content-Type", "application/json;charset=utf8");
        response.getWriter().append(R.ok("logout success").toString());
        response.sendRedirect("http://localhost:8084/");
    }
}
