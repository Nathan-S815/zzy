package com.nuwa.attract.start.api.controller.screen;


import com.alibaba.cola.dto.SingleResponse;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.nuwa.attract.start.api.controller.screen.param.ScreenVo;
import com.nuwa.infrastructure.attract.database.reward.entity.Reward;
import com.nuwa.infrastructure.attract.database.reward.mapper.RewardMapper;
import com.nuwa.infrastructure.attract.database.reward.service.RewardService;
import com.nuwa.infrastructure.attract.database.travel.entity.Customer;
import com.nuwa.infrastructure.attract.database.travel.mapper.CustomerMapper;
import com.nuwa.infrastructure.attract.database.travel.mapper.TravelTeamMapper;
import com.nuwa.infrastructure.attract.database.travel.service.CustomerService;
import com.nuwa.infrastructure.attract.database.user.entity.AttractUser;
import com.nuwa.infrastructure.attract.database.user.mapper.AttractUserMapper;
import com.nuwa.infrastructure.attract.database.user.service.AttractUserService;
import com.nuwa.infrastructure.enums.AccountTypeEnum;
import com.nuwa.infrastructure.vo.screen.MchDetailsVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.models.auth.In;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.stream.Collectors;

/**
 * @Author: WangXh
 * @DateTime: 2022/10/27
 * @Description: TODO
 */

@Api(tags = {"首页数据系统"})
@Slf4j
@RestController
@RequestMapping("/screen")
public class ScreenController {

    @Autowired
    private AttractUserMapper attractUserMapper;

    @Autowired
    private AttractUserService attractUserService;

    @Autowired
    private CustomerService customerService;

    @Autowired
    private CustomerMapper customerMapper;

    @Autowired
    private TravelTeamMapper travelTeamMapper;

    @Autowired
    private RewardService rewardService;

    @Autowired
    private ExecutorService executorService;

    @Autowired
    private RewardMapper rewardMapper;

