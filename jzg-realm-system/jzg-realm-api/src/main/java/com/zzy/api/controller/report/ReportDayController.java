package com.zzy.api.controller.report;

import cn.hutool.core.date.DateUtil;
import com.zzy.api.service.base.*;
import com.zzy.api.service.reportbase.*;
import com.zzy.core.dto.R;
import com.zzy.core.utils.TimeDateUtil;
import com.zzy.core.utils.WeatherUtil;
import com.zzy.db.entity.base.*;
import com.zzy.db.entity.reportbase.*;
import com.zzy.security.common.JwtUtil;
import com.zzy.security.dto.CustomUser;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.util.Date;

@RestController
@RequestMapping("/reportDay")
@Api(value = "每日数据上报接口", tags = "每日数据上报接口")
public class ReportDayController {

    @Autowired
    private IBaseHotelReportService iBaseHotelReportService;
    @Autowired
    private IReportBaseHotelService iReportBaseHotelService;
    @Autowired
    private IBaseTrafficReportService iBaseTrafficReportService;
    @Autowired
    private IReportBaseTrafficService iReportBaseTrafficService;
    @Autowired
    private IBaseRecreationReportService iBaseRecreationReportService;
    @Autowired
    private IReportBaseEntertainmentService iReportBaseEntertainmentService;
    @Autowired
    private IBaseShoppingReportService iBaseShoppingReportService;
    @Autowired
    private IReportBaseShoppingService iReportBaseShoppingService;
    @Autowired
    private IBaseRestaurantReportService iBaseRestaurantReportService;
    @Autowired
    private IReportBaseRestaurantService iReportBaseRestaurantService;
    @Autowired
    private IBaseTravelReportService iBaseTravelReportService;
    @Autowired
    private IReportBaseTravelService iReportBaseTravelService;
    @Autowired
    private IBaseTravelLineReportService iBaseTravelLineReportService;
    @Autowired
    private IBaseScenicReportService iBaseScenicReportService;
    @Autowired
    private IReportBaseScenicService iReportBaseScenicService;
    @Autowired
    private IBaseGuideReportService iBaseGuideReportService;
    @Autowired
    private IReportBaseGuideService iReportBaseGuideService;
    @Autowired
    private IBaseNoticeService iBaseNoticeService;

    @PostMapping("/saveBaseNotice")
    @ApiOperation(value = "新增公告信息")
    public R saveBaseNoticeReport(BaseNotice baseNotice, HttpServletRequest request) throws Exception {
        try {
            baseNotice.setCreateTime(DateUtil.format(new Date(),"yyyy-MM-dd HH:mm:ss"));
            baseNotice.setReportTime(DateUtil.format(new Date(),"yyyy-MM-dd HH:mm:ss"));
            baseNotice.setIsDelete(0);
            boolean save = iBaseNoticeService.save(baseNotice);
            if(save) {
                return R.ok("新增成功");
            }else {
                return R.ok("新增失败");
            }
        }catch (Exception e){
            return R.ok("新增失败");
        }
    }

    @PostMapping("/deleteBaseNotice")
    @ApiOperation(value = "删除公告信息")
    public R deleteBaseNotice(Integer id){
        BaseNotice byId = iBaseNoticeService.getById(id);
        byId.setIsDelete(1);
        boolean save = iBaseNoticeService.updateById(byId);
        if(save) {
            return R.ok("删除成功");
        }
        return R.ok("删除失败");
    }

    @PostMapping("/updateBaseNotice")
    @ApiOperation(value = "修改公告信息")
    public R updateBaseNotice(BaseNotice baseNotice){
        boolean save = iBaseNoticeService.updateById(baseNotice);
        if(save) {
            return R.ok("修改成功");
        }
        return R.ok("修改失败");
    }

