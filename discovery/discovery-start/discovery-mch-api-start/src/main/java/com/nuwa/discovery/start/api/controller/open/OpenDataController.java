package com.nuwa.discovery.start.api.controller.open;


import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.convert.Convert;
import cn.hutool.core.date.DateUtil;
import com.alibaba.cola.dto.SingleResponse;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.nuwa.discovery.start.api.controller.open.vo.BusinessDataVO;
import com.nuwa.discovery.start.api.controller.open.vo.CommodityDataVO;
import com.nuwa.discovery.start.api.controller.open.vo.MemberDataVO;
import com.nuwa.infrastructure.discovery.database.system.entity.SystemConfig;
import com.nuwa.infrastructure.discovery.database.system.mapper.SystemConfigMapper;
import com.nuwa.infrastructure.discovery.database.task.entity.ScenicTask;
import com.nuwa.infrastructure.discovery.database.task.mapper.ScenicTaskMapper;
import com.nuwa.infrastructure.discovery.database.user.entity.Member;
import com.nuwa.infrastructure.discovery.database.user.entity.MemberTaskApply;
import com.nuwa.infrastructure.discovery.database.user.mapper.MemberMapper;
import com.nuwa.infrastructure.discovery.database.user.mapper.MemberTaskApplyMapper;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.checkerframework.checker.units.qual.A;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@Slf4j
@Controller
@RequestMapping("/open/data")
@Api(tags = {"旅游通开放数据"})
public class OpenDataController {

    @Autowired
    private SystemConfigMapper systemConfigMapper;

    @Autowired
    private ScenicTaskMapper scenicTaskMapper;

    @Autowired
    private MemberMapper memberMapper;

    @Autowired
    private MemberTaskApplyMapper memberTaskApplyMapper;

    @ApiOperation(value = "商家数据")
    @RequestMapping(value = "/business/data/get", method = RequestMethod.GET)
    @ResponseBody
    public SingleResponse<?> getBusinessData(){
        BusinessDataVO businessDataVO = new BusinessDataVO();

        businessDataVO.setBusinessCount(Convert.toInt(getSystemConfig("conf_key","business_count").getConfValue()));
        businessDataVO.setBusinessIncrCount(Convert.toInt(getSystemConfig("conf_key","business_incr_count").getConfValue()));

        Integer taskCount = scenicTaskMapper.selectCount(new QueryWrapper<>());
        businessDataVO.setTaskCount(taskCount);
        if(taskCount == 0){
            businessDataVO.setTaskPercentageComplete(0D);
            return SingleResponse.of(businessDataVO);
        }
        Date beginDate = DateUtil.beginOfMonth(DateUtil.lastMonth());
        Date endDate = DateUtil.endOfMonth(DateUtil.lastMonth());
        QueryWrapper<ScenicTask> queryWrapper = new QueryWrapper<>();
        queryWrapper.ge("end_date", beginDate).le("end_date", endDate);
        List<ScenicTask> scenicTasks = scenicTaskMapper.selectList(queryWrapper);

        if (CollUtil.isEmpty(scenicTasks)) {
            businessDataVO.setTaskPercentageComplete(0D);
            return SingleResponse.of(businessDataVO);
        }
        Long limitApplyMax;
        Long applyTotal;
        Integer taskPercentageCompleteCount = 0;
        for (ScenicTask scenicTask : scenicTasks) {
            applyTotal = scenicTask.getApplyTotal();
            limitApplyMax = scenicTask.getLimitApplyMax();
            if (applyTotal/limitApplyMax >= 20) {
                taskPercentageCompleteCount += 1;
            }
        }
        BigDecimal taskPercentageCompleteCountBigDecimal = new BigDecimal(taskPercentageCompleteCount);
        BigDecimal taskCountBigDecimal = new BigDecimal(scenicTasks.size());
        BigDecimal TaskPercentageComplete = taskPercentageCompleteCountBigDecimal.divide(taskCountBigDecimal, 2, BigDecimal.ROUND_HALF_UP);
        businessDataVO.setTaskPercentageComplete(Convert.toDouble(TaskPercentageComplete));
        return SingleResponse.of(businessDataVO);
    }

    @ApiOperation(value = "达人数据")
    @RequestMapping(value = "/member/data/get", method = RequestMethod.GET)
    @ResponseBody
    public SingleResponse<?> getMemberData(){
        MemberDataVO memberDataVO = new MemberDataVO();
        Integer memberCount = memberMapper.selectCount(new QueryWrapper<>());
        memberDataVO.setMemberCount(memberCount);

        QueryWrapper<Member> queryWrapper = new QueryWrapper<>();
        Date beginDate = DateUtil.beginOfMonth(new Date());
        Date endDate = DateUtil.endOfMonth(new Date());
        queryWrapper.ge("create_time", beginDate)
                .le("create_time", endDate);
        Integer memberIncrCount = memberMapper.selectCount(queryWrapper);
        memberDataVO.setMemberIncrCount(memberIncrCount);

        Long fansCount = memberCount * 3007L + 19000000L;
        memberDataVO.setFansCount(fansCount);

        Integer taskApplyCount = memberTaskApplyMapper.selectCount(new QueryWrapper<>());
        memberDataVO.setTaskApplyCount(taskApplyCount);
        return SingleResponse.of(memberDataVO);
    }



    @ApiOperation(value = "带货数据")
    @RequestMapping(value = "/commodity/data/get", method = RequestMethod.GET)
    @ResponseBody
    public SingleResponse<?> getCommodityData(){
        CommodityDataVO commodityDataVO = new CommodityDataVO();
        QueryWrapper<MemberTaskApply> queryWrapper = new QueryWrapper<>();
        queryWrapper.isNotNull("video");
        Integer videoCount = memberTaskApplyMapper.selectCount(queryWrapper);
        commodityDataVO.setVideoCount(videoCount + 6388);

        QueryWrapper<ScenicTask> queryWrapper1 = new QueryWrapper<>();
        queryWrapper1.gt("apply_total", 0);
        Integer taskApplyCount = scenicTaskMapper.selectCount(queryWrapper1);
        commodityDataVO.setTaskApplyCount(taskApplyCount);
        return SingleResponse.of(commodityDataVO);
    }
    private SystemConfig getSystemConfig(String column, String val){
        QueryWrapper<SystemConfig> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq(column,val);
        SystemConfig systemConfig = systemConfigMapper.selectOne(queryWrapper);
        return systemConfig;
    }


}
