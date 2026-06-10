package com.zzy.api.controller.base;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.zzy.api.service.base.*;
import com.zzy.core.dto.R;
import com.zzy.core.utils.TimeDateUtil;
import com.zzy.db.dao.base.BaseScenicMapper;
import com.zzy.db.entity.base.*;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import org.springframework.stereotype.Controller;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 基础信息 前端控制器
 * </p>
 *
 * @author zzy
 * @since 2020-04-13
 */
@RestController
@RequestMapping("/base")
@Api(value = "基础数据接口", tags = "基础数据接口")
public class BaseScenicController {

    @Autowired
    private IBaseScenicService iBaseScenicService;

    @Autowired
    private IBaseGuideService iBaseGuideService;

    @Autowired
    private IBaseHotelService iBaseHotelService;

    @Autowired
    private IBaseRecreationService iBaseRecreationService;

    @Autowired
    private IBaseRestaurantService iBaseRestaurantService;

    @Autowired
    private IBaseShoppingService iBaseShoppingService;

    @Autowired
    private IBaseTravelService iBaseTravelService;

    @GetMapping("/listBaseScenic")
    @ApiOperation(value = "获取景区列表")
    public R listBaseScenic(Integer pagNumber,Integer pagSize,String unitName){
        if(pagNumber==null||pagSize==null) {
            pagNumber=1;
            pagSize=10;
        }
        Map<String,Object> para = new HashMap<>();
        para.put("departName", unitName);
        return R.ok(iBaseScenicService.selectPageList(pagNumber,pagSize,para));
    }
    @GetMapping("/getBaseScenicById")
    @ApiOperation(value = "根据id获取景区信息")
    public R getBaseScenicById(Integer id){
        BaseScenic byId = iBaseScenicService.getById(id);
        if(byId!=null){
            return R.ok(byId);
        }else {
            return R.ok("暂无数据");
        }
    }

    @PostMapping("/svaeBaseScenic")
    @ApiOperation(value = "新增景区")
    public R svaeBaseScenic(BaseScenic baseScenic){
        baseScenic.setCreateTime(new Date());
        boolean save = iBaseScenicService.save(baseScenic);
        return R.ok(save);
    }

    @PostMapping("/updateBaseScenic")
    @ApiOperation(value = "修改景区")
    public R updateBaseScenic(BaseScenic baseScenic){
        boolean save = iBaseScenicService.updateById(baseScenic);
        return R.ok(save);
    }

    @DeleteMapping("/deleteBaseScenicById")
    @ApiOperation(value = "根据id删除景区")
    public R deleteBaseScenicById(Integer id){
        boolean b = iBaseScenicService.removeById(id);
        return R.ok(b);
    }

    @GetMapping("/listBaseGuide")
    @ApiOperation(value = "获取导游列表")
    public R listBaseGuide(Integer pagNumber,Integer pagSize,String unitName){
        if(pagNumber==null||pagSize==null) {
            pagNumber=1;
            pagSize=10;
        }
        Map<String,Object> para = new HashMap<>();
        para.put("departName", unitName);
        return R.ok(iBaseGuideService.selectPageList(pagNumber,pagSize,para));

    }
    @GetMapping("/getBaseGuideById")
    @ApiOperation(value = "根据id获取导游信息")
    public R getBaseGuideById(Integer id){
        BaseGuide byId = iBaseGuideService.getById(id);
        if(byId!=null){
            return R.ok(byId);
        }else {
            return R.ok("暂无数据");
        }
    }

    @PostMapping("/svaeBaseGuide")
    @ApiOperation(value = "新增导游")
    public R svaeBaseGuide(BaseGuide baseGuide,String applyTimeVO,String approvalTimeVO){
        baseGuide.setApplyTime(TimeDateUtil.strToDate(applyTimeVO));
        baseGuide.setApprovalTime(TimeDateUtil.strToDate(approvalTimeVO));
        baseGuide.setCreateTime(new Date());
        boolean save = iBaseGuideService.save(baseGuide);
        return R.ok(save);
    }

    @PostMapping("/updateBaseGuide")
    @ApiOperation(value = "修改导游")
    public R updateBaseGuide(BaseGuide baseGuide,String applyTimeVO,String approvalTimeVO){
        baseGuide.setApplyTime(TimeDateUtil.strToDate(applyTimeVO));
        baseGuide.setApprovalTime(TimeDateUtil.strToDate(approvalTimeVO));
        boolean save = iBaseGuideService.updateById(baseGuide);
        return R.ok(save);
    }

