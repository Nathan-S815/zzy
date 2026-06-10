package com.zzy.security.service;


import com.zzy.security.lib.dao.KaptchaInfoMapper;
import com.zzy.security.lib.entity.KaptchaInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class KapService {


    @Autowired
    KaptchaInfoMapper kaptchaInfoMapper;

    public boolean insertInfo(KaptchaInfo ki) {
        return kaptchaInfoMapper.insert(ki)>0;
    }
}
