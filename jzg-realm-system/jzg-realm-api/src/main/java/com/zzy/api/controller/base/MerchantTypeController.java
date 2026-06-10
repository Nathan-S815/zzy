package com.zzy.api.controller.base;

import com.zzy.api.service.base.IMerchantTypeService;
import com.zzy.core.dto.R;
import com.zzy.db.entity.base.MerchantType;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/merchantype")
@Api(value = "商户类别接口", tags = "商户类别接口")
public class MerchantTypeController {

    @Autowired
    private IMerchantTypeService iMerchantTypeService;

    @GetMapping("/public/listMerchantType")
    @ApiOperation(value = "获取商户类别列表公共")
    public R listpublicMerchantType(){
        List<MerchantType> list = iMerchantTypeService.list();
        if(list.size()>0){
            return R.ok(list);
        }
        return R.ok("暂无数据");
    }

    @GetMapping("/listMerchantType")
    @ApiOperation(value = "获取商户类别列表")
    public R listMerchantType(){
        List<MerchantType> list = iMerchantTypeService.list();
        if(list.size()>0){
            return R.ok(list);
        }
        return R.ok("暂无数据");
    }

    @GetMapping("/getMerchantTypeById")
    @ApiOperation(value = "根据id获取商户类别信息")
    public R getMerchantTypeById(Integer id){
        MerchantType byId = iMerchantTypeService.getById(id);
        if(byId!=null){
            return R.ok(byId);
        }
        return R.ok("暂无数据");
    }

    @PostMapping("/saveMerchantType")
    @ApiOperation(value = "新增商户类别")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "typeName", value = "商户类别", required = false, paramType = "query", dataType = "String")
    })
    public R saveMerchantType(String typeName){
        MerchantType merchantType = new MerchantType();
        merchantType.setTypeName(typeName);
        merchantType.setCreateTime(LocalDateTime.now());
        boolean save = iMerchantTypeService.save(merchantType);
        if(save){
            return R.ok("新增成功");
        }
        return R.ok("新增失败");
    }

    @PostMapping("/updateMerchantType")
    @ApiOperation(value = "修改商户类别")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "id", required = false, paramType = "query", dataType = "Integer"),
            @ApiImplicitParam(name = "typeName", value = "商户类别", required = false, paramType = "query", dataType = "String")
    })
    public R updateMerchantType(Integer id,String typeName){
        MerchantType merchantType = new MerchantType();
        merchantType.setId(id);
        merchantType.setTypeName(typeName);
        boolean b = iMerchantTypeService.updateById(merchantType);
        if(b){
            return R.ok("修改成功");
        }
        return R.ok("修改失败");
    }

    @DeleteMapping("/deleteMerchantType")
    @ApiOperation(value = "删除商户类别")
    public R deleteMerchantType(Integer id){
        boolean b = iMerchantTypeService.removeById(id);
        if(b){
            return R.ok("删除成功");
        }
        return R.ok("删除失败");
    }

}