    @PostMapping("/saveBaseGuideReport")
    @ApiOperation(value = "新增导游每日上报信息")
    public R saveReportBaseGuide(BaseGuideReport baseGuideReport, HttpServletRequest request) throws Exception {
        try {
            baseGuideReport.setCreateTime(LocalDateTime.now());
            baseGuideReport.setReportTime(LocalDateTime.now());
            ReportBaseGuide reportBaseGuide = iReportBaseGuideService.getById(baseGuideReport.getGuideId());
            Integer type = baseGuideReport.getType();
            Integer score = baseGuideReport.getScore();
            if (type == 0){
                reportBaseGuide.setCredit(reportBaseGuide.getCredit()+score);
                baseGuideReport.setCredit(reportBaseGuide.getCredit()+score);
            }else if (type == 1){
                reportBaseGuide.setCredit(reportBaseGuide.getCredit()-score);
                baseGuideReport.setCredit(reportBaseGuide.getCredit()-score);
            }
            reportBaseGuide.setUpdateTime(LocalDateTime.now());
            iReportBaseGuideService.updateById(reportBaseGuide);
            boolean save = iBaseGuideReportService.save(baseGuideReport);
            if(save) {
                return R.ok("新增成功");
            }else {
                return R.ok("新增失败");
            }
        }catch (Exception e){
            return R.ok("新增失败");
        }
    }

    @PostMapping("/saveBaseScenicReport")
    @ApiOperation(value = "新增景区每日上报信息")
    public R saveReportBaseScenic(BaseScenicReport baseScenicReport,String reportTimeVO, HttpServletRequest request) throws Exception {
        CustomUser cu = JwtUtil.getFromJwt(request);
        baseScenicReport.setUserId(cu.getUserId());
        baseScenicReport.setCreateTime(new Date());
        if(reportTimeVO!=null){
            baseScenicReport.setReportTime(TimeDateUtil.strToDate(reportTimeVO+" 12:00:00"));
        }else {
            baseScenicReport.setReportTime(new Date());
        }
        if (baseScenicReport.getIdDelete() == null){
            baseScenicReport.setIdDelete("0");
        }
        try {
        ReportBaseScenic reportBaseScenic = iReportBaseScenicService.getById(baseScenicReport.getSubScenicId());
        baseScenicReport.setSubScenicName(reportBaseScenic.getScenicName());
    }catch (Exception e){
        return R.ok("请完善基础信息");
    }
        boolean save = iBaseScenicReportService.save(baseScenicReport);
        if(save) {
            return R.ok("新增成功");
        }
        return R.ok("新增失败");
    }

    @PostMapping("/updateReportBaseScenic")
    @ApiOperation(value = "修改景区每日上报信息")
    public R updateReportBaseScenic(BaseScenicReport baseScenicReport){
        boolean save = iBaseScenicReportService.updateById(baseScenicReport);
        if(save) {
            return R.ok("修改成功");
        }
        return R.ok("修改失败");
    }

    @PostMapping("/deleteReportBaseScenic")
    @ApiOperation(value = "删除景区每日上报信息")
    public R deleteReportBaseScenic(Integer id){
        BaseScenicReport byId = iBaseScenicReportService.getById(id);
        byId.setIdDelete("1");
        boolean save = iBaseScenicReportService.updateById(byId);
        if(save) {
            return R.ok("删除成功");
        }
        return R.ok("删除失败");
    }

