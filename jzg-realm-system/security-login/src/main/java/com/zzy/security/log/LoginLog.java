package com.zzy.security.log;

import cn.hutool.http.HttpUtil;
import com.alibaba.fastjson.JSON;
import com.zzy.security.lib.entity.UserLoginLog;

import java.util.HashMap;
import java.util.Map;

public  class LoginLog {
    public static void sendLoginLog(UserLoginLog userLoginLog){
        Map<String,Object> param = new HashMap<>();
        param.put("operUser","26d4612ef26611eabdb09afa7f8fbd05");
        param.put("logType",1);
        param.put("operType",1);
        param.put("logPlatform",2);
        param.put("operContent","用户登录");
        String url ="http://172.20.18.3:7777/loginCenter/sysLog/insert";
        HttpUtil.createPost(url).form("operUser","26d4612ef26611eabdb09afa7f8fbd05").body(JSON.toJSONString(param)).setConnectionTimeout(3000).execute();
    }
}
