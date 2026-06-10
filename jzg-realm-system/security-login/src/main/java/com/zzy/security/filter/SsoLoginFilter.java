package com.zzy.security.filter;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.StrUtil;
import com.zzy.core.dto.R;
import com.zzy.security.common.JwtUtil;
import com.zzy.security.dto.CustomUser;
import com.zzy.security.lib.entity.SsoLoginAuth;
import com.zzy.security.service.SSoService;
import com.zzy.security.service.SsoLoginClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

//@Component
public class SsoLoginFilter implements Filter {

    private static final Logger log = LoggerFactory.getLogger(SsoLoginFilter.class);


//    @AutowiredWW
    private SSoService sSoService;


//    @Value("${jwt.header}")
    private String tokenHeader;

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse resp = (HttpServletResponse) response;
        String path = req.getServletPath();
        if (path.startsWith("/ssoAuth")) {
            String username = request.getParameter("username");
            String appkey = request.getParameter("appkey");
            String secret = request.getParameter("secret");
            PrintWriter out = null;
            out = response.getWriter();
            R r = null;
            if(StrUtil.isBlankOrUndefined(username)
            ||StrUtil.isBlankOrUndefined(appkey)
             ||StrUtil.isBlankOrUndefined(secret)
            ){
                r = R.error("params illegal");
                out.write(r.toString());
                out.flush();
                return;
            }
//            Date time = DateUtil.date(Long.parseLong(timestamp));
//            if(!DateUtil.offsetMinute(time,35).isAfter(new Date())){
//                r = R.error("date expired");
//                out.write(r.toString());
//                out.flush();
//                return;
//            }
            SsoLoginAuth la = sSoService.findSsoAuthByUsername(username);
            if(la==null){
                r = R.error("params illegal");
                out.write(r.toString());
                out.flush();
                return;
            }
            if(!la.getAppKey().equals(appkey)){
                r = R.error("appKey not match");
                out.write(r.toString());
                out.flush();
                return;
            }
            if(!la.getSecretKey().equals(secret)){
                r = R.error("secret not match");
                out.write(r.toString());
                out.flush();
                return;
            }
//            String dbsign = SsoLoginClient.getSSOSign(la.getAppKey(),la.getSecretKey(),timestamp);
//            log.info(dbsign);
//            if(!dbsign.equals(sign)){
//                r = R.error("sign error");
//                out.write(r.toString());
//                out.flush();
//                return;
//            }
            HttpSession session = ((HttpServletRequest) request).getSession(true);
            List<SimpleGrantedAuthority> li = new ArrayList<>();
            li.add(new SimpleGrantedAuthority("ROLE_ADMIN"));
            li.add(new SimpleGrantedAuthority("ROLE_USER"));
            UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(username,null,li);
            SecurityContext context = SecurityContextHolder.createEmptyContext();
            context.setAuthentication(authentication);
            SecurityContextHolder.setContext(context);
            session.setAttribute("SPRING_SECURITY_CONTEXT", SecurityContextHolder.getContext());
            CustomUser us = new CustomUser(username, Arrays.asList("ROLE_ADMIN","ROLE_USER"));
            String token = JwtUtil.generateToken(us);
            session.setAttribute("sessionToken"+username, "Bearer "+token);
            try {
                String service = request.getParameter("service");
                String page = null;
                if("cyzhjc".equals(service)){
                    page = "#/index";
                }
//                StringBuilder sb = new StringBuilder("http://60.163.192.3:8081/");
                StringBuilder sb = new StringBuilder("http://<PUBLIC_HOST>:8081/");
                if(StrUtil.isNotBlank(page)){
                    sb.append(page);
                }
                resp.sendRedirect(sb.toString());
                return;
            } catch (Exception e) {
                log.debug("PrintWriter IOException:{}",e.getMessage());
            } finally {
                if (out != null) {
                    out.close();
                }


            }
        }
        chain.doFilter(req, resp);
    }

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void destroy() {

    }

}