    @PostMapping("/saveBaseHotelReport")
    @ApiOperation(value = "新增酒店每日上报信息")
    public R saveReportBaseHotel(BaseHotelReport baseHotelReport,String reportTimeVO, HttpServletRequest request) throws Exception {
        CustomUser cu = JwtUtil.getFromJwt(request);
        baseHotelReport.setUserId(cu.getUserId());
        baseHotelReport.setCreateTime(new Date());
        if(reportTimeVO!=null){
            baseHotelReport.setReportTime(TimeDateUtil.strToDate(reportTimeVO+" 12:00:00"));
        }else {
            baseHotelReport.setReportTime(new Date());
        }
        if (baseHotelReport.getIdDelete() == null){
            baseHotelReport.setIdDelete("0");
        }
        if (baseHotelReport.getStayOneIn() == null){
            baseHotelReport.setStayOneIn(0);
        }
        if (baseHotelReport.getStayTwoIn() == null){
            baseHotelReport.setStayOneIn(0);
        }
        if (baseHotelReport.getStayThreeIn() == null){
            baseHotelReport.setStayOneIn(0);
        }
        if (baseHotelReport.getStayFourIn() == null){
            baseHotelReport.setStayOneIn(0);
        }
        if (baseHotelReport.getStayFiveIn() == null){
            baseHotelReport.setStayOneIn(0);
        }
        if (baseHotelReport.getStaySixIn() == null){
            baseHotelReport.setStayOneIn(0);
        }
        if (baseHotelReport.getStayOneOut() == null){
            baseHotelReport.setStayOneIn(0);
        }
        if (baseHotelReport.getStayTwoOut() == null){
            baseHotelReport.setStayOneIn(0);
        }
        if (baseHotelReport.getStayThreeOut() == null){
            baseHotelReport.setStayOneIn(0);
        }
        if (baseHotelReport.getStayFourOut() == null){
            baseHotelReport.setStayOneIn(0);
        }
        if (baseHotelReport.getStayFiveOut() == null){
            baseHotelReport.setStayOneIn(0);
        }
        if (baseHotelReport.getStaySixOut() == null){
            baseHotelReport.setStayOneIn(0);
        }
        try {
            ReportBaseHotel reportBaseHotel = iReportBaseHotelService.getById(baseHotelReport.getSubHotelId());
            baseHotelReport.setSubHotelName(reportBaseHotel.getHotelName());
            baseHotelReport.setSubScenicId(reportBaseHotel.getScenicId().toString());
        }catch (Exception e){
            return R.ok("请完善基础信息");
        }
        boolean save = iBaseHotelReportService.save(baseHotelReport);
        if(save) {
            return R.ok("新增成功");
        }
        return R.ok("新增失败");
    }
    @PostMapping("/updateReportBaseHotel")
    @ApiOperation(value = "修改酒店每日上报信息")
    public R updateReportBaseHotel(BaseHotelReport baseHotelReport){
        if (baseHotelReport.getStayOneIn() == null){
            baseHotelReport.setStayOneIn(0);
        }
        if (baseHotelReport.getStayTwoIn() == null){
            baseHotelReport.setStayTwoIn(0);
        }
        if (baseHotelReport.getStayThreeIn() == null){
            baseHotelReport.setStayThreeIn(0);
        }
        if (baseHotelReport.getStayFourIn() == null){
            baseHotelReport.setStayFourIn(0);
        }
        if (baseHotelReport.getStayFiveIn() == null){
            baseHotelReport.setStayFiveIn(0);
        }
        if (baseHotelReport.getStaySixIn() == null){
            baseHotelReport.setStaySixIn(0);
        }
        if (baseHotelReport.getStayOneOut() == null){
            baseHotelReport.setStayOneOut(0);
        }
        if (baseHotelReport.getStayTwoOut() == null){
            baseHotelReport.setStayTwoOut(0);
        }
        if (baseHotelReport.getStayThreeOut() == null){
            baseHotelReport.setStayThreeOut(0);
        }
        if (baseHotelReport.getStayFourOut() == null){
            baseHotelReport.setStayFourOut(0);
        }
        if (baseHotelReport.getStayFiveOut() == null){
            baseHotelReport.setStayFiveOut(0);
        }
        if (baseHotelReport.getStaySixOut() == null){
            baseHotelReport.setStaySixOut(0);
        }
        boolean save = iBaseHotelReportService.updateById(baseHotelReport);
        if(save) {
            return R.ok("修改成功");
        }
        return R.ok("修改失败");
    }

