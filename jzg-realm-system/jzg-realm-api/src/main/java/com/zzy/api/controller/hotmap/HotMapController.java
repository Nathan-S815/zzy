package com.zzy.api.controller.hotmap;

import com.zzy.api.service.hotmap.IGetAbjzgMinutePeopleHotDataService;
import com.zzy.api.service.hotmap.IHotCountMapService;
import com.zzy.core.dto.R;
import com.zzy.db.entity.hotmap.GetAbjzgMinutePeopleHotData;
import com.zzy.db.entity.hotmap.HotCountMap;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/hotmap")
@Api(value = "热力图数据接口", tags = "热力图数据接口")
public class HotMapController {

    @Autowired
    private IHotCountMapService iHotCountMapService;

    @Autowired
    private IGetAbjzgMinutePeopleHotDataService iGetAbjzgMinutePeopleHotDataService;

    //    @GetMapping("/listHotMapCount")
//    @ApiOperation(value = "获取热力图数据")
//    public R listHotCountMap(){
//        List<HotCountMap> hotCountMapList = iHotCountMapService.list();
//        if(hotCountMapList.size()>0){
//            return R.ok(hotCountMapList);
//        }
//        return R.ok("暂无数据");
//    }
    @GetMapping("/listHotMapCount")
    @ApiOperation(value = "获取热力图数据")
    public R listHotCountMap() {
        List<Map<String,Object>> hotCountMapList = iGetAbjzgMinutePeopleHotDataService.getLatest();
        if (hotCountMapList.size() > 0) {
            return R.ok(hotCountMapList);
        }
        return R.ok("暂无数据");
    }

    @PostMapping("/saveHotCountMap")
    @ApiOperation(value = "新增热力图数据")
    public R saveHotCountMap(HotCountMap hotCountMap) {
        boolean save = iHotCountMapService.save(hotCountMap);
        if (save) {
            return R.ok("新增成功");
        }
        return R.ok("新增失败");
    }
}
