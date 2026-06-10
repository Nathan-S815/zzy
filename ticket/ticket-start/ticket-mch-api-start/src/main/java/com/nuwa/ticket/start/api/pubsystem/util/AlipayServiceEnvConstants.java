

/**

 * Alipay.com Inc.

 * Copyright (c) 2004-2014 All Rights Reserved.

 */

package com.nuwa.ticket.start.api.pubsystem.util;


/**
 * 支付宝服务窗环境常量（demo中常量只是参考，需要修改成自己的常量值）
 * 
 * @author taixu.zqq
 * @version $Id: AlipayServiceConstants.java, v 0.1 2014年7月24日 下午4:33:49 taixu.zqq Exp $
 */
public class AlipayServiceEnvConstants {

    /**支付宝公钥-从支付宝生活号详情页面获取*/
	public static final String ALIPAY_PUBLIC_KEY = "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEA5dDE7CMiEHeXeWOHOcAbaWeybUxN+wwg1kr9n3uktysMTb49vbuygk9lRuJchFlf6n3tRZFDCdF2rA4cs66g98kcQsOAuCsrScQAB/5WhjGEwr7P1GutEe6X3+/wwrd7g7X15MR2DfKnTNxMgXSNWQr6x20dyzedqoVxFD3roV6cM+6OMHP7AoQ+2tjswOhO8KF00XoIWrEYZcg+UeT4i1n5wwp5ZRdTYcuThWUzL8YCAbaPrLE74KB+lOUc+79URsk3eI7XjlRwAHf2FWhVhGYLBNLRbnZApvpc0bYsRSp42mVYQnObXhayptkQ0WBJPQOBkIwU7/nhVXabb3wbYwIDAQAB";
    
    /**签名编码-视支付宝服务窗要求*/
    public static final String SIGN_CHARSET      = "GBK";

    /**字符编码-传递给支付宝的数据编码*/
    public static final String CHARSET           = "GBK";

    /**签名类型-视支付宝服务窗要求*/
    public static final String SIGN_TYPE         = "RSA2";
    
    /**开发者账号PID*/
    public static final String PARTNER           = "";

    /** 服务窗appId  */
    //TODO !!!! 注：该appId必须设为开发者自己的生活号id  
    public static final String APP_ID            = "2021002189618144";

    //TODO !!!! 注：该私钥为测试账号私钥  开发者必须设置自己的私钥 , 否则会存在安全隐患 
    public static final String PRIVATE_KEY       = "MIIEvgIBADANBgkqhkiG9w0BAQEFAASCBKgwggSkAgEAAoIBAQDRb5hdR4hwczaBKdI6ind8AqB62cxgJfUHacwMOanONytMSS1ePWLqtQcQKjnKTmHU5C6Cm2MyRBqh2mJIQfZiHMl1SknCeTUArr7nmCMhR1xI4UvbgdGiNojL7wWYoReob5mzzpzIFXIQ239neJXf0otfEo1VwXxSel9NP/qWi3Yki8DPpjQv+FAT+MsDis+GMIm5fhSAtD/UdHz++R2vjfWQmt1J5sSo7bkmdUMgbhmtflLoHWWMkfYGyYRjPyzMbHCAZoQ+z0IgWeOuKDtDK+Rh3ihC3P3bbnxsZStQ517uiNQNi1EQrXkp2kwiEzachiOtcPScFJUGJ0jRzu8tAgMBAAECggEAWVUSVmdWqTBbhUiDZXj+ciTkssIHzNMj6t+Tq3tFFLKvg0CLW3EvqhFBTAHW2JYKYLZbqFYDP8OQ4Iy38z4/i6XfwGnJgGwW0Y233C0mfuFjWarPoneDJX/BZcs0ReAgqTXBB9MPSMDtAIe/ljD3LJsv24OcSYHYN43+7IJnrqJUWg7h+UdIbRtkMnuMyNAq9lfnn8r58723V0+KRpv+46dz0eiDbHgPZEVpJwsOSZqZL6TxiHgWorT1jnmWEsMyxLWBB9TTQ4StlyGrng2/DB3nVtCTQyCgKqJycIVvnqcSDh6822AovQM08dTYZTjHmXZxt6F2IXpASRccoU5+AQKBgQDrFh5UNoVhx4DNdKlFBKXvJqsi/plchz+noQ++i6jvAR2LDCYpCkjHYF5KwEebQCYPHxe2OVppccfmM1pfzNIT79UXyK6t2lsTAcLXlUM6rk47CjWLqKNM6mIiJpk8L2F3wEtpsOPr4FoV0l26Vw7CsuVyG7orxHRqlpm5eCFC7QKBgQDkEU9eII/kQy1yj3z0DVF0YOMIL8eGjURb7q7RHOWSFaCSufi7BC/k9P8I44ehDnvsNGoCDDZdPpBT90SJC+3Aal8QgSgac2ZrMnN9SpwFo07ucP18iMDLhkxR5LlXny50+H57ioaSgnj9pJRqI4IzmoV3LX9YQ8bn5oUHAO2VQQKBgQCDqBR+NzaF5zQUXFR8CblgcPiBFbMP5dLZb1Mg9Jg8mhRQjhJFA7R0rFzCYmL36HooF9Kww/gFe6pnGrVvV4cDkVp3AnYJeXNt9puHHtnS4FGBsRsBxwUFi3JAC/vff+2HIlNZsq5CqJfrBrBlLmJe2c+0EyE1Z8WG+CbJfySplQKBgDVcr3tQlxpLdYjGdfAMZbCbMsOpmj+rMgFomVKoNja2BLoP9VicpDAn+rwzsHpIj0o3AU61WMr8Znye8FQ4jEpnGRMdhSqbMa2HQmpgWJ0hFsjHeyf2P1XfeF/46jzMK+yGb+GsCcyh5nlCVs92u3Ijh3LZHGCkixebYii/WNeBAoGBAMsCTjKDMzNfoF7W/95/fRS1khUV7PAf0JIRWkjq0XRNvP8Cw0/dsf2mltJ96G6mpjsBk1EPNqiiFg9PEnE0lk0AtwTBC7v8uhHsYii22HDCJEnwoolq3DCKDvMs2ieSQKjy/6BIdVYY3c6x3XZplUscNR/e4pKNql2+fcc1hAh6";
    
    //TODO !!!! 注：该公钥为测试账号公钥  开发者必须设置自己的公钥 ,否则会存在安全隐患
    public static final String PUBLIC_KEY        = "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEA0W+YXUeIcHM2gSnSOop3fAKgetnMYCX1B2nMDDmpzjcrTEktXj1i6rUHECo5yk5h1OQugptjMkQaodpiSEH2YhzJdUpJwnk1AK6+55gjIUdcSOFL24HRojaIy+8FmKEXqG+Zs86cyBVyENt/Z3iV39KLXxKNVcF8UnpfTT/6lot2JIvAz6Y0L/hQE/jLA4rPhjCJuX4UgLQ/1HR8/vkdr431kJrdSebEqO25JnVDIG4ZrX5S6B1ljJH2BsmEYz8szGxwgGaEPs9CIFnjrig7QyvkYd4oQtz92258bGUrUOde7ojUDYtREK15KdpMIhM2nIYjrXD0nBSVBidI0c7vLQIDAQAB";
    /**支付宝网关*/
    public static final String ALIPAY_GATEWAY    = "https://openapi.alipay.com/gateway.do";

    /**授权访问令牌的授权类型*/
    public static final String GRANT_TYPE        = "authorization_code";
}