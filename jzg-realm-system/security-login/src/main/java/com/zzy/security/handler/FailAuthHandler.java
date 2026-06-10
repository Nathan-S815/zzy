package com.zzy.security.handler;

import com.zzy.core.dto.R;
import com.zzy.core.utils.CookieUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;


@Component("failAuthHandler")
public class FailAuthHandler  implements AuthenticationFailureHandler {


    private final static Logger log = LoggerFactory.getLogger(FailAuthHandler.class);


    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException e) throws IOException, ServletException {
        SecurityContextHolder.clearContext();
        CookieUtil.removeCookie(request, response, "username");
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        PrintWriter printWriter = response.getWriter();
        String body = R.error(e.getMessage()).toString();
        printWriter.write(body);
        printWriter.flush();
    }
}
