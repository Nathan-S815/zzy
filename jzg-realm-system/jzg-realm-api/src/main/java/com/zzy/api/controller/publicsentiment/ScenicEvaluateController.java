package com.zzy.api.controller.publicsentiment;

import com.zzy.api.service.ota.IScenicCommentInfoService;
import com.zzy.core.dto.R;
import com.zzy.core.utils.TimeDateUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


import java.util.Date;

@RestController
@RequestMapping("/evaluate")
@Api(tags = "景区口碑舆情分析")
public class ScenicEvaluateController {

    @Autowired
    private IScenicCommentInfoService iScenicCommentInfoService;

    @ApiOperation(value = "整体分析")
    @GetMapping("/getEntiretyAnalysis")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "startTime", value = "开始时间（2020-05-09）", required = false, paramType = "query", dataType = "String"),
            @ApiImplicitParam(name = "endTime", value = "结束时间（2020-05-16）", required = false, paramType = "query", dataType = "String")
    })
    public R getEntiretyAnalysis(String startTime,String endTime) {
        if (endTime == null){
            endTime = TimeDateUtil.getFormatDate(new Date());
        }
        if (startTime == null){
            startTime = TimeDateUtil.getFirstOfYear(endTime);
        }
        return R.ok(iScenicCommentInfoService.getEntiretyAnalysis(startTime,endTime));
    }

    @ApiOperation(value = "各景区分析")
    @GetMapping("/getEachAnalysis")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "startTime", value = "开始时间（2020-05-09）", required = false, paramType = "query", dataType = "String"),
            @ApiImplicitParam(name = "endTime", value = "结束时间（2020-05-16）", required = false, paramType = "query", dataType = "String")
    })
    public R getEachAnalysis(String startTime,String endTime) {
        if (endTime == null){
            endTime = TimeDateUtil.getFormatDate(new Date());
        }
        if (startTime == null){
            startTime = TimeDateUtil.getYear(endTime)-1+"-01-01";
        }
        return R.ok(iScenicCommentInfoService.getEachAnalysis(startTime,endTime));
    }



    @ApiOperation(value="获取景区热词", notes = "评论关键字")
    @GetMapping("/findCommentKeyWord")
    public R getCommentKeyWord(){
        return R.ok(iScenicCommentInfoService.findCommentKeyWordByArea());
    }


}
