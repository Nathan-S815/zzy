package com.zzy.api.controller.analysis;

import com.zzy.api.service.analysis.IncomeService;
import com.zzy.core.dto.R;
import com.zzy.core.utils.TimeDateUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;

@RestController
@RequestMapping("/income")
@Api(value = "旅游综合收入", tags = "旅游综合收入")
public class IncomeController {
    @Autowired
    private IncomeService incomeService;

    @GetMapping("/getCurrentIncome")
    @ApiOperation(value = "实际收入")
    public R getCurrentIncome(){
        String startTime = TimeDateUtil.getYear(TimeDateUtil.getFormatDate(new Date()))+"-01-01";
        String endTime = TimeDateUtil.getFormatDate(new Date());
        return R.ok(incomeService.getCurrentIncome(startTime,endTime));
    }

    @GetMapping("/getForecastIncome")
    @ApiOperation(value = "预测收入")
    public R getForecastIncome(){
        String startTime = TimeDateUtil.getYear(TimeDateUtil.getFormatDate(new Date()))-1+"-01-01";
        String endTime = TimeDateUtil.getYear(TimeDateUtil.getFormatDate(new Date()))-1+"-12-31";
        return R.ok(incomeService.getCurrentIncome(startTime,endTime));
    }

}
