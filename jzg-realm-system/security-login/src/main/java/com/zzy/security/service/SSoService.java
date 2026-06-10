package com.zzy.security.service;


import com.zzy.security.lib.dao.SsoLoginAuthMapper;
import com.zzy.security.lib.entity.SsoLoginAuth;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SSoService {

    @Autowired
    private SsoLoginAuthMapper ssoLoginAuthMapper;


    public SsoLoginAuth findSsoAuthByUsername(String username) {
        return ssoLoginAuthMapper.selectByPrimaryKey(username);
    }
}