    @DeleteMapping("/deleteBaseGuideById")
    @ApiOperation(value = "根据id删除导游")
    public R deleteBaseGuideById(Integer id){
        boolean b = iBaseGuideService.removeById(id);
        return R.ok(b);
    }

    @GetMapping("/listBaseHotel")
    @ApiOperation(value = "获取旅游饭店列表")
    public R listBaseHotel(Integer pagNumber,Integer pagSize,String unitName){
        if(pagNumber==null||pagSize==null) {
            pagNumber=1;
            pagSize=10;
        }
        Map<String,Object> para = new HashMap<>();
        para.put("departName", unitName);
        return R.ok(iBaseHotelService.selectPageList(pagNumber,pagSize,para));
    }

    @GetMapping("/getBaseHotelById")
    @ApiOperation(value = "根据id获取旅游饭店信息")
    public R getBaseHotelById(Integer id){
        BaseHotel byId = iBaseHotelService.getById(id);
        if(byId!=null){
            return R.ok(byId);
        }else {
            return R.ok("暂无数据");
        }
    }

    @PostMapping("/svaeBaseHotel")
    @ApiOperation(value = "新增旅游饭店")
    public R svaeBaseHotel(BaseHotel baseHotel){
        baseHotel.setCreateTime(new Date());
        boolean save = iBaseHotelService.save(baseHotel);
        return R.ok(save);
    }

    @PostMapping("/updateBaseHotel")
    @ApiOperation(value = "修改旅游饭店")
    public R updateBaseHotel(BaseHotel baseHotel){
        boolean save = iBaseHotelService.save(baseHotel);
        return R.ok(save);
    }

    @DeleteMapping("/deleteBaseHotelById")
    @ApiOperation(value = "根据id删除旅游饭店")
    public R deleteBaseHotelById(Integer id){
        boolean b = iBaseHotelService.removeById(id);
        return R.ok(b);
    }

    @GetMapping("/listBaseRecreation")
    @ApiOperation(value = "获取休闲娱乐列表")
    public R listBaseRecreation(Integer pagNumber,Integer pagSize,String unitName){
        if(pagNumber==null||pagSize==null) {
            pagNumber=1;
            pagSize=10;
        }
        Map<String,Object> para = new HashMap<>();
        para.put("departName", unitName);
        return R.ok(iBaseRecreationService.selectPageList(pagNumber,pagSize,para));
    }

    @GetMapping("/getBaseRecreationById")
    @ApiOperation(value = "根据id获取休闲娱乐信息")
    public R getBaseRecreationById(Integer id){
        BaseRecreation byId = iBaseRecreationService.getById(id);
        if(byId!=null){
            return R.ok(byId);
        }else {
            return R.ok("暂无数据");
        }
    }

    @PostMapping("/svaeBaseRecreation")
    @ApiOperation(value = "新增休闲娱乐")
    public R svaeBaseRecreation(BaseRecreation baseRecreation){
        baseRecreation.setCreateTime(new Date());
        boolean save = iBaseRecreationService.save(baseRecreation);
        return R.ok(save);
    }

    @PostMapping("/updateBaseRecreation")
    @ApiOperation(value = "修改休闲娱乐")
    public R updateBaseRecreation(BaseRecreation baseRecreation){
        boolean save = iBaseRecreationService.updateById(baseRecreation);
        return R.ok(save);
    }

    @DeleteMapping("/deleteBaseRecreationById")
    @ApiOperation(value = "根据id删除休闲娱乐")
    public R deleteBaseRecreationById(Integer id){
        boolean b = iBaseRecreationService.removeById(id);
        return R.ok(b);
    }

    @GetMapping("/listBaseRestaurant")
    @ApiOperation(value = "获取餐厅列表")
    public R listBaseRestaurant(Integer pagNumber,Integer pagSize,String unitName){
        if(pagNumber==null||pagSize==null) {
            pagNumber=1;
            pagSize=10;
        }
        Map<String,Object> para = new HashMap<>();
        para.put("departName", unitName);
        return R.ok(iBaseRestaurantService.selectPageList(pagNumber,pagSize,para));
    }

