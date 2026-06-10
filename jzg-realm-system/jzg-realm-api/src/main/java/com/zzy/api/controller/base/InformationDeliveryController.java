package com.zzy.api.controller.base;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.zzy.api.service.base.IInfoActivityPropagateService;
import com.zzy.api.service.base.IInfoEvacuationService;
import com.zzy.api.service.base.IInfoInformationMessageService;
import com.zzy.api.service.base.IInfoPromotionVideoService;
import com.zzy.core.dto.R;
import com.zzy.db.entity.base.InfoActivityPropagate;
import com.zzy.db.entity.base.InfoEvacuation;
import com.zzy.db.entity.base.InfoInformationMessage;
import com.zzy.db.entity.base.InfoPromotionVideo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/information")
@Api(value = "信息统一发布平台接口", tags = "信息统一发布平台接口")
public class InformationDeliveryController {

    @Autowired
    private IInfoActivityPropagateService iInfoActivityPropagateService;

    @Autowired
    private IInfoEvacuationService iInfoEvacuationService;

    @Autowired
    private IInfoInformationMessageService iInfoInformationMessageService;

    @Autowired
    private IInfoPromotionVideoService iInfoPromotionVideoService;

    @GetMapping("/listInfoInformationMessage")
    @ApiOperation(value = "获取欢迎词")
    public R listInfoInformationMessage(){
        QueryWrapper<InfoInformationMessage> wrapper = new QueryWrapper<>();
        wrapper.eq("is_enable",1);
        return R.ok(iInfoInformationMessageService.list(wrapper));
    }

    @PostMapping("/updateInfoInformationMessage")
    @ApiOperation(value = "修改欢迎词")
    public R updateInfoInformationMessage(InfoInformationMessage infoInformationMessage){
        infoInformationMessage.setCreateTime(new Date());
        boolean b = iInfoInformationMessageService.updateById(infoInformationMessage);
        if(b){
            return R.ok("修改成功");
        }
        return R.ok("修改失败");
    }

    @GetMapping("/listInfoPromotionVideo")
    @ApiOperation(value = "获取政务宣传列表")
    public R listInfoPromotionVideo(Integer pagNumber,Integer pagSize,String unitName){
        if(pagNumber==null||pagSize==null) {
            pagNumber=1;
            pagSize=10;
        }
        Map<String,Object> para = new HashMap<>();
        para.put("departName", unitName);
        return R.ok(iInfoPromotionVideoService.selectPageListInfoPromotionVideo(pagNumber,pagSize,para));
    }

    @GetMapping("/getInfoPromotionVideo")
    @ApiOperation(value = "根据id获取政务宣传数据")
    public R getInfoPromotionVideo(Integer id){
        return R.ok(iInfoPromotionVideoService.getById(id));
    }

    @PostMapping("/saveInfoPromotionVideo")
    @ApiOperation(value = "新增政务宣传数据")
    public R saveInfoPromotionVideo(InfoPromotionVideo infoPromotionVideo){
        infoPromotionVideo.setCreateTime(new Date());
        boolean save = iInfoPromotionVideoService.save(infoPromotionVideo);
        if(save){
            return R.ok("添加成功");
        }
        return R.ok("添加失败");
    }
    @PostMapping("/updateInfoPromotionVideo")
    @ApiOperation(value = "修改政务宣传数据")
    public R updateInfoPromotionVideo(InfoPromotionVideo infoPromotionVideo){
        boolean save = iInfoPromotionVideoService.updateById(infoPromotionVideo);
        if(save){
            return R.ok("修改成功");
        }
        return R.ok("修改成功");
    }

    @DeleteMapping("/deleteInfoPromotionVideo")
    @ApiOperation(value = "根据id删除政务宣传数据")
    public R deleteInfoPromotionVideo(Integer id){
        return R.ok(iInfoPromotionVideoService.removeById(id));
    }

    @GetMapping("/listInfoActivityPropagate")
    @ApiOperation(value = "获取活动宣传列表")
    public R listInfoActivityPropagate(Integer pagNumber,Integer pagSize,String unitName){
        if(pagNumber==null||pagSize==null) {
            pagNumber=1;
            pagSize=10;
        }
        Map<String,Object> para = new HashMap<>();
        para.put("departName", unitName);
        return R.ok(iInfoActivityPropagateService.selectPageListInfoActivityPropagate(pagNumber,pagSize,para));
    }

    @PostMapping("/saveInfoActivityPropagate")
    @ApiOperation(value = "新增活动宣传数据")
    public R saveInfoActivityPropagate(InfoActivityPropagate infoActivityPropagate){
        infoActivityPropagate.setCreateTime(new Date());
        boolean save = iInfoActivityPropagateService.save(infoActivityPropagate);
        if(save){
            return R.ok("添加成功");
        }
        return R.ok("添加失败");
    }

    @GetMapping("/getInfoActivityPropagate")
    @ApiOperation(value = "根据id获取活动宣传数据")
    public R getInfoActivityPropagate(Integer id){
        return R.ok(iInfoActivityPropagateService.getById(id));
    }

    @PostMapping("/updateInfoActivityPropagate")
    @ApiOperation(value = "修改活动宣传数据")
    public R updateInfoActivityPropagate(InfoActivityPropagate infoActivityPropagate){
        boolean save = iInfoActivityPropagateService.updateById(infoActivityPropagate);
        if(save){
            return R.ok("修改成功");
        }
        return R.ok("修改失败");
    }

    @DeleteMapping("/deleteInfoActivityPropagate")
    @ApiOperation(value = "根据id删除活动宣传数据")
    public R deleteInfoActivityPropagate(Integer id){
        return R.ok(iInfoActivityPropagateService.removeById(id));
    }

    @GetMapping("/listInfoEvacuation")
    @ApiOperation(value = "获取疏导分流列表")
    public R listInfoEvacuation(Integer pagNumber,Integer pagSize,String unitName){
        if(pagNumber==null||pagSize==null) {
            pagNumber=1;
            pagSize=10;
        }
        Map<String,Object> para = new HashMap<>();
        para.put("departName", unitName);
        return R.ok(iInfoEvacuationService.selectPageListInfoEvacuation(pagNumber,pagSize,para));
    }

    @GetMapping("/getInfoEvacuation")
    @ApiOperation(value = "根据id获取疏导分流数据")
    public R getInfoEvacuation(Integer id){
        return R.ok(iInfoEvacuationService.getById(id));
    }

    @PostMapping("/saveInfoEvacuation")
    @ApiOperation(value = "新增疏导分流数据")
    public R saveInfoEvacuation(InfoEvacuation infoEvacuation){
        infoEvacuation.setCreateTime(new Date());
        boolean save = iInfoEvacuationService.save(infoEvacuation);
        if(save){
            return R.ok("添加成功");
        }
        return R.ok("添加失败");
    }
    @PostMapping("/updateInfoEvacuation")
    @ApiOperation(value = "修改疏导分流数据")
    public R updateInfoEvacuation(InfoEvacuation infoEvacuation){
        boolean save = iInfoEvacuationService.updateById(infoEvacuation);
        if(save){
            return R.ok("修改成功");
        }
        return R.ok("修改失败");
    }

    @DeleteMapping("/deleteInfoEvacuation")
    @ApiOperation(value = "根据id删除疏导分流数据")
    public R deleteInfoEvacuation(Integer id){
        return R.ok(iInfoEvacuationService.removeById(id));
    }
}
