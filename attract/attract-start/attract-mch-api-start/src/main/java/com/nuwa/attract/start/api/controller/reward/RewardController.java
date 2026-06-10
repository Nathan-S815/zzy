package com.nuwa.attract.start.api.controller.reward;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.URLEncoder;
import java.util.*;
import java.util.stream.Collectors;

import javax.annotation.Resource;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import cn.hutool.core.lang.Assert;
import cn.hutool.core.util.StrUtil;
import com.alibaba.cola.dto.Query;
import com.alibaba.cola.dto.SingleResponse;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.api.R;
import com.google.api.client.util.Lists;
import com.nuwa.app.attract.command.MaterialUploadImageCmdExe;
import com.nuwa.attract.start.api.controller.reward.entity.NightRewardConfig;
import com.nuwa.attract.start.api.controller.reward.entity.NightRewardModel;
import com.nuwa.attract.start.api.controller.reward.entity.OneDayRewardConfig;
import com.nuwa.attract.start.api.controller.reward.param.CalculateRewardParam;
import com.nuwa.attract.start.api.controller.reward.vo.CalculateRewardVO;
import com.nuwa.client.attract.co.MaterialUploadCO;
import com.nuwa.client.attract.dto.clientobject.MaterialUploadCmd;
import com.nuwa.client.attract.dto.clientobject.reward.qry.RewardPageQry;
import com.nuwa.client.attract.dto.clientobject.travel.qry.TravelTeamPageQry;
import com.nuwa.client.attract.dto.clientobject.user.qry.AttractUserPageQry;
import com.nuwa.framework.base.UserAware;
import com.nuwa.infrastructure.attract.database.common.entity.Material;
import com.nuwa.infrastructure.attract.database.common.service.MaterialService;
import com.nuwa.infrastructure.attract.database.reward.entity.Reward;
import com.nuwa.infrastructure.attract.database.reward.param.RewardPageParam;
import com.nuwa.infrastructure.attract.database.reward.service.RewardService;
import com.nuwa.infrastructure.attract.database.teamuserref.entity.TeamUserRef;
import com.nuwa.infrastructure.attract.database.teamuserref.mapper.TeamUserRefMapper;
import com.nuwa.infrastructure.attract.database.teamuserref.service.TeamUserRefService;
import com.nuwa.infrastructure.attract.database.travel.entity.TeamUserCustomerRef;
import com.nuwa.infrastructure.attract.database.travel.entity.TravelTeam;
import com.nuwa.infrastructure.attract.database.travel.mapper.TravelTeamMapper;
import com.nuwa.infrastructure.attract.database.travel.param.TravelTeamPageParam;
import com.nuwa.infrastructure.attract.database.travel.service.TeamUserCustomerRefService;
import com.nuwa.infrastructure.attract.database.travel.service.TravelTeamService;
import com.nuwa.infrastructure.attract.database.user.entity.AttractUser;
import com.nuwa.infrastructure.attract.database.user.param.AttractUserPageParam;
import com.nuwa.infrastructure.attract.database.user.service.AttractUserService;
import com.nuwa.infrastructure.enums.AccountTypeEnum;
import com.nuwa.infrastructure.enums.MaterialFileTypeEnum;
import com.nuwa.infrastructure.enums.MaterialTargetEnum;
import com.nuwa.infrastructure.enums.TeamStatusEnum;
import com.nuwa.infrastructure.vo.AttractUserPageVO;
import com.nuwa.infrastructure.vo.RewardPageVO;
import com.nuwa.infrastructure.vo.TravelTeamPageVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import jodd.io.FileUtil;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.util.ClassUtils;
import org.springframework.util.CollectionUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

/**
 * @author nanHuang @南皇
 * @version com.nuwa.attract.start.api.controller.reward:RewardController.java,v1.0.0 2022-09-19 13:15:56 nanHuang Exp $
 */
