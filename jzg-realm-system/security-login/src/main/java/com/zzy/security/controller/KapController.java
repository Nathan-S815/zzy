package com.zzy.security.controller;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.RandomUtil;
import cn.hutool.crypto.digest.DigestUtil;
import com.google.code.kaptcha.impl.DefaultKaptcha;
import com.zzy.core.dto.R;
import com.zzy.core.utils.KaptchaUtil;
import com.zzy.security.lib.entity.KaptchaInfo;
import com.zzy.security.service.KapService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;


@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/kaptcha")
@Api(tags = "验证码")
public class KapController {

    @Autowired
    private DefaultKaptcha captchaProducer;


    @Autowired
    private KapService kapService;



    /**
     * 注册验证码图片
     */
    @ApiOperation(value="获取验证码", notes = "返回验证码图片")
    @GetMapping("/registCode")
    public R registValidateCode() throws Exception{
        String capText = captchaProducer.createText();
        String r =  KaptchaUtil.generateCodeImgStr(captchaProducer, capText);
        String token = DigestUtil.md5Hex(capText+ RandomUtil.randomString(16)+System.currentTimeMillis());
        KaptchaInfo ki = new KaptchaInfo();
        ki.setCapText(capText);
        ki.setCapToken(token);
        ki.setCreateTime(new Date());
        kapService.insertInfo(ki);
        Map<String,Object> m = new HashMap<>();
        m.put("img", r);
        m.put("token", token);
        return R.ok(m);
    }


}
