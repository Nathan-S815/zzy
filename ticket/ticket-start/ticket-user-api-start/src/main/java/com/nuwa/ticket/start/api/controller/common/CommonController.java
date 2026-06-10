package com.nuwa.ticket.start.api.controller.common;

import com.alibaba.cola.dto.SingleResponse;
import com.nuwa.infrastructure.ticket.database.scenicspot.entity.ScenicspotBaseType;
import com.nuwa.infrastructure.ticket.database.scenicspot.service.ScenicspotBaseTypeService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 * 中国地区信息 前端控制器
 * </p>
 *
 * @author ROOT
 * @since 2020-11-11
 */
@Api(tags = {"公共接口-相关接口"})
@Slf4j
@RestController
@RequestMapping("/common")
public class CommonController {

    @Autowired
    private ScenicspotBaseTypeService scenicspotBaseTypeService;

    @ApiOperation(value = "获取景点类型列表")
    @RequestMapping(value = "/scenicspot/typeList", method = RequestMethod.GET)
    @ResponseBody
    public SingleResponse<List<ScenicspotBaseType>> scenicspotBaseType() {
        List<ScenicspotBaseType> scenicspotBaseTypes = scenicspotBaseTypeService.lambdaQuery()
                .eq(ScenicspotBaseType::getPoiType, "scenic")
                .orderByAsc(ScenicspotBaseType::getWeight)
                .list();
        return SingleResponse.of(scenicspotBaseTypes);
    }

    @ApiOperation(value = "获取景博类型列表")
    @RequestMapping(value = "/type/list/{poiType}", method = RequestMethod.GET)
    @ResponseBody
    public SingleResponse<List<ScenicspotBaseType>> scenicspotBaseTypeV2(@PathVariable("poiType") String poiType) {
        List<ScenicspotBaseType> scenicspotBaseTypes = scenicspotBaseTypeService.lambdaQuery()
                .eq(ScenicspotBaseType::getPoiType, poiType)
                .orderByAsc(ScenicspotBaseType::getWeight).list();
        return SingleResponse.of(scenicspotBaseTypes);
    }
}