@Api(tags = {"奖励接口"})
@RestController
@RequestMapping("/reward")
@Slf4j
@CrossOrigin
public class RewardController {
    @Resource
    private AttractUserService         attractUserService;
    @Resource
    private TravelTeamService          travelTeamService;
    @Resource
    private TeamUserCustomerRefService teamUserCustomerRefService;
    @Resource
    private RewardService              rewardService;
    @Value("${upload.native.path}")
    private String                     uploadPath;
    @Resource
    private MaterialUploadImageCmdExe materialUploadImageCmdExe;
    @Autowired
    private MaterialService materialService;
    @Autowired
    private TravelTeamMapper travelTeamMapper;
    @Autowired
    private TeamUserRefService teamUserRefService;
    @Autowired
    private TeamUserRefMapper teamUserRefMapper;

    @ApiOperation(value = "奖励列表")
    @RequestMapping(value = "/rewardPage", method = RequestMethod.GET)
    public SingleResponse<IPage<RewardPageVO>> rewardPage(@Valid RewardPageQry qry, UserAware userAware) {
        RewardPageParam param = new RewardPageParam(qry);
        if (StrUtil.isNotBlank(qry.getMchName())) {
            List<Long> userIds = attractUserService.lambdaQuery().like(AttractUser::getMchName, qry.getMchName()).list()
                .stream().map(AttractUser::getUserId).collect(Collectors.toList());
            param.getQry().setUserIds(userIds);
        }

        IPage<RewardPageVO> page = rewardService.paginateAndConvert(param, RewardPageVO::toVO);
        for(RewardPageVO record : page.getRecords()) {
            AttractUser user = attractUserService.getById(record.getUserId());
            if (user != null) {
                record.setMchName(user.getMchName());
            }
        }
        return SingleResponse.of(page);
    }

    @ApiOperation(value = "奖励详情")
    @RequestMapping(value = "/rewardInfo/{rewardId}", method = RequestMethod.GET)
    public SingleResponse<IPage<TravelTeamPageVO>> rewardPage(@Valid TravelTeamPageQry qry,
                                                              @PathVariable(value = "rewardId") Long rewardId,
                                                              UserAware userAware) {
        Reward reward = rewardService.getById(rewardId);
        Assert.notNull(reward, "奖励不存在");

        TravelTeamPageParam param = new TravelTeamPageParam(qry);
        param.getQry().setTeamIds(StrUtil.split(reward.getTeamId(), ','));
        IPage<TravelTeamPageVO> page = travelTeamService.paginateAndConvert(param, TravelTeamPageVO::toVO);
        return SingleResponse.of(page);
    }

    @ApiOperation(value = "奖励生成列表")
    @RequestMapping(value = "/page", method = RequestMethod.GET)
    public SingleResponse<IPage<AttractUserPageVO>> page(@Valid AttractUserPageQry qry, UserAware userAware)
        throws Exception {
        AttractUserPageParam param = new AttractUserPageParam(qry);
        param.getQry().setAccountType(3);
        param.getQry().setReviewStatus(1);
        IPage<AttractUserPageVO> page = attractUserService.paginateAndConvert(param, AttractUserPageVO::toVO);
        for (AttractUserPageVO record : page.getRecords()) {
            AttractUser user = attractUserService.getById(record.getUserId());
            record.setMchName(user.getMchName());
        }
        return SingleResponse.of(page);
    }