    @PostMapping("/deleteReportBaseHotel")
    @ApiOperation(value = "删除酒店每日上报信息")
    public R deleteReportBaseHotel(Integer id){
        BaseHotelReport byId = iBaseHotelReportService.getById(id);
        byId.setIdDelete("1");
        boolean save = iBaseHotelReportService.updateById(byId);
        if(save) {
            return R.ok("删除成功");
        }
        return R.ok("删除失败");
    }

    @PostMapping("/saveBaseTrafficReport")
    @ApiOperation(value = "新增交通每日上报信息")
    public R saveReportBaseTraffic(BaseTrafficReport baseTrafficReport,String reportTimeVO, HttpServletRequest request) throws Exception {
        CustomUser cu = JwtUtil.getFromJwt(request);
        baseTrafficReport.setUserId(cu.getUserId());
        baseTrafficReport.setCreateTime(LocalDateTime.now());
        if(reportTimeVO!=null){
            baseTrafficReport.setReportTime(TimeDateUtil.strToDate(reportTimeVO+" 12:00:00"));
        }else {
            baseTrafficReport.setReportTime(new Date());
        }
        if (baseTrafficReport.getIdDelete() == null){
            baseTrafficReport.setIdDelete("0");
        }
        try {
        ReportBaseTraffic reportBaseTraffic = iReportBaseTrafficService.getById(baseTrafficReport.getSubTrafficId());
        baseTrafficReport.setSubTrafficName(reportBaseTraffic.getTrafficName());
        baseTrafficReport.setSubScenicId(reportBaseTraffic.getScenicId());
    }catch (Exception e){
        return R.ok("请完善基础信息");
    }
        boolean save = iBaseTrafficReportService.save(baseTrafficReport);
        if(save) {
            return R.ok("新增成功");
        }
        return R.ok("新增失败");
    }

    @PostMapping("/updateReportBaseTraffic")
    @ApiOperation(value = "修改交通每日上报信息")
    public R updateReportBaseTraffic(BaseTrafficReport baseTrafficReport){
        boolean save = iBaseTrafficReportService.updateById(baseTrafficReport);
        if(save) {
            return R.ok("修改成功");
        }
        return R.ok("修改失败");
    }

    @PostMapping("/deleteReportBaseTraffic")
    @ApiOperation(value = "删除交通每日上报信息")
    public R deleteReportBaseTraffic(Integer id){
        BaseTrafficReport byId = iBaseTrafficReportService.getById(id);
        byId.setIdDelete("1");
        boolean save = iBaseTrafficReportService.updateById(byId);
        if(save) {
            return R.ok("删除成功");
        }
        return R.ok("删除失败");
    }

    @PostMapping("/saveBaseRecreationReport")
    @ApiOperation(value = "新增娱乐每日上报信息")
    public R saveReportBaseRecreation(BaseRecreationReport baseRecreationReport,String reportTimeVO, HttpServletRequest request) throws Exception {
        CustomUser cu = JwtUtil.getFromJwt(request);
        baseRecreationReport.setUserId(cu.getUserId());
        baseRecreationReport.setCreateTime(LocalDateTime.now());
        if(reportTimeVO!=null){
            baseRecreationReport.setReportTime(TimeDateUtil.strToDate(reportTimeVO+" 12:00:00"));
        }else {
            baseRecreationReport.setReportTime(new Date());
        }
        if (baseRecreationReport.getIdDelete() == null){
            baseRecreationReport.setIdDelete("0");
        }
        try {
        ReportBaseEntertainment reportBaseEntertainment = iReportBaseEntertainmentService.getById(baseRecreationReport.getSubRecreationId());
        baseRecreationReport.setSubRecreationName(reportBaseEntertainment.getEntertainmentName());
        baseRecreationReport.setSubScenicId(reportBaseEntertainment.getScenicId());
    }catch (Exception e){
        return R.ok("请完善基础信息");
    }
        boolean save = iBaseRecreationReportService.save(baseRecreationReport);
        if(save) {
            return R.ok("新增成功");
        }
        return R.ok("新增失败");
    }

