package com.nuwa.discovery.start.api.controller.open;


import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.convert.Convert;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.NumberUtil;
import com.alibaba.cola.dto.SingleResponse;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.nuwa.discovery.start.api.controller.open.vo.MemberCountVO;
import com.nuwa.discovery.start.api.controller.open.vo.MemberRatioGenderVO;
import com.nuwa.discovery.start.api.controller.open.vo.MemberRatioTagVO;
import com.nuwa.infrastructure.discovery.database.member.entity.MemberTag;
import com.nuwa.infrastructure.discovery.database.member.mapper.MemberTagMapper;
import com.nuwa.infrastructure.discovery.database.member.vo.MemBerFansCountVO;
import com.nuwa.infrastructure.discovery.database.member.vo.MemberDetailVO;
import com.nuwa.infrastructure.discovery.database.member.vo.MemberGmvDetailVO;
import com.nuwa.infrastructure.discovery.database.member.vo.MemberTagCountVO;
import com.nuwa.infrastructure.discovery.database.task.entity.ScenicTask;
import com.nuwa.infrastructure.discovery.database.task.mapper.ScenicTaskMapper;
import com.nuwa.infrastructure.discovery.database.task.service.ScenicTaskService;
import com.nuwa.infrastructure.discovery.database.user.entity.Member;
import com.nuwa.infrastructure.discovery.database.user.mapper.MemberMapper;
import com.nuwa.infrastructure.discovery.database.user.service.MemberAccountBindService;
import com.nuwa.infrastructure.discovery.database.user.service.MemberService;
import com.nuwa.infrastructure.discovery.database.user.vo.GreateVideoVO;
import com.nuwa.infrastructure.discovery.database.user.vo.MemberRankDataVO;
import com.nuwa.infrastructure.discovery.database.user.vo.MemberVideoVO;
import com.nuwa.infrastructure.discovery.database.user.vo.NewTaskVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Slf4j
@Controller
@RequestMapping("/data/summary")
@Api(tags = {"数据汇总接口"})
public class DataSummaryController {


    @Autowired
    private ScenicTaskMapper scenicTaskMapper;

    @Autowired
    private MemberMapper memberMapper;

    @Autowired
    private MemberTagMapper memberTagMapper;

    @Autowired
    private MemberAccountBindService memberAccountBindService;

    @Autowired
    private MemberService memberService;

    @Autowired
    private ScenicTaskService scenicTaskService;


    @ApiOperation(value = "商家累计发布任务数")
    @RequestMapping(value = "/task/count/{mchId}", method = RequestMethod.GET)
    @ResponseBody
    public SingleResponse<?> getTaskCount(@PathVariable("mchId") String mchId){
        QueryWrapper<ScenicTask> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("mch_id", mchId);
        Integer taskCount = scenicTaskMapper.selectCount(queryWrapper);
        return SingleResponse.of(taskCount);
    }

    @ApiOperation(value = "达人任务完成数量")
    @RequestMapping(value = "/task/complete/count/{mchId}", method = RequestMethod.GET)
    @ResponseBody
    public SingleResponse<?> getTaskCompleteCount(@PathVariable("mchId") String mchId){
        QueryWrapper<ScenicTask> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("mch_id", mchId)
                //报名率高于20%算作完成
                .ge("apply_proportion", 20)
                .le("end_date", DateUtil.today());
        Integer taskCompleteCount = scenicTaskMapper.selectCount(queryWrapper);
        return SingleResponse.of(taskCompleteCount);
    }


    @ApiOperation(value = "达人任务完成数量")
    @RequestMapping(value = "/member/count/{mchId}", method = RequestMethod.GET)
    @ResponseBody
    public SingleResponse<?> getMemberCount(@PathVariable("mchId") String mchId){
        MemberCountVO memberCountVO = new MemberCountVO();

        QueryWrapper<Member> memberCountQueryWrapper = new QueryWrapper<>();
        memberCountQueryWrapper.eq("mch_id", mchId);
        Integer memberCount = memberMapper.selectCount(memberCountQueryWrapper);
        memberCountVO.setMemberCount(memberCount);

        Date beginDate = DateUtil.beginOfMonth(new Date());
        Date endDate = DateUtil.endOfMonth(new Date());
        QueryWrapper<Member> memberIncrCountQueryWrapper = new QueryWrapper<>();
        memberIncrCountQueryWrapper.eq("mch_id", mchId)
                .ge("create_time", beginDate)
                .le("create_time", endDate);

        Integer memberIncrCount = memberMapper.selectCount(memberIncrCountQueryWrapper);
        memberCountVO.setMemberIncrCount(memberIncrCount);

        return SingleResponse.of(memberCountVO);
    }