    @GetMapping("/getBaseRestaurantById")
    @ApiOperation(value = "根据id获取餐厅信息")
    public R getBaseRestaurantById(Integer id){
        BaseRestaurant byId = iBaseRestaurantService.getById(id);
        if(byId!=null){
            return R.ok(byId);
        }else {
            return R.ok("暂无数据");
        }
    }

    @PostMapping("/svaeBaseRestaurant")
    @ApiOperation(value = "新增餐厅")
    public R svaeBaseRestaurant(BaseRestaurant baseRestaurant){
        baseRestaurant.setCreateTime(new Date());
        boolean save = iBaseRestaurantService.save(baseRestaurant);
        return R.ok(save);
    }

    @PostMapping("/updateBaseRestaurant")
    @ApiOperation(value = "修改餐厅")
    public R updateBaseRestaurant(BaseRestaurant baseRestaurant){
        boolean save = iBaseRestaurantService.updateById(baseRestaurant);
        return R.ok(save);
    }

    @DeleteMapping("/deleteBaseRestaurantById")
    @ApiOperation(value = "根据id删除餐厅")
    public R deleteBaseRestaurantById(Integer id){
        boolean b = iBaseRestaurantService.removeById(id);
        return R.ok(b);
    }

    @GetMapping("/listBaseShopping")
    @ApiOperation(value = "获取购物场所列表")
    public R listBaseShopping(Integer pagNumber,Integer pagSize,String unitName){
        if(pagNumber==null||pagSize==null) {
            pagNumber=1;
            pagSize=10;
        }
        Map<String,Object> para = new HashMap<>();
        para.put("departName", unitName);
        return R.ok(iBaseShoppingService.selectPageList(pagNumber,pagSize,para));
    }

    @GetMapping("/getBaseShoppingById")
    @ApiOperation(value = "根据id获取购物场所信息")
    public R getBaseShoppingById(Integer id){
        BaseShopping byId = iBaseShoppingService.getById(id);
        if(byId!=null){
            return R.ok(byId);
        }else {
            return R.ok("暂无数据");
        }
    }

    @PostMapping("/svaeBaseShopping")
    @ApiOperation(value = "新增购物场所")
    public R svaeBaseShopping(BaseShopping baseShopping){
        baseShopping.setCreateTime(new Date());
        boolean save = iBaseShoppingService.save(baseShopping);
        return R.ok(save);
    }

    @PostMapping("/updateBaseShopping")
    @ApiOperation(value = "修改购物场所")
    public R updateBaseShopping(BaseShopping baseShopping){
        boolean save = iBaseShoppingService.updateById(baseShopping);
        return R.ok(save);
    }

    @DeleteMapping("/deleteBaseShoppingById")
    @ApiOperation(value = "根据id删除购物场所")
    public R deleteBaseShoppingById(Integer id){
        boolean b = iBaseShoppingService.removeById(id);
        return R.ok(b);
    }

    @GetMapping("/listBaseTravel")
    @ApiOperation(value = "获取旅行社所列表")
    public R listBaseTravel(Integer pagNumber,Integer pagSize,String unitName){
        if(pagNumber==null||pagSize==null) {
            pagNumber=1;
            pagSize=10;
        }
        Map<String,Object> para = new HashMap<>();
        para.put("departName", unitName);
        return R.ok(iBaseTravelService.selectPageList(pagNumber,pagSize,para));
    }

    @GetMapping("/getBaseTravelById")
    @ApiOperation(value = "根据id获取旅行社信息")
    public R getBaseTravelById(Integer id){
        BaseTravel byId = iBaseTravelService.getById(id);
        if(byId!=null){
            return R.ok(byId);
        }else {
            return R.ok("暂无数据");
        }
    }

    @PostMapping("/svaeBaseTravel")
    @ApiOperation(value = "新增旅行社")
    public R svaeBaseTravel(BaseTravel baseTravel){
        baseTravel.setCreateTime(new Date());
        boolean save = iBaseTravelService.save(baseTravel);
        return R.ok(save);
    }

    @PostMapping("/updateBaseTravel")
    @ApiOperation(value = "修改旅行社")
    public R updateBaseTravel(BaseTravel baseTravel){
        boolean save = iBaseTravelService.updateById(baseTravel);
        return R.ok(save);
    }

    @DeleteMapping("/deleteBaseTravelById")
    @ApiOperation(value = "根据id删除旅行社")
    public R deleteBaseTravelById(Integer id){
        boolean b = iBaseTravelService.removeById(id);
        return R.ok(b);
    }
}
