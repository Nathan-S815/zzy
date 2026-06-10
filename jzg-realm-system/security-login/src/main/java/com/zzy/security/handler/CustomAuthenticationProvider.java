package com.zzy.security.handler;

import com.zzy.core.utils.AuthUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;



@Component("customAuthenticationProvider")
public class CustomAuthenticationProvider implements AuthenticationProvider {


    @Autowired
    @Qualifier("userDetailsServiceImpl")
    private UserDetailsService userDetailsService;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String username = authentication.getName();
        String oripassword = (String) authentication.getCredentials();
        String password = AuthUtil.getSaltedPwd(oripassword);
        UserDetails user = userDetailsService.loadUserByUsername(username);
        if(user==null || !user.getPassword().equals(password)) {
            throw new BadCredentialsException("用户名或密码错误", new Throwable());
        }
        if(!user.isEnabled()){
            throw new DisabledException("账户被禁用",new Throwable());
        }
        return new UsernamePasswordAuthenticationToken(user, password, user.getAuthorities());
    }

    @Override
    public boolean supports(Class<?> aClass) {
        return true;
    }
}
