package com.zzy.security.common;


import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.NumberUtil;
import cn.hutool.core.util.StrUtil;
import com.zzy.security.dto.CustomUser;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.util.*;


public class JwtUtil {

    private final static Logger log = LoggerFactory.getLogger(JwtUtil.class);

    public static final String ROLE_REFRESH_TOKEN = "ROLE_REFRESH_TOKEN";

    private static final String CLAIM_KEY_USER_ID = "user_id";
    private static final String CLAIM_KEY_AUTHORITIES = "scope";

    private static String secret;

    private static Long access_token_expiration;

    private static Long refresh_token_expiration;

    public static final String jwtHead = "Authorization";

    static final String auth_token_start = "Bearer ";

    static{
        Properties properties = new Properties();
        InputStream inputStream = JwtUtil.class.getResourceAsStream("/tokenconfig.properties");
        try {
            properties.load(inputStream);
        } catch (IOException e) {
            e.printStackTrace();
        }
        secret = properties.get("jwt.secret").toString();
        access_token_expiration = Long.parseLong(properties.get("jwt.expiration").toString());
        refresh_token_expiration = Long.parseLong(properties.get("jwt.expiration").toString());
    }

    public static CustomUser getFromJwt(HttpServletRequest request){
        String token = request.getHeader(JwtUtil.jwtHead);
        if(StrUtil.isBlank(token)){
            return null;
        }
        token = token.substring(auth_token_start.length());
        CustomUser us = JwtUtil.getUserFromToken(token);
//        if(us==null){
//            us = new CustomUser(26,"badz", Arrays.asList("部门负责人","ADMIN"));
//        }
        return us;
    }

    public static String getToken(HttpServletRequest request,String tokenHeader) {
        String auth_token = request.getHeader(tokenHeader);
        if(StrUtil.isBlankOrUndefined(auth_token)){
            return null;
        }
        final String auth_token_start = "Bearer ";
        if (StrUtil.isNotEmpty(auth_token) && auth_token.startsWith(auth_token_start)) {
            auth_token = auth_token.substring(auth_token_start.length());
        }else{
            return null;
        }
        return auth_token;
    }

    public static CustomUser getUserFromToken(String token) {
        CustomUser userDetail;
        try {
            final Claims claims = getClaimsFromToken(token);
            if(claims==null) return null;
            int userId = getUserIdFromToken(token);
            String username = claims.getSubject();
            userDetail = new CustomUser(userId, username, (List<String>) claims.get(CLAIM_KEY_AUTHORITIES));
        } catch (Exception e) {
            userDetail = null;
        }
        return userDetail;
    }

    public static int getUserIdFromToken(String token) {
        int userId;
        try {
            final Claims claims = getClaimsFromToken(token);
            userId = Integer.parseInt(String.valueOf(claims.get(CLAIM_KEY_USER_ID)));
        } catch (Exception e) {
            userId = 0;
        }
        return userId;
    }

    public static String getUsernameFromToken(String token) {
        String username;
        try {
            final Claims claims = getClaimsFromToken(token);
            username = claims.getSubject();
        } catch (Exception e) {
            username = null;
        }
        return username;
    }

    public static Date getCreatedDateFromToken(String token) {
        Date created;
        try {
            final Claims claims = getClaimsFromToken(token);
            created = claims.getIssuedAt();
        } catch (Exception e) {
            created = null;
        }
        return created;
    }

    public static String generateToken(CustomUser userDetail) {
        Map<String, Object> claims = generateClaims(userDetail);
        claims.put(CLAIM_KEY_AUTHORITIES, userDetail.AuthToListStr());
        return generateToken(userDetail.getUsername(), claims);
    }


    public static Date getExpirationDateFromToken(String token) {
        Date expiration;
        try {
            final Claims claims = getClaimsFromToken(token);
            expiration = claims.getExpiration();
        } catch (Exception e) {
            expiration = null;
        }
        return expiration;
    }


    public static String refreshToken(String token) {
        String refreshedToken;
        try {
            final Claims claims = getClaimsFromToken(token);
            refreshedToken = generateToken(claims.getSubject(), claims);
        } catch (Exception e) {
            refreshedToken = null;
        }
        return refreshedToken;
    }


    public static boolean validateToken(String token, UserDetails userDetails) {
        CustomUser userDetail = (CustomUser) userDetails;
        final String username = getUsernameFromToken(token);
        return (username.equals(userDetail.getUsername()) && !isTokenExpired(token));
    }

    public static String generateRefreshToken(CustomUser userDetail) {
        Map<String, Object> claims = generateClaims(userDetail);
        // 只授于更新 token 的权限
        claims.put(CLAIM_KEY_AUTHORITIES, userDetail.getAuthorities());
        return generateRefreshToken(userDetail.getUsername(), claims);
    }


    private static Claims getClaimsFromToken(String token) {
        Claims claims;
        try {
            claims = Jwts.parser()
                    .setSigningKey(secret.getBytes("UTF-8"))
                    .parseClaimsJws(token)
                    .getBody();
        } catch (Exception e) {
            log.error(e.getMessage());
            claims = null;
        }
        return claims;
    }

    private static Date generateExpirationDate(long expiration) {
        return new Date(System.currentTimeMillis() + expiration * 1000);
    }

    private static Boolean isTokenExpired(String token) {
        final Date expiration = getExpirationDateFromToken(token);
        return expiration.before(new Date());
    }


    private static Map<String, Object> generateClaims(CustomUser userDetail) {
        Map<String, Object> claims = new HashMap<>(16);
        claims.put(CLAIM_KEY_USER_ID, userDetail.getUserId());
        claims.put("sub",userDetail.getUsername());
        return claims;
    }

    private static String generateToken(String subject, Map<String, Object> claims) {
        return generateToken(subject, claims, access_token_expiration);
    }


    private static String generateRefreshToken(String subject, Map<String, Object> claims) {
        return generateToken(subject, claims, refresh_token_expiration);
    }



    private static String generateToken(String subject, Map<String, Object> claims, long expiration) {
        try {
            return Jwts.builder()
                    .setClaims(claims)
                    .setSubject(subject)
                    .setId(UUID.randomUUID().toString())
                    .setIssuedAt(new Date())
                    .setExpiration(DateUtil.offsetSecond(new Date(), Integer.parseInt(String.valueOf(expiration))))
                    .signWith(SignatureAlgorithm.HS512, secret.getBytes("UTF-8"))
                    .compact();
        } catch (UnsupportedEncodingException e) {
            log.error("generateToken error:{}",e.getMessage());
            return null;
        }
    }

}
