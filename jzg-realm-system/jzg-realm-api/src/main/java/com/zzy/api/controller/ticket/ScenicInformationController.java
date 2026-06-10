package com.zzy.api.controller.ticket;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.zzy.api.service.base.IBaseTicketService;
import com.zzy.api.service.ticket.IScenicEnterPeopleService;
import com.zzy.core.dto.R;
import com.zzy.db.dao.base.BaseTicketMapper;
import com.zzy.db.dao.ticket.ScenicEnterPeopleMapper;
import com.zzy.db.entity.ticket.ScenicEnterPeople;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.*;

@RestController
@RequestMapping("/scenicInformation")
@Api(tags="景区实时信息")
public class ScenicInformationController {

    @Autowired
    private IScenicEnterPeopleService iScenicEnterPeopleService;
    @Autowired
    private ScenicEnterPeopleMapper scenicEnterPeopleMapper;
    @Autowired
    private IBaseTicketService ibaseTicketService;

    @ApiOperation(value="各景区实时信息")
    @GetMapping("/getScenicInformation")
    public R getScenicInformation(){
        return R.ok(ibaseTicketService.getScenicInformation());
    }

    @ApiOperation(value = "旅游接待人次")
    @GetMapping("/getAllTouristNumber")
    public R getAllTouristNumber() {
        return R.ok(ibaseTicketService.getAllTouristNumber());
    }

    @ApiOperation(value = "游客人数预测")
    @GetMapping("/getTouristNumberForecast")
    public R getTouristNumberForecast() {
        return R.ok(ibaseTicketService.getAllTouristNumberForecast());
    }

    @ApiOperation(value = "获取甘海子票务信息")
    @PostMapping("/public/getTicketGanhaizi")
    public R getTicketGanhaizi(@RequestBody String ticketInfo) {
        ScenicEnterPeople ticketInformation  = JSON.parseObject(ticketInfo, new TypeReference<ScenicEnterPeople>(){});
        List list = new ArrayList<>();
        ticketInformation.setCreateTime(LocalDateTime.now());
        list.add(ticketInformation);
        if (scenicEnterPeopleMapper.batchInsert(list)>0){
            return R.ok("保存成功");
        }else {
            return R.ok("保存失败");
        }
//        return R.ok();
    }
}
