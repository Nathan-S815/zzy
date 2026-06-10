package com.zzy.security.controller;


import cn.hutool.core.util.StrUtil;
import com.zzy.core.dto.R;
import com.zzy.security.service.SsoLoginClient;
import com.zzy.security.service.UsersService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

//@Controller
//@RequestMapping("/sso")
//@Api(tags = "文旅页面跳转相关")
public class SSOController {



    @Autowired
    private UsersService usersService;

//    @ApiOperation(value="获取各个管理页面跳转url", notes = "获取跳转url")
//    @GetMapping("/getAllSystemUrls")
//    @ResponseBody
    public R getSystemUrls(){
//        String usernames = String.valueOf(SecurityContextHolder.getContext().getAuthentication().getName());
//        System.out.println("登录名是:"+usernames);
        String username = "test1";
        List<Map<String,Object>> l =usersService.findAllSystemUrls(username);
        return R.ok(l);
    }



//    @ApiOperation(value="跳转到指定url的管理页面", notes = "跳转url")
//    @ApiImplicitParams({
//            @ApiImplicitParam(name="systemUrl",value="特定页面url", required=true,paramType = "query",  dataType="string"),
//    })
//    @GetMapping("/toSystemUrlPage")
//    @ResponseBody
    public R toSystemUrl(HttpServletRequest request, HttpServletResponse response){
        String systemUrl = request.getParameter("systemUrl");
        if(StrUtil.isBlankOrUndefined(systemUrl)){
            return R.ok("参数不能为空");
        }
        Map<String,Object> m = new HashMap<>();
        m.put("url",SsoLoginClient.getSystemWebUrl(systemUrl));
        return R.ok(m);
    }

}
