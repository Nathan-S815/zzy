package com.nuwa.hotel.start.api.util;

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
import java.util.Objects;

public class MemberJwtUtil {
    private static final String USER_ID = "userId";
    private static final String USER_NAME = "userName";
    private static final String MCH_ID = "mchId";
    private static final String MCH_APP_ID = "mchAppId";
    private static final String APP_ID = "appId";
    private static final String TOKEN_ID = "tokenId";

    //token过期时间30天
    private static final long EXPIRE_TIME = 30 * 24 * 60 * 60 * 1000;

    public static final int EXPIRE_HOUR_TIME = 24 * 7;

    //JWT签名密钥
    public static final String SECRET_KEY = "<REDACTED>";


    public static String sign(Integer userId, String userName, Long mchId) {
        Date date = DateUtil.offsetMonth(new Date(), 1);
        Algorithm algorithm = Algorithm.HMAC256(SECRET_KEY);
        // 附带username信息
        return JWT.create()
                .withClaim(USER_ID, userId)
                .withClaim(USER_NAME, userName)
                .withClaim(MCH_ID, mchId)
                .withClaim(MCH_APP_ID, mchId)
                .withClaim(APP_ID, mchId)
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
            //在token中附带了username信息
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
    public static Long getUserId(String token) {
        try {
            DecodedJWT jwt = JWT.decode(token);
            return jwt.getClaim(USER_ID).asLong();
        } catch (JWTDecodeException e) {
            return null;
        }
    }

    /**
     * 获得token中的信息，无需secret解密也能获得
     */
    public static Long getMchId(String token) {
        DecodedJWT jwt = JWT.decode(token);
        try {
            Long aLong = jwt.getClaim(MCH_ID).asLong();
            if (Objects.isNull(aLong)) {
                String strMchId = jwt.getClaim(MCH_ID).asString();
                return Long.parseLong(strMchId);
            }
            return aLong;
        } catch (JWTDecodeException e) {
            String strMchId = jwt.getClaim(MCH_ID).asString();
            return Long.parseLong(strMchId);
        }
    }

    /**
     * 获得token中的信息，无需secret解密也能获得
     */
    public static String getUserName(String token) {
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

    public static void main(String[] args) {
        Long mchId = getMchId("eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJtY2hJZCI6Ijk4NCIsIm1jaEFwcElkIjoiOTg0IiwidG9rZW5JZCI6Im5sajlzNmkzdmVwdzRscGEiLCJhcHBJZCI6Ijk4NCIsInVzZXJOYW1lIjoi6Zuo6L-H5re75pm0IiwiZXhwIjoxNjUwNjA2OTQ2LCJ1c2VySWQiOjI0OTEzN30.e-q7AzuUTPJ4U6j20uOeQgNS6ybW1EIDRaMXqYB51Iw");
        System.out.println(mchId);
    }
}