    @PostMapping("/updateReportBaseRecreation")
    @ApiOperation(value = "修改娱乐每日上报信息")
    public R updateReportBaseRecreation(BaseRecreationReport baseRecreationReport){
        boolean save = iBaseRecreationReportService.updateById(baseRecreationReport);
        if(save) {
            return R.ok("修改成功");
        }
        return R.ok("修改失败");
    }

    @PostMapping("/deleteReportBaseRecreation")
    @ApiOperation(value = "删除娱乐每日上报信息")
    public R deleteReportBaseRecreation(Integer id){
        BaseRecreationReport byId = iBaseRecreationReportService.getById(id);
        byId.setIdDelete("1");
        boolean save = iBaseRecreationReportService.updateById(byId);
        if(save) {
            return R.ok("删除成功");
        }
        return R.ok("删除失败");
    }

    @PostMapping("/saveBaseShoppingReport")
    @ApiOperation(value = "新增购物每日上报信息")
    public R saveReportBaseShopping(BaseShoppingReport baseShoppingReport,String reportTimeVO, HttpServletRequest request) throws Exception {
        CustomUser cu = JwtUtil.getFromJwt(request);
        baseShoppingReport.setUserId(cu.getUserId());
        baseShoppingReport.setCreateTime(LocalDateTime.now());
        if(reportTimeVO!=null){
            baseShoppingReport.setReportTime(TimeDateUtil.strToDate(reportTimeVO+" 12:00:00"));
        }else {
            baseShoppingReport.setReportTime(new Date());
        }
        if (baseShoppingReport.getIdDelete() == null){
            baseShoppingReport.setIdDelete("0");
        }
        try {
        ReportBaseShopping reportBaseShopping = iReportBaseShoppingService.getById(baseShoppingReport.getSubShoppingId());
        baseShoppingReport.setSubShoppingName(reportBaseShopping.getShoppingName());
        baseShoppingReport.setSubScenicId(reportBaseShopping.getScenicId());
    }catch (Exception e){
        return R.ok("请完善基础信息");
    }
        boolean save = iBaseShoppingReportService.save(baseShoppingReport);
        if(save) {
            return R.ok("新增成功");
        }
        return R.ok("新增失败");
    }

    @PostMapping("/updateReportBaseShopping")
    @ApiOperation(value = "修改购物每日上报信息")
    public R updateReportBaseShopping(BaseShoppingReport baseShoppingReport){
        boolean save = iBaseShoppingReportService.updateById(baseShoppingReport);
        if(save) {
            return R.ok("修改成功");
        }
        return R.ok("修改失败");
    }

    @PostMapping("/deleteReportBaseShopping")
    @ApiOperation(value = "删除购物每日上报信息")
    public R deleteReportBaseShopping(Integer id){
        BaseShoppingReport byId = iBaseShoppingReportService.getById(id);
        byId.setIdDelete("1");
        boolean save = iBaseShoppingReportService.updateById(byId);
        if(save) {
            return R.ok("删除成功");
        }
        return R.ok("删除失败");
    }

