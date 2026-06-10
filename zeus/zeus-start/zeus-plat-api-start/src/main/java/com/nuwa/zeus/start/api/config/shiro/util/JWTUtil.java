package com.nuwa.zeus.start.api.config.shiro.util;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.RandomUtil;
import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTDecodeException;
import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;

import java.util.Date;

public class JWTUtil {

    private static final String USER_NAME = "userName";
    private static final String MCH_USER_ID = "mchUserId";
    private static final String MCH_ID = "mchId";

    private static final String MCH_NAME = "mchName";
    private static final String TOKEN_ID = "tokenId";

    //token过期时间30天
    public static final long EXPIRE_TIME = 30l * 24 * 60 * 60 * 1000;

    public static final int EXPIRE_HOUR_TIME = 6;

    //JWT签名密钥
    public static final String SECRET_KEY = "<REDACTED>";


    /**
     * 生成签名,5min后过期
     *
     * @param username 用户名
     * @return 加密的token
     */
    public static String sign(String username, Long userId, Long mchId,String mchName) {
        Date date = DateUtil.offsetHour(new Date(),EXPIRE_HOUR_TIME);
        Algorithm algorithm = Algorithm.HMAC256(SECRET_KEY);
        // 附带username信息
        return JWT.create()
                .withClaim(USER_NAME, username)
                .withClaim(MCH_USER_ID, userId)
                .withClaim(MCH_ID, mchId)
                .withClaim(MCH_NAME, mchName)
                .withClaim(TOKEN_ID, RandomUtil.randomString(16))
                .withExpiresAt(date)
                .sign(algorithm);
    }

    /**
     * 校验 token 是否正确
     */
    public static boolean verify(String token) {
        try {
            Algorithm algorithm = Algorithm.HMAC256(SECRET_KEY);
            JWTVerifier verifier = JWT.require(algorithm)
                    .build();
            //验证 token
            verifier.verify(token);
            return true;
        } catch (Exception exception) {
            return false;
        }
    }

    /**
     * 获得token中的信息，无需secret解密也能获得
     */
    public static String getUsername(String token) {
        try {
            DecodedJWT jwt = JWT.decode(token);
            return jwt.getClaim(USER_NAME).asString();
        } catch (JWTDecodeException e) {
            return null;
        }
    }

    /**
     * 获得token中的信息，无需secret解密也能获得
     */
    public static Long getMchUserId(String token) {
        try {
            DecodedJWT jwt = JWT.decode(token);
            return jwt.getClaim(MCH_USER_ID).asLong();
        } catch (JWTDecodeException e) {
            return null;
        }
    }

    /**
     * 获得token中的信息，无需secret解密也能获得
     */
    public static Long getMchId(String token) {
        try {
            DecodedJWT jwt = JWT.decode(token);
            Claim claim = jwt.getClaim(MCH_ID);
            if (BeanUtil.isNotEmpty(claim)) {
                return claim.asLong();
            }
            return null;
        } catch (JWTDecodeException e) {
            return null;
        }
    }

    /**
     * 获得token中的信息，无需secret解密也能获得
     */
    public static String getTokenId(String token) {
        try {
            DecodedJWT jwt = JWT.decode(token);
            Claim claim = jwt.getClaim(TOKEN_ID);
            if (BeanUtil.isNotEmpty(claim)) {
                return claim.asString();
            }
            return null;
        } catch (JWTDecodeException e) {
            return null;
        }
    }
}
