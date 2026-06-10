package com.zzy.api.controller.hotmap;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.github.pagehelper.PageInfo;
import com.jcraft.jsch.ChannelExec;
import com.jcraft.jsch.JSchException;
import com.zzy.api.service.hotmap.IMonitorInfoService;
import com.zzy.core.dto.R;
import com.zzy.core.utils.MinioUtil;
import com.zzy.db.dao.hotmap.MonitorInfoMapper;
import com.zzy.db.entity.hotmap.MonitorInfo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/monitor/public")
@Api(value = "监控信息接口", tags = "监控信息接口")
public class MonitorController {
    @Autowired
    private IMonitorInfoService iMonitorInfoService;
    @Autowired
    private MonitorInfoMapper monitorInfoMapper;

    @GetMapping("/publicListMonitorInfoByType")
    @ApiOperation(value = "根据景区类型获取监控信息列表")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "pageNumber", value = "页数", required = false, paramType = "query", dataType = "String"),
            @ApiImplicitParam(name = "pageSize", value = "页码", required = false, paramType = "query", dataType = "String"),
            @ApiImplicitParam(name = "type", value = "类型（1-九寨沟，2-神仙池，3-甲勿池，4-古藏寨，5-甘海子，6熊猫馆）", required = false, paramType = "query", dataType = "String")
    })
    public R listMonitorInfoByType(Integer pageNumber, Integer pageSize,Integer type){
        if(pageNumber==null||pageSize==null||type==null){
            pageNumber=1;
            pageSize=200;
            type=1;
        }
        PageInfo<MonitorInfo> monitorInfoPageInfo = iMonitorInfoService.listMonitorInfo(pageNumber, pageSize, type);
        if(monitorInfoPageInfo!=null){
            return R.ok(monitorInfoPageInfo);
        }
        return R.ok("暂无数据");
    }

    @PostMapping("/saveMonitor")
    @ApiOperation(value = "新增监控信息")
    public R saveMonitor(MonitorInfo monitorInfo){
        monitorInfo.setCreateTime(LocalDateTime.now());
        monitorInfo.setUpdateTime(LocalDateTime.now());
        return R.ok(iMonitorInfoService.save(monitorInfo));
    }


    @PostMapping("/uploadPic")
    @ApiOperation(value = "上传图片接口")
    public R uploadPic(MultipartFile pic) throws Exception{
        String picUrl=null;
        if(MinioUtil.isNullEmpty(pic)) {
            return R.nullValueError();
        }else {
            picUrl = MinioUtil.uploadFile(pic);
        }
        return R.ok(picUrl);
    }

    @PostMapping("/executeCommand")
    @ApiOperation(value = "执行推流命令")
    public R executeCommand(String rtsp,String sn){
        JschCommand jschCommand = new JschCommand();
      /*  if(rtsp.contains("realmonitor")) {
            rtsp += "&subtype=1";
        }*/
        System.out.println(rtsp);
        jschCommand.executeCommand("ffmpeg -rtsp_transport tcp -i \""+rtsp+"\" -vcodec libx264 -vprofile baseline -acodec aac -ar 44100 -strict -2 -ac 1 -f flv -s 480x720 -q 10 rtmp://112.44.67.32:1936/stream/"+sn);
             return R.ok("推流中");
    }
    @PostMapping("/executeCommand22")
    @ApiOperation(value = "执行推流命令")
    public R executeCommand22(String rtsp,String sn){
        JschCommand jschCommand = new JschCommand();
      /*  if(rtsp.contains("realmonitor")) {
            rtsp += "&subtype=1";
        }*/
        System.out.println(rtsp);
        jschCommand.executeCommand("ffmpeg -rtsp_transport tcp -i \""+rtsp+"\" -vcodec libx264 -vprofile baseline -acodec aac -ar 44100 -strict -2 -ac 1 -f flv -s 1240x720 -q 10 rtmp://112.44.67.32:1936/stream/"+sn);
             return R.ok("推流中");
    }

    @GetMapping("/stopWater")
    @ApiOperation(value = "执行关流命令")
    public R stopWater(){
        JschCommand jschCommand = new JschCommand();
        boolean b = jschCommand.executeCommand("ps -ef|grep  ffmpeg|grep -v grep|cut -c 9-15|xargs kill -9");
        if(b) {
            return R.ok("关流成功");
        }
        return R.ok("关流失败");
    }


    @GetMapping("/getInAndOutPeople")
    @ApiOperation(value = "熊猫馆客流数据接口")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "dayTime", value = "日期（2020-06-24）", required = true, paramType = "query", dataType = "String")
    })
    public R getInAndOutPeople(String dayTime){
        List<Map<String, Object>> inAndOutPeople = iMonitorInfoService.getInAndOutPeople(dayTime);
        if(inAndOutPeople.size()>0){
            return R.ok(inAndOutPeople);
        }
        return R.ok("暂无数据");
    }



}