    @PostMapping("/saveBaseRestaurantReport")
    @ApiOperation(value = "新增餐饮每日上报信息")
    public R saveReportBaseRestaurant(BaseRestaurantReport baseRestaurantReport,String reportTimeVO, HttpServletRequest request) throws Exception {
        CustomUser cu = JwtUtil.getFromJwt(request);
        baseRestaurantReport.setUserId(cu.getUserId());
        baseRestaurantReport.setCreateTime(LocalDateTime.now());
        if(reportTimeVO!=null){
            baseRestaurantReport.setReportTime(TimeDateUtil.strToDate(reportTimeVO+" 12:00:00"));
        }else {
            baseRestaurantReport.setReportTime(new Date());
        }
        if (baseRestaurantReport.getIdDelete() == null){
            baseRestaurantReport.setIdDelete("0");
        }
        try {
        ReportBaseRestaurant reportBaseRestaurant = iReportBaseRestaurantService.getById(baseRestaurantReport.getSubRestaurantId());
        baseRestaurantReport.setSubRestaurantName(reportBaseRestaurant.getRestaurantName());
        baseRestaurantReport.setSubScenicId(reportBaseRestaurant.getScenicId());
    }catch (Exception e){
        return R.ok("请完善基础信息");
    }
        boolean save = iBaseRestaurantReportService.save(baseRestaurantReport);
        if(save) {
            return R.ok("新增成功");
        }
        return R.ok("新增失败");
    }

    @PostMapping("/updateReportBaseRestaurant")
    @ApiOperation(value = "修改餐饮每日上报信息")
    public R updateReportBaseRestaurant(BaseRestaurantReport baseRestaurantReport){
        boolean save = iBaseRestaurantReportService.updateById(baseRestaurantReport);
        if(save) {
            return R.ok("修改成功");
        }
        return R.ok("修改失败");
    }

    @PostMapping("/deleteReportBaseRestaurant")
    @ApiOperation(value = "删除餐饮每日上报信息")
    public R deleteReportBaseRestaurant(Integer id){
        BaseRestaurantReport byId = iBaseRestaurantReportService.getById(id);
        byId.setIdDelete("1");
        boolean save = iBaseRestaurantReportService.updateById(byId);
        if(save) {
            return R.ok("删除成功");
        }
        return R.ok("删除失败");
    }

    @PostMapping("/saveBaseTravelReport")
    @ApiOperation(value = "新增旅行社每日上报信息")
    public R saveReportBaseTravel(BaseTravelLineReport baseTravelLineReport,String reportTimeVO, HttpServletRequest request) throws Exception {
        CustomUser cu = JwtUtil.getFromJwt(request);
        baseTravelLineReport.setUserId(cu.getUserId());
        baseTravelLineReport.setCreateTime(new Date());
        if(reportTimeVO!=null){
            baseTravelLineReport.setReportTime(TimeDateUtil.strToDate(reportTimeVO+" 12:00:00"));
        }else {
            baseTravelLineReport.setReportTime(new Date());
        }
        if (baseTravelLineReport.getIdDelete() == null){
            baseTravelLineReport.setIdDelete("0");
        }
        try {
        ReportBaseTravel reportBaseTravel = iReportBaseTravelService.getById(baseTravelLineReport.getSubTravelId());
        baseTravelLineReport.setSubTravelName(reportBaseTravel.getTravelName());
    }catch (Exception e){
        return R.ok("请完善基础信息");
    }
        boolean save = iBaseTravelLineReportService.save(baseTravelLineReport);
        if(save) {
            return R.ok("新增成功");
        }
        return R.ok("新增失败");
    }

    @PostMapping("/updateReportBaseTravel")
    @ApiOperation(value = "修改旅行社每日上报信息")
    public R updateReportBaseTravel(BaseTravelLineReport baseTravelLineReport){
        boolean save = iBaseTravelLineReportService.updateById(baseTravelLineReport);
        if(save) {
            return R.ok("修改成功");
        }
        return R.ok("修改失败");
    }

    @PostMapping("/deleteReportBaseTravel")
    @ApiOperation(value = "删除旅行社每日上报信息")
    public R deleteReportBaseTravel(Integer id){
        BaseTravelLineReport byId = iBaseTravelLineReportService.getById(id);
        byId.setIdDelete("1");
        boolean save = iBaseTravelLineReportService.updateById(byId);
        if(save) {
            return R.ok("删除成功");
        }
        return R.ok("删除失败");
    }


}
