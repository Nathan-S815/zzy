package com.zzy.core.utils;

import cn.hutool.crypto.digest.DigestUtil;

public class AuthUtil {

    public static String getSaltedPwd(String oriPwd) {
        String salt = DigestUtil.md5HexTo16(DigestUtil.md5Hex(oriPwd));
        return DigestUtil.md5Hex(salt+oriPwd);
    }




}
