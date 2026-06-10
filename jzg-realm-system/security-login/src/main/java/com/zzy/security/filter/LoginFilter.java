package com.zzy.security.filter;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.crypto.digest.DigestUtil;
import com.zzy.core.dto.R;
import com.zzy.core.utils.AuthUtil;
import com.zzy.core.utils.KaptchaUtil;
import com.zzy.security.lib.dao.KaptchaInfoMapper;
import com.zzy.security.lib.entity.KaptchaInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.stereotype.Component;

import javax.servlet.Filter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Date;


public class LoginFilter extends UsernamePasswordAuthenticationFilter {

    private AuthenticationManager authenticationManager;
    private KaptchaInfoMapper kaptchaInfoMapper;


    public LoginFilter(AuthenticationManager authenticationManager, AuthenticationSuccessHandler successHandler, AuthenticationFailureHandler failHandler) {
        this.authenticationManager = authenticationManager;
        this.setAuthenticationFailureHandler(failHandler);
        this.setAuthenticationSuccessHandler(successHandler);
    }





    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)
            throws AuthenticationException {
        String loginType = request.getHeader("loginType");
        String name = null;
        String password = null;
        if("h5".equals(loginType)){
            String departId = request.getParameter("departId");
            String memberId = request.getParameter("memberId");
            if(StrUtil.isBlankOrUndefined(departId) || StrUtil.isBlankOrUndefined(memberId)){
                response.setStatus(200);
                response.setContentType("application/json");
                response.setCharacterEncoding("UTF-8");
                PrintWriter printWriter = null;
                try {
                    printWriter = response.getWriter();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                String body = R.ok("参数不能空").toString();
                printWriter.write(body);
                printWriter.flush();
                return null;
            }
            name = departId+memberId;
            password = DigestUtil.md5Hex("123456");
        }else{
            String valiCode = request.getParameter("validateCode");
            String valideRes = null;
            if(StrUtil.isBlankOrUndefined(valiCode)){
                valideRes = "验证码不能为空";
            }else{
                String token = request.getHeader("capToken");
                KaptchaInfo ki = kaptchaInfoMapper.selectByToken(token);
                if(ki==null || ki.getCreateTime().before(DateUtil.offsetMinute(new Date(),-5))){
                    valideRes = "验证码过期请刷新";
                }else if(!ki.getCapText().equalsIgnoreCase(valiCode)){
                    valideRes = "验证码不匹配";
                }else {
                    valideRes = "true";
                }
            }
//            valideRes = "true";
            if(!"true".equals(valideRes)){
                try {
                    response.setContentType("application/json");
                    response.setCharacterEncoding("UTF-8");
                    PrintWriter printWriter = response.getWriter();
                    String body = R.error(valideRes).toString();
                    printWriter.write(body);
                    printWriter.flush();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                return null;
            }
            name = request.getParameter("username");
            password = request.getParameter("password");
            if(StrUtil.isBlankOrUndefined(name)){
                try {
                    response.setStatus(200);
                    response.setContentType("application/json");
                    response.setCharacterEncoding("UTF-8");
                    PrintWriter printWriter = response.getWriter();
                    String body = R.ok("用户名不能空").toString();
                    printWriter.write(body);
                    printWriter.flush();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                return null;
            }
        }
        return authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(name, password, new ArrayList<>()));
    }

    public LoginFilter setKapMapper(KaptchaInfoMapper kaptchaInfoMapper) {
        this.kaptchaInfoMapper = kaptchaInfoMapper;
        return this;
    }
}
