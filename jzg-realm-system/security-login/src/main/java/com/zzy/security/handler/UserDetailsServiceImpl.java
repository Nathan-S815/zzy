package com.zzy.security.handler;

import com.zzy.security.lib.dao.UserInfoMapper;
import com.zzy.security.lib.dao.UserRoleMapper;
import com.zzy.security.lib.entity.RoleInfo;
import com.zzy.security.lib.entity.UserInfo;
import com.zzy.security.dto.CustomUser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;


@SuppressWarnings("ALL")
@Service("userDetailsServiceImpl")
public class UserDetailsServiceImpl implements UserDetailsService {

    private static final Logger log = LoggerFactory.getLogger(UserDetailsServiceImpl.class);

    @Autowired
    private UserInfoMapper userInfoMapper;

    @Autowired
    private UserRoleMapper userRoleMapper;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserInfo user = userInfoMapper.selectByUsrename(username);
        if(user==null || user.getIsDelete()==1) {
//            throw new UsernameNotFoundExceion(String.format("No user found with username '%s'", username));
            throw new BadCredentialsException("用户名或密码错误", new Throwable());
        }else {
            return create(user);
        }
    }


    private UserDetails create(UserInfo user) {
        List<RoleInfo> list = null;
        try {
             list = userRoleMapper.selectRolesByUserId(user.getId());
        } catch (Exception e) {
            log.info("custom user create exceion:{}",e.getMessage());
        }
        if(list==null || list.size()<1){
            list = new ArrayList<>();
        }
        return new CustomUser(user,list,true);
    }
}