    @ApiOperation(value = "达人任务完成数量")
    @RequestMapping(value = "/member/ranking/{mchId}", method = RequestMethod.GET)
    @ResponseBody
    public SingleResponse<?> getMemberRanking(@PathVariable("mchId") String mchId){
        List<MemberRankDataVO> memberRankDataVOList = memberMapper.getMemberRankDataVOByMchId(mchId);
        return SingleResponse.of(memberRankDataVOList);
    }

    @ApiOperation(value = "达人性别比例")
    @RequestMapping(value = "/member/ratio/gender/{mchId}", method = RequestMethod.GET)
    @ResponseBody
    public SingleResponse<?> getMemberRatioGender(@PathVariable("mchId") String mchId){
        MemberRatioGenderVO memberRatioGenderVO = new MemberRatioGenderVO();

        Integer maleCount = memberMapper.sexCount(mchId, 1);
        Integer femaleCount= memberMapper.sexCount(mchId, 2);
        if(maleCount == 0 && femaleCount == 0){
            memberRatioGenderVO.setFemale(50D);
            memberRatioGenderVO.setMale(50D);
            return SingleResponse.of(memberRatioGenderVO);
        }
        Integer total = maleCount + femaleCount;

        memberRatioGenderVO.setMale(Convert.toDouble(NumberUtil.div(maleCount, total, 4)) * 100);
        memberRatioGenderVO.setFemale(Convert.toDouble(NumberUtil.div(femaleCount, total, 4)) * 100);
        return SingleResponse.of(memberRatioGenderVO);
    }

    @ApiOperation(value = "达人标签比例")
    @RequestMapping(value = "/member/ratio/tag/{mchId}", method = RequestMethod.GET)
    @ResponseBody
    public SingleResponse<?> getMemberRatioTag(@PathVariable("mchId") String mchId){
        QueryWrapper<MemberTag> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("delete_flag",0);
        List<MemberTag> memberTags = memberTagMapper.selectList(queryWrapper);

        List<MemberRatioTagVO> memberRatioTagVOList = new ArrayList<>();

        List<MemberTagCountVO> memberTagCountVOList = memberTagMapper.getMemberTagCountVO(mchId);
        if(CollUtil.isEmpty(memberTagCountVOList)){
            return SingleResponse.of(memberRatioTagVOList);
        }
        Integer total = 0;
        for (MemberTagCountVO tagCountVO : memberTagCountVOList) {
            total +=  tagCountVO.getMemberCount();
        }
        Map<Long, Integer> idAndMemberCountMap = memberTagCountVOList.stream()
                .collect(Collectors.toMap(MemberTagCountVO::getId, MemberTagCountVO::getMemberCount));

        MemberRatioTagVO memberRatioTagVO;
        for (MemberTag memberTag : memberTags) {
            memberRatioTagVO = new MemberRatioTagVO();
            Integer count = idAndMemberCountMap.get(memberTag.getId());
            memberRatioTagVO.setRatio(Convert.toDouble(NumberUtil.div(count, total, 4)) * 100);
            memberRatioTagVO.setTagName(memberTag.getName());
            memberRatioTagVOList.add(memberRatioTagVO);
        }
        return SingleResponse.of(memberRatioTagVOList);
    }


    @ApiOperation(value = "达人GMV总和")
    @RequestMapping(value = "/member/total/gmv/{mchId}", method = RequestMethod.GET)
    @ResponseBody
    public SingleResponse<?> getMemberTotalGMV(@PathVariable("mchId") String mchId){
        Long totalGMV = memberMapper.getTotalGMV(mchId);
        if(totalGMV == null){
            return SingleResponse.of(0D);
        }
        Double result = Convert.toDouble(totalGMV)/100;
        return SingleResponse.of(result);
    }

    @ApiOperation(value = "达人粉丝总和")
    @RequestMapping(value = "/member/total/fans/{mchId}", method = RequestMethod.GET)
    @ResponseBody
    public SingleResponse<?> getMemberTotalFans(@PathVariable("mchId") String mchId){
        Long totalFansCount = memberMapper.getTotalFans(mchId);
        return SingleResponse.of(totalFansCount);
    }


