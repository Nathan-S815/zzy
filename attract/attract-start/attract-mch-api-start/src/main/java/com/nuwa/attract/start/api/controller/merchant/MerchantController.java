package com.nuwa.attract.start.api.controller.merchant;


import cn.hutool.core.lang.Assert;
import com.alibaba.cola.dto.SingleResponse;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.nuwa.attract.start.api.controller.merchant.param.TeamAuditParam;
import com.nuwa.client.attract.dto.clientobject.travel.qry.MchTravelTeamPageQry;
import com.nuwa.framework.base.UserAware;
import com.nuwa.infrastructure.attract.database.teamuserref.entity.TeamUserRef;
import com.nuwa.infrastructure.attract.database.teamuserref.mapper.TeamUserRefMapper;
import com.nuwa.infrastructure.attract.database.teamuserref.service.TeamUserRefService;
import com.nuwa.infrastructure.attract.database.travel.entity.TravelTeam;
import com.nuwa.infrastructure.attract.database.travel.mapper.TeamCustomerRefMapper;
import com.nuwa.infrastructure.attract.database.travel.service.CustomerService;
import com.nuwa.infrastructure.attract.database.travel.service.TeamCustomerRefService;
import com.nuwa.infrastructure.attract.database.travel.service.TeamUserCustomerRefService;
import com.nuwa.infrastructure.attract.database.travel.service.TravelTeamService;
import com.nuwa.infrastructure.attract.database.user.service.AttractUserService;
import com.nuwa.infrastructure.enums.TeamStatusEnum;
import com.nuwa.infrastructure.vo.MchTravelTeamPageVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * 酒店景区商家管理
 *
 * @author nanHuang @南皇
 * @version com.nuwa.attract.start.api.controller.merchant:MerchantController.java,v1.0.0 2022-09-16 16:14:24
 * nanHuang Exp $
 */
@Api(tags = {"景区酒店端"})
@RestController
@RequestMapping("/merchant")
public class MerchantController {
    @Resource
    private AttractUserService attractUserService;
    @Resource
    private TravelTeamService travelTeamService;
    @Resource
    private CustomerService customerService;
    @Resource
    private TeamUserCustomerRefService teamUserCustomerRefService;
    @Resource
    private TeamCustomerRefService teamCustomerRefService;
    @Resource
    private TeamCustomerRefMapper teamCustomerRefMapper;
    @Autowired
    private TeamUserRefMapper teamUserRefMapper;
    @Autowired
    private TeamUserRefService teamUserRefService;

    @ApiOperation(value = "团队审核列表")
    @RequestMapping(value = "/page", method = RequestMethod.GET)
    public SingleResponse<IPage<MchTravelTeamPageVO>> page(@Valid MchTravelTeamPageQry qry, UserAware userAware)
            throws Exception {

        String endDate = "";
        String startDate = "";
        if (qry.getApplyBeginTime() != null) {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            startDate = sdf.format(qry.getApplyBeginTime())+ " 00:00:00";
        }
        if (qry.getApplyEndTime() != null) {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            endDate = sdf.format(qry.getApplyEndTime())+ " 23:59:59";
        }
        System.out.println(startDate);
        Page<MchTravelTeamPageVO> page = new Page<>(qry.getPage(), qry.getLimit());
        IPage<MchTravelTeamPageVO> record = teamUserRefMapper.qryAuditList(page,userAware.getUserId(),qry.getUserId(),qry.getTeamId(),qry.getMchName(),startDate,endDate,qry.getStatus());

        return SingleResponse.of(record);
    }

    @ApiOperation(value = "审核通过")
    @RequestMapping(value = "/auditPass", method = RequestMethod.POST)
    @ResponseBody
    public SingleResponse<?> poiAuditPass(@RequestBody @Valid TeamAuditParam param, UserAware userAware) {
        TeamUserRef teamUserRef = teamUserRefMapper.selectById(param.getTeamUserRefId());
        Assert.notNull(teamUserRef, "该行程不存在");
        teamUserRef.setStatus(TeamStatusEnum.MCH_AUDIT_PASS.getCode());
        teamUserRef.setAttendance(param.getAttendance());
        teamUserRef.setHotelRooms(teamUserRef.getType() == 2 ? param.getHotelRooms() : null);
        teamUserRef.setMchReviewReason(param.getReason());
        teamUserRef.setMchReviewTime(new Date());
        teamUserRef.updateById();
        QueryWrapper<TeamUserRef> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("team_id", param.getTeamId());
        queryWrapper.eq("delete_flag", "0");
        queryWrapper.in("status", 1, 2);
        List<TeamUserRef> teamUserRefList = teamUserRefMapper.selectList(queryWrapper);
        if (CollectionUtils.isEmpty(teamUserRefList)) {
            TravelTeam travelTeam = new TravelTeam();
            travelTeam.setTeamId(param.getTeamId());
            travelTeam.setTeamStatus(TeamStatusEnum.UPLOAD_INVOICE.getCode());
            travelTeamService.updateById(travelTeam);
        }
        return SingleResponse.buildSuccess();
    }

    @ApiOperation(value = "审核拒绝")
    @RequestMapping(value = "/auditRefused", method = RequestMethod.POST)
    @ResponseBody
    public SingleResponse<?> auditRefused(@RequestBody @Valid TeamAuditParam param, UserAware userAware) {
        TeamUserRef teamUserRef = teamUserRefMapper.selectById(param.getTeamUserRefId());
        Assert.notNull(teamUserRef, "该团队不存在");
        teamUserRef.setStatus(TeamStatusEnum.MCH_AUDIT_REJECT.getCode());
        teamUserRef.setAttendance(param.getAttendance());
        teamUserRef.setMchReviewReason(param.getReason());
        teamUserRef.setHotelRooms(teamUserRef.getType() == 2 ? param.getHotelRooms() : null);
        teamUserRef.setMchReviewTime(new Date());
        teamUserRef.updateById();
        TravelTeam travelTeam = travelTeamService.getById(param.getTeamId());
        travelTeam.setTeamStatus(TeamStatusEnum.MCH_AUDIT_REJECT.getCode());
        travelTeamService.updateById(travelTeam);
        return SingleResponse.buildSuccess();
    }
}
