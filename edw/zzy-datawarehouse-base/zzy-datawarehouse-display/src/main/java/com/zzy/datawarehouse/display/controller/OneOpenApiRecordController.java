package com.zzy.datawarehouse.display.controller;


import com.alibaba.cola.dto.SingleResponse;
import com.zzy.datawarehouse.display.entity.CnRegionInfo;
import com.zzy.datawarehouse.display.entity.OneOpenApiRecord;
import com.zzy.datawarehouse.display.param.RegionParam;
import com.zzy.datawarehouse.display.service.OneOpenApiRecordService;
import com.zzy.datawarehouse.display.vo.OneCodeUseInfoVO;
import com.zzy.datawarehouse.display.vo.OneOpenApiMemberVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 一码通调用记录
 *
 * @author wanghanhan
 * @email han950115@163.com
 * @date 2022-12-13 10:24:45
 */
@Api(tags = "一码通调用记录 管理")
@RestController
@RequestMapping("display/oneopenapirecord")
public class OneOpenApiRecordController {

    @Autowired
    private OneOpenApiRecordService oneOpenApiRecordService;


    @ApiOperation(value = "全省一码通使用人数")
    @GetMapping("list")
    public SingleResponse<?> getOneOpenNum() {
        List<OneOpenApiRecord> list = oneOpenApiRecordService.list();
        return SingleResponse.of(list.size());
    }

    @ApiOperation(value = "景区一码通数据情况")
    @GetMapping("openApiUseService")
    public SingleResponse<OneOpenApiMemberVO> getOpenApiUseServiceNum() {
        SingleResponse<OneOpenApiMemberVO> oneOpenApiMemberVO = oneOpenApiRecordService.getOpenApiUseServiceNum();
        return oneOpenApiMemberVO;
    }

    @ApiOperation(value = "一码通使用数据")
    @GetMapping("oneCodeUseInfo")
    public SingleResponse<List<OneCodeUseInfoVO>> getOneCodeUseInfoVOList() {
        SingleResponse<List<OneCodeUseInfoVO>> oneCodeUseInfoVOList = oneOpenApiRecordService.getOneCodeUseInfoVOList();
        return oneCodeUseInfoVOList;
    }
}