    @ApiOperation(value = "首页数据")
    @RequestMapping(value = "/home", method = RequestMethod.GET)
    public SingleResponse<ScreenVo> apply(String date) {

        ScreenVo screenVo = new ScreenVo();
        // 1.查询商家入住统计
        CompletableFuture<Void> checkNum = CompletableFuture.runAsync(() -> {
            List<AttractUser> attractUserList = attractUserService.list();
            // 景区
            List<AttractUser> sceneryNum = attractUserList.stream().filter(o -> o.getAccountType().equals(AccountTypeEnum.SCENERY.getCode())).collect(Collectors.toList());
            // 酒店
            Integer hotelNum = attractUserList.stream().filter(o -> o.getAccountType().equals(AccountTypeEnum.HOTEL.getCode())).collect(Collectors.toList()).size();
            // 旅行社
            Integer travelNum = attractUserList.stream().filter(o -> o.getAccountType().equals(AccountTypeEnum.TRAVEL.getCode())).collect(Collectors.toList()).size();
            screenVo.setSceneryNum(sceneryNum.size());
            screenVo.setHotelNum(hotelNum);
            screenVo.setTravelNum(travelNum);
        }, executorService);
        // 2.本地游客
        CompletableFuture<Void> visitor = CompletableFuture.runAsync(() -> {
            List<Customer> customerList = customerService.lambdaQuery().list();
            List localVisitor = customerList.parallelStream().filter(o -> o.getIdcard().startsWith("330726")).collect(Collectors.toList());
            screenVo.setLocalVisitor(localVisitor.size());
            // 3.外地游客
            screenVo.setOtherPlaceVisitor(customerList.size() - localVisitor.size());
        }, executorService);
        // 4.查询游客接待排行
        CompletableFuture<Void> agencyNum = CompletableFuture.runAsync(() -> {
            // 旅行社
            List<Map<String, Object>> travelAgencyNum = travelTeamMapper.qryTravelAgencyNum();
            screenVo.setTravelAgencyNum(travelAgencyNum);
            // 酒店
            List<Map<String, Object>> hotelAgencyNum = travelTeamMapper.qryAgencyNum(AccountTypeEnum.HOTEL.getCode());
            screenVo.setHotelAgencyNum(hotelAgencyNum);
            // 景区
            List<Map<String, Object>> sceneryAgencyNum = travelTeamMapper.qryAgencyNum(AccountTypeEnum.SCENERY.getCode());
            screenVo.setSceneryAgencyNum(sceneryAgencyNum);

            // 5.查询团队接待排行
            // 旅行社
            List<Map<String, Object>> travelTeamAgencyNum = travelTeamMapper.qrytravelTamAgencyNum();
            screenVo.setTravelTeamAgencyNum(travelTeamAgencyNum);
            // 酒店
            List<Map<String, Object>> hotelTeamAgencyNum = travelTeamMapper.qryTeamAgencyNum(AccountTypeEnum.HOTEL.getCode());
            screenVo.setHotelTeamAgencyNum(hotelTeamAgencyNum);
            // 景区
            List<Map<String, Object>> sceneryTeamAgencyNum = travelTeamMapper.qryTeamAgencyNum(AccountTypeEnum.SCENERY.getCode());
            screenVo.setSceneryTeamAgencyNum(sceneryTeamAgencyNum);

        }, executorService);
        // 5.月接待数据曲线图
        CompletableFuture<Void> monthVisitor = CompletableFuture.runAsync(() -> {
            List<Map<String, Object>> everyMonthVisitorList = travelTeamMapper.queryMonthVisitorNum();
            screenVo.setEveryMonthVisitorList(everyMonthVisitorList);

            List<Map<String, Object>> lastYearMonthVisitorList = travelTeamMapper.querylastMonthVisitorList();
            screenVo.setLastYearMonthVisitorList(lastYearMonthVisitorList);
        }, executorService);
        // 6.地区游客排行
        CompletableFuture<Void> placeVisitorList = CompletableFuture.runAsync(() -> {
            // 国内游客排行
            List<Map<String, Object>> otherPlaceVisitorList = travelTeamMapper.qryotherPlaceVisitorList("33");
            screenVo.setOtherPlaceVisitorList(otherPlaceVisitorList);
            // 省内游客排行
            List<Map<String, Object>> localVisitorList = travelTeamMapper.qrylocalVisitorList("33");
            screenVo.setLocalVisitorList(localVisitorList);
        }, executorService);
        // 7.奖励申报
        CompletableFuture<Void> reward = CompletableFuture.runAsync(() -> {
            /*数据暂时写死, 后期做调整
            rewardMapper.selectReawdData(date);
            * */
            // 招徕奖励申报件数
            List<Reward> rewardList = rewardService.list();
            screenVo.setRewards(72);
            // 申报通过件数
            List<Reward> rewards = rewardList.stream().filter(item -> item.getReviewStatus().equals("1")).collect(Collectors.toList());
            screenVo.setRewardPass(68);
            // 奖励总金额
            Double sumBonus = rewards.stream().mapToDouble(Reward::getTotalReward).sum();
            screenVo.setBonus(8984.24);
        }, executorService);

        CompletableFuture.allOf(checkNum, visitor, agencyNum, monthVisitor, placeVisitorList, reward).join();
        return SingleResponse.of(screenVo);
    }


    @ApiOperation(value = "商户详情数据")
    @RequestMapping(value = "/mchDetail/{accountType}/list", method = RequestMethod.GET)
    @ResponseBody
    public SingleResponse<IPage<MchDetailsVO>> getMchDetailList(@RequestParam(defaultValue = "10") Long pageSize,
                                                                @RequestParam(defaultValue = "1") Long pageNum, String mchName,
                                                                @PathVariable("accountType") @ApiParam(" 1-景区 2-酒店 3-旅行社")  String accountType) {
        IPage<MchDetailsVO> page = attractUserService.getMchDetailList(pageSize, pageNum, mchName,accountType);
        return SingleResponse.of(page);
    }
}