    @ApiOperation(value = "达人发布视频总和")
    @RequestMapping(value = "/member/total/video/{mchId}", method = RequestMethod.GET)
    @ResponseBody
    public SingleResponse<?> getMemberTotalVideo(@PathVariable("mchId") String mchId){
        Long totalVideoCount = memberMapper.getTotalVideo(mchId);
        return SingleResponse.of(totalVideoCount);
    }

    @ApiOperation(value = "最新任务")
    @RequestMapping(value = "/newest/task/{mchId}", method = RequestMethod.GET)
    @ResponseBody
    public SingleResponse<IPage<NewTaskVO>> getNewestTask(@RequestParam(defaultValue = "10") Long pageSize,
                                                          @RequestParam(defaultValue = "1") Long pageNum,
                                                          @PathVariable("mchId")  String mchId) {
        IPage<NewTaskVO> page = scenicTaskService.getNewestTask(pageSize, pageNum, mchId);
        return SingleResponse.of(page);
    }

    @ApiOperation(value = "达人优秀作品")
    @RequestMapping(value = "/greatVideo/list/{mchId}", method = RequestMethod.GET)
    @ResponseBody
    public SingleResponse<List<GreateVideoVO>> getGreateVideoList(@PathVariable("mchId") String mchId) {
        List<GreateVideoVO> greateVideoVOS = memberMapper.selectGreateList(mchId);
        return SingleResponse.of(greateVideoVOS);
    }

    @ApiOperation(value = "累计注册达人列表/本月新增")
    @RequestMapping(value = "/memberDetail/list/{opType}/{mchId}", method = RequestMethod.GET)
    @ResponseBody
    public SingleResponse<IPage<MemberDetailVO>> getMemberDetailList(@RequestParam(defaultValue = "10") Long pageSize,
                                                                     @RequestParam(defaultValue = "1") Long pageNum,
                                                                     @PathVariable("opType") @ApiParam("1-所有数据 2-本月新增") String opType ,
                                                                     @PathVariable("mchId")  @ApiParam("商户id") String mchId,String userNick){
        IPage<MemberDetailVO> page = memberAccountBindService.getMemberDetailList(pageSize, pageNum,opType,mchId,userNick);
        return SingleResponse.of(page);
    }


    @ApiOperation(value = "达人粉丝榜")
    @RequestMapping(value = "/memberFansCount/list/{mchId}", method = RequestMethod.GET)
    @ResponseBody
    public SingleResponse<IPage<MemBerFansCountVO>> getMemberFansCountList(@RequestParam(defaultValue = "10") Long pageSize,
                                                                           @RequestParam(defaultValue = "1") Long pageNum,
                                                                           @PathVariable("mchId") String mchId) {
        IPage<MemBerFansCountVO> page = memberAccountBindService.getMemberFansCountList(pageSize, pageNum,mchId);
        return SingleResponse.of(page);
    }


    @ApiOperation(value = "达人Gmv详情")
    @RequestMapping(value = "/memberGmvDetail/list/{mchId}", method = RequestMethod.GET)
    @ResponseBody
    public SingleResponse<IPage<MemberGmvDetailVO>> getMemberGmvDetailList(@RequestParam(defaultValue = "10") Long pageSize,
                                                                           @RequestParam(defaultValue = "1") Long pageNum,
                                                                           @PathVariable("mchId") String mchId) {
        IPage<MemberGmvDetailVO> page = memberAccountBindService.getMemberGmvDetailList(pageSize, pageNum,mchId);
        return SingleResponse.of(page);
    }

    @ApiOperation(value = "达人视频列表")
    @RequestMapping(value = "/memberVideoDetail/list/{mchId}", method = RequestMethod.GET)
    @ResponseBody
    public SingleResponse<IPage<MemberVideoVO>> getMemberVideoDetailList(@RequestParam(defaultValue = "10") Long pageSize,
                                                                     @RequestParam(defaultValue = "1") Long pageNum,
                                                                     @PathVariable("mchId") String mchId) {
        IPage<MemberVideoVO> page = memberAccountBindService.getMemberVideoDetailList(pageSize, pageNum, mchId);
        return SingleResponse.of(page);
    }
}
