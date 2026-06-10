package com.zzy.api.controller;


import com.zzy.api.service.log.IJzgLogOpertertService;
import com.zzy.core.dto.R;
import com.zzy.db.entity.log.JzgLogOpertert;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author zzy
 * @since 2020-09-26
 */
@RestController
@RequestMapping("/jzgLogOpertert")
@Api(tags = "日志操作接口")
public class JzgLogOpertertController {

    @Autowired
    private IJzgLogOpertertService iJzgLogOpertertService;

    @PostMapping("/public/insertLog")
    @ApiOperation(value = "新增日志")
    public R insertLog(JzgLogOpertert jzgLogOpertert){
        boolean save = iJzgLogOpertertService.save(jzgLogOpertert);
        if (save){
            return R.success();
        }
        return R.fail();
    }

}