    @ApiOperation(value = "奖励生成详情")
    @RequestMapping(value = "/info", method = RequestMethod.GET)
    public SingleResponse<Map<String, Object>> info(@Valid TravelTeamPageQry qry, UserAware userAware)
        throws Exception {
        Map<String,Object> map =new HashMap<>();
        Assert.notNull(qry.getUserId(), "旅行社id不能为空");
        TravelTeamPageParam param = new TravelTeamPageParam(qry);
        param.getQry().setTeamStatus(TeamStatusEnum.OFFICIAL_SUCCESS.getCode());
        IPage<TravelTeamPageVO> page = travelTeamService.paginateAndConvert(param, TravelTeamPageVO::toVO);
        QueryWrapper<TravelTeam> teamQueryWrapper =new QueryWrapper<>();
        teamQueryWrapper.eq("user_id",qry.getUserId());
        teamQueryWrapper.eq("team_status", TeamStatusEnum.OFFICIAL_SUCCESS.getCode());
        List<TravelTeam> travelTeams = travelTeamMapper.selectList(teamQueryWrapper);
        if (!CollectionUtils.isEmpty(travelTeams)) {
            CalculateRewardParam qry1 = new CalculateRewardParam();
            List<Long> bonusIdListId = travelTeams.stream().mapToLong(t -> t.getTeamId()).boxed().collect(Collectors.toList());
            qry1.setTeamIds(bonusIdListId);
            map.put("oneDayReward", calculate(qry1, userAware).getData().getOneDayReward());
            map.put("nightReward", calculate(qry1, userAware).getData().getNightReward());
        } else {
            map.put("oneDayReward", "0");
            map.put("nightReward", "0");
        }
        map.put("page",page);
        return SingleResponse.of(map);
    }

    @ApiOperation(value = "确认发放奖励")
    @RequestMapping(value = "/distribution/{rewardId}", method = RequestMethod.GET)
    public SingleResponse<?> distribution(@PathVariable("rewardId") Long rewardId, UserAware userAware) {
        Reward reward = rewardService.getById(rewardId);
        Assert.notNull(reward, "奖励id不正确");
        reward.setReviewStatus(1);
        reward.updateById();
        return SingleResponse.buildSuccess();
    }

    @ApiOperation(value = "生成奖励")
    @RequestMapping(value = "/generateReward", method = RequestMethod.POST)
    public SingleResponse<?> generateReward(@RequestBody @Validated CalculateRewardParam param, UserAware userAware) {
        SingleResponse<CalculateRewardVO> calculate = this.calculate(param, userAware);
        //获取团队
        List<TravelTeam> travelTeams = travelTeamService.lambdaQuery()
            .in(TravelTeam::getTeamId, param.getTeamIds())
            .eq(TravelTeam::getReward, 0)
            .list();
        AttractUser attractUser = attractUserService.getById(travelTeams.get(0).getUserId());

        Reward reward = new Reward();
        reward.setNightReward(calculate.getData().getNightReward());
        reward.setOneDayReward(calculate.getData().getOneDayReward());
        reward.setUserId(attractUser.getUserId());
        reward.setCreateById(userAware.getUserId());
        reward.setCreateTime(new Date());
        reward.setCreateByName(userAware.getMchName());
        reward.setTotalReward(reward.getNightReward() + reward.getOneDayReward());
        reward.setTravelType("330726".equals(attractUser.getAreaId()) ? 1 : 2);
        reward.setTeamId(StringUtils.join(param.getTeamIds().toArray(), ","));
        reward.insert();
        return SingleResponse.buildSuccess();
    }

