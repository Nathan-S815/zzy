package com.zzy.core.utils;

import cn.hutool.core.img.ImgUtil;
import cn.hutool.core.io.FileUtil;
import com.google.code.kaptcha.impl.DefaultKaptcha;
import com.google.code.kaptcha.util.Config;
import org.springframework.stereotype.Component;
import sun.misc.BASE64Encoder;

import javax.imageio.ImageIO;
import javax.imageio.stream.ImageOutputStream;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;


public class KaptchaUtil {


    /**
     *注册验证码图片SessionKey
     */
    public static final String LOGIN_VALIDATE_CODE = "kaptcha_validate_code";



    /**
     * 生成验证码图片
     * @param request 设置session
     * @param response 转成图片
     * @param captchaProducer 生成图片方法类
     * @param validateSessionKey session名称
     * @throws Exception
     */
    public static void validateCode(HttpServletRequest request, HttpServletResponse response, DefaultKaptcha captchaProducer, String validateSessionKey) throws Exception{
        response.setDateHeader("Expires", 0);
        response.setHeader("Cache-Control", "no-store, no-cache, must-revalidate");
        response.addHeader("Cache-Control", "post-check=0, pre-check=0");
        response.setHeader("Pragma", "no-cache");
        response.setContentType("image/jpeg");
        String capText = captchaProducer.createText();
        HttpSession s = request.getSession(true);
        s.setAttribute(validateSessionKey, capText);
        CookieUtil.setCookie(response,validateSessionKey,s.getId());
        BufferedImage bi = captchaProducer.createImage(capText);
        ServletOutputStream out = response.getOutputStream();
        ImageIO.write(bi, "jpg", out);
        try {
            out.flush();
        } finally {
            out.close();
        }
    }

    /**
     * 生成验证码图片Base64字符串
     * @param captchaProducer 生成图片方法类
     * @throws Exception
     */
    public static String generateCodeImgStr(DefaultKaptcha captchaProducer, String capText) throws Exception{
//        HttpSession s = request.getSession(true);
//        s.setAttribute(validateSessionKey, capText);
//        CookieUtil.setCookie(response,validateSessionKey,s.getId());
        BufferedImage bi = captchaProducer.createImage(capText);
        return "data:image/png;base64,"+ImgUtil.toBase64(bi,"png");
    }


    /**
     * true:相同
     * @param request
     * @param validateCode
     * @return
     */
    public static boolean isSameValidateCode(HttpServletRequest request, String validateCode){
//        return CookieUtil.getCookieValue(request,"JSESSIONID").equalsIgnoreCase(validateCode);
        return request.getSession().getAttribute(LOGIN_VALIDATE_CODE).toString().equalsIgnoreCase(validateCode);
    }



    /**
     * true:已过期
     * @param request
     * @return
     */
    public static boolean isValidateCodeExpire(HttpServletRequest request){
        if(request==null || request.getSession()==null) return true;
        return request.getSession().getAttribute(LOGIN_VALIDATE_CODE)==null;
//        return CookieUtil.getCookieValue(request,"JSESSIONID")==null;
    }


}
