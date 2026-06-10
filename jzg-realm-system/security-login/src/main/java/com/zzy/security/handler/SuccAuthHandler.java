package com.zzy.security.handler;

import cn.hutool.core.util.StrUtil;
import com.alibaba.fastjson.JSON;
import com.zzy.security.dto.MerchantInfo;
import com.zzy.security.dto.UserLoginInfo;
import com.zzy.security.lib.dao.UserLoginLogMapper;
import com.zzy.security.lib.entity.UserLoginLog;
import com.zzy.security.common.JwtUtil;
import com.zzy.security.dto.CustomUser;
import com.zzy.security.log.LoginLog;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.*;


@SuppressWarnings("ALL")
@Component("succAuthHandler")
public class SuccAuthHandler implements AuthenticationSuccessHandler {


    private final static Logger log = LoggerFactory.getLogger(SuccAuthHandler.class);

    @Autowired
    private UserLoginLogMapper userLoginLogMapper;

    @Value("${jwt.header}")
    private String tokenHeader;


    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        CustomUser us = (CustomUser) authentication.getPrincipal();
        String token = JwtUtil.generateToken(us);
        PrintWriter out = null;
//        HttpSession session = ((HttpServletRequest) request).getSession(true);
        try {
            String loginType = request.getHeader("loginType");
//            CookieUtil.setCookie(response, "username", token);
            UserLoginInfo udi = new UserLoginInfo();
            if("h5".equals(loginType)){
                    udi = userLoginLogMapper.selectUserWithDepartmentInfoH5ByUserName(us.getUsername());
                    udi.setAuths(us.getAuths());
                    udi.setLoginState("success");
            }else{
                udi.setAuths(us.getAuths());
                udi.setLoginName(us.getUsername());
                udi.setUserId(us.getUserId());
            }
//            else{
//                udi = userLoginLogMapper.selectUserWithDepartmentInfoPcByUserName(us.getUsername());
//                udi.setAuths(us.getAuths());
//                Map<String,Object> m = new HashMap<>();
//                m.put("userId", us.getUserId());
//                m.put("roles",us.getAuths());
//                List<String> list = userLoginLogMapper.selectMerchantTypeCodeByRoleName(m);
//                if(list!=null && list.size()>0){
//                    MerchantInfo mi = null;
//                    List<MerchantInfo> l = new ArrayList<>();
//                    Map<String,Object> baseId = null;
//                    for (String s : list) {
//                        mi = new MerchantInfo();
//                        mi.setMerchantTypeName(s);
//                        m.put("tableName", s);
//                        baseId = userLoginLogMapper.selectBaseInfoIdByNameAndUserId(m);
//                        mi.setBaseId(Integer.parseInt(String.valueOf(baseId.get("id"))));
//                        mi.setAuditState(Integer.parseInt(String.valueOf(baseId.get("auditState"))));
//                        mi.setFillBase(!StrUtil.isBlankOrUndefined(String.valueOf(baseId.get("baseName"))));
//                        mi.setMerchantName(String.valueOf(baseId.get("baseName")).replace("null", ""));
//                        l.add(mi);
//                    }
//                    udi.setMerchantInfo(l);
//                }
//                us.getAuths()
//            }
            response.setHeader("Access-Control-Allow-Headers", "Origin,X-Requested-With,Content-Type,Accept,Authorization,token");
            response.addHeader("Access-Control-Expose-Headers","Content-Disposition");
            response.addHeader("Access-Control-Expose-Headers",tokenHeader);
            response.addHeader(tokenHeader,"Bearer "+token);
            response.setStatus(200);
            response.setContentType("application/json;charset=UTF-8");
            udi.setLoginState("success");
            response.getWriter().append(JSON.toJSONString(udi));
        } catch (IOException e) {
            log.debug("PrintWriter IOException:{}",e.getMessage());
        } finally {
            if (out != null) {
                out.close();
            }
        }
        SecurityContext context = SecurityContextHolder.createEmptyContext();
        UsernamePasswordAuthenticationToken to = getAuthenticationToken(authentication);
        context.setAuthentication(to);
        SecurityContextHolder.setContext(context);
//        session.setAttribute("SPRING_SECURITY_CONTEXT", SecurityContextHolder.getContext());
//        session.setAttribute("sessionToken"+us.getUsername(), "Bearer "+token);
        try {
            UserLoginLog ua = userLoginLogMapper.selectByUserName(us.getUsername());
            if(ua==null) {
                ua = new UserLoginLog();
                ua.setUserName(us.getUsername());
            }
            ua.setLoginAction("login success");
            ua.setLoginTime(new Date());
            ua.setLoginNumber(ua.getLoginNumber()+1);
            userLoginLogMapper.upsertSelective(ua);
            LoginLog.sendLoginLog(ua);
        } catch (Exception e1) {
            e1.printStackTrace();
            log.debug("操作日志入库失败:{}",e1.getMessage());
        }


    }


    private UsernamePasswordAuthenticationToken getAuthenticationToken(Authentication us) {
        if (us!= null) {
            return new UsernamePasswordAuthenticationToken(us, null, us.getAuthorities());
        } else {
            return null;
        }

    }
}