    @ApiOperation(value = "奖励计算")
    @RequestMapping(value = "/calculate", method = RequestMethod.POST)
    public SingleResponse<CalculateRewardVO> calculate(@RequestBody @Validated CalculateRewardParam param,
                                                       UserAware userAware) {
        //init
        CalculateRewardVO result = new CalculateRewardVO();
        result.setNightReward(0);
        result.setOneDayReward(0);
        //获取团队
        List<TravelTeam> travelTeams = travelTeamService.lambdaQuery()
            .in(TravelTeam::getTeamId, param.getTeamIds())
            .eq(TravelTeam::getReward, 0)
            .list();

        AttractUser attractUser = attractUserService.getById(travelTeams.get(0).getUserId());

        Integer oneDayPerson = 0;
        Integer nightRewardPerson =0;
        List<NightRewardModel> nightRewardModels = Lists.newArrayList();
        for (TravelTeam travelTeam : travelTeams) {
            // 查询行程信息
            List<TeamUserRef> personList = teamUserRefService.lambdaQuery()
                    .eq(TeamUserRef::getTeamId, travelTeam.getTeamId())
                    .eq(TeamUserRef::getDeleteFlag, 0)
                    .eq(TeamUserRef::getStatus, TeamStatusEnum.OFFICIAL_SUCCESS.getCode()).list();
            // 存在酒店行程则判断为非一日游,统计一日游人数,
            boolean nameExist = personList.stream().anyMatch(item -> item.getType().equals(AccountTypeEnum.HOTEL.getCode()));
            if (travelTeam.getBeginDate().compareTo(travelTeam.getEndDate()) == 0 && !nameExist) {
                // 多个景点统计最小人数
                oneDayPerson += personList.stream().filter(item -> item.getType().equals(AccountTypeEnum.SCENERY.getCode()))
                        .min(Comparator.comparing(TeamUserRef::getAttendance)).get().getAttendance();
            } else {
                List<TeamUserRef> nightList = personList.stream().filter(item -> item.getType().equals(AccountTypeEnum.HOTEL.getCode())).collect(Collectors.toList());
                nightRewardPerson += nightList.stream().mapToInt(item -> item.getAttendance()).sum();
                NightRewardModel nightRewardModel = new NightRewardModel();
                nightRewardModel.setNight(nightList == null ? 0 : nightList.size());
                nightRewardModel.setPerson(nightRewardPerson);
                nightRewardModels.add(nightRewardModel);
            }
        }
        result.setOneDayReward(OneDayRewardConfig.oneDayCalculateRole(oneDayPerson));
        result.setNightReward(
            NightRewardConfig.nightRewardCalculateRole(nightRewardModels, "330726".equals(attractUser.getAreaId())));
        return SingleResponse.of(result);
    }

    @ApiOperation(value = "奖励政策上传")
    @PostMapping(value = "/upload")
    public SingleResponse<?> uploadImage(@Valid @ApiParam(value = "file", type = "MultipartFile" )  MultipartFile file,
                                          Long typeId) throws Exception {
        MaterialUploadCmd cmd = new MaterialUploadCmd();
        MaterialUploadCO materialUploadCo = new MaterialUploadCO();
        materialUploadCo.setFile(file);
        materialUploadCo.setTypeId(typeId);
        materialUploadCo.setTargeType(MaterialTargetEnum.REWARD.getCode());
        cmd.setMaterialUploadCO(materialUploadCo);
        return materialUploadImageCmdExe.execute(cmd);
    }
    /**
     * 文件下载
     *
     * @param name
     * @param response
     */
    @RequestMapping(value = "/download", method = RequestMethod.GET)
    @ApiOperation(value = "奖励政策下载")
    public void download(String name, HttpServletResponse response) {
        try {
            //输入流，通过输入流读取文件内容
            FileInputStream fileInputStream = new FileInputStream(new File("/home/resource/attract/" + name));

            //输出流，通过输出流将文件写回浏览器
            ServletOutputStream outputStream = response.getOutputStream();

            response.setContentType("application/x-download");
            response.setHeader("Content-Disposition",
                "attachment;filename=" + name + ";filename*=utf-8''" + URLEncoder.encode(name, "UTF-8"));

            int len = 0;
            byte[] bytes = new byte[1024];
            while ((len = fileInputStream.read(bytes)) != -1) {
                outputStream.write(bytes, 0, len);
                outputStream.flush();
            }

            //关闭资源
            outputStream.close();
            fileInputStream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 获取奖励文件
     *
     * @param name
     * @param response
     */
    @RequestMapping(value = "/getRewardFile", method = RequestMethod.GET)
    @ApiOperation(value = "获取奖励文件名")
    public SingleResponse getRewardFile() {
        return rewardService.selectFileList();
    }



    @ApiOperation(value = "删除奖励文件")
    @RequestMapping(value = "/del/{id}", method = RequestMethod.GET)
    public SingleResponse<?> del(@PathVariable("id") Long id) {
        Material material = materialService.getById(id);
        Assert.notNull(material, "该团队不存在");
        material.setStatus(3);
        materialService.updateById(material);
        return SingleResponse.buildSuccess();
    }
}
