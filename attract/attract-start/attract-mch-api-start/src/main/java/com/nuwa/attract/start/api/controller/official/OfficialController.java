package com.nuwa.attract.start.api.controller.official;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import javax.annotation.Resource;
import javax.validation.Valid;

import cn.hutool.core.lang.Assert;
import cn.hutool.core.util.StrUtil;
import com.alibaba.cola.dto.SingleResponse;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.nuwa.attract.start.api.controller.merchant.param.TeamAuditParam;
import com.nuwa.client.attract.dto.clientobject.travel.qry.MchTravelTeamPageQry;
import com.nuwa.client.attract.dto.clientobject.travel.qry.OfficialTravelTeamPageQry;
import com.nuwa.framework.base.UserAware;
import com.nuwa.infrastructure.attract.database.invoice.entity.Invoice;
import com.nuwa.infrastructure.attract.database.invoice.mapper.InvoiceMapper;
import com.nuwa.infrastructure.attract.database.invoice.service.InvoiceService;
import com.nuwa.infrastructure.attract.database.teamuserref.entity.TeamUserRef;
import com.nuwa.infrastructure.attract.database.teamuserref.mapper.TeamUserRefMapper;
import com.nuwa.infrastructure.attract.database.teamuserref.service.TeamUserRefService;
import com.nuwa.infrastructure.attract.database.travel.entity.TravelTeam;
import com.nuwa.infrastructure.attract.database.travel.mapper.TeamCustomerRefMapper;
import com.nuwa.infrastructure.attract.database.travel.param.MchTravelTeamPageParam;
import com.nuwa.infrastructure.attract.database.travel.param.OfficialTravelTeamPageParam;
import com.nuwa.infrastructure.attract.database.travel.service.CustomerService;
import com.nuwa.infrastructure.attract.database.travel.service.TeamCustomerRefService;
import com.nuwa.infrastructure.attract.database.travel.service.TeamUserCustomerRefService;
import com.nuwa.infrastructure.attract.database.travel.service.TravelTeamService;
import com.nuwa.infrastructure.attract.database.user.entity.AttractUser;
import com.nuwa.infrastructure.attract.database.user.service.AttractUserService;
import com.nuwa.infrastructure.enums.TeamStatusEnum;
import com.nuwa.infrastructure.vo.MchTravelTeamPageVO;
import com.nuwa.infrastructure.vo.OfficialTravelTeamPageVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * 酒店景区商家管理
 *
 * @author nanHuang @南皇
 * @version com.nuwa.attract.start.api.controller.merchant:MerchantController.java,v1.0.0 2022-09-16 16:14:24
 * nanHuang Exp $
 */
@Api(tags = {"文旅局端"})
@RestController
@RequestMapping("/official")
public class OfficialController {
    @Resource
    private AttractUserService         attractUserService;
    @Resource
    private TravelTeamService          travelTeamService;
    @Autowired
    private TeamUserRefService teamUserRefService;
    @Autowired
    private TeamUserRefMapper teamUserRefMapper;
    @Autowired
    private InvoiceMapper invoiceMapper;
    @Autowired
    private InvoiceService invoiceService;


    @ApiOperation(value = "团队审核列表")
    @RequestMapping(value = "/page", method = RequestMethod.GET)
    public SingleResponse<IPage<OfficialTravelTeamPageVO>> page(@Valid OfficialTravelTeamPageQry qry, UserAware userAware)
        throws Exception {
        OfficialTravelTeamPageParam param = new OfficialTravelTeamPageParam(qry);
        if (StrUtil.isNotBlank(qry.getMchName())) {
            List<Long> attractUserIdList = attractUserService.lambdaQuery().like(AttractUser::getMchName,
                qry.getMchName()).list().stream().map(AttractUser::getUserId).collect(Collectors.toList());
            param.getQry().setUserIds(attractUserIdList);
        }

        IPage<OfficialTravelTeamPageVO> page = travelTeamService.paginateAndConvert(param, OfficialTravelTeamPageVO::toVO);
        for (OfficialTravelTeamPageVO record : page.getRecords()) {
            AttractUser attractUser = attractUserService.getById(record.getUserId());
            record.setMchName(attractUser.getMchName());
        }

        return SingleResponse.of(page);
    }

    @ApiOperation(value = "审核通过")
    @RequestMapping(value = "/auditPass", method = RequestMethod.POST)
    @ResponseBody
    public SingleResponse<?> poiAuditPass(@RequestBody @Validated TeamAuditParam param, UserAware userAware) {
        TravelTeam travelTeam = travelTeamService.getById(param.getTeamId());
        Assert.notNull(travelTeam, "该团队不存在");
        travelTeam.setTeamStatus(TeamStatusEnum.OFFICIAL_SUCCESS.getCode());
        travelTeam.setOfficialReviewReason(param.getReason());
        travelTeam.setOfficialReviewTime(new Date());
        travelTeam.setLastUpdateTime(new Date());
        travelTeam.setLastUpdateById(userAware.getUserId());
        travelTeam.setLastUpdateByName(userAware.getMchName());
        travelTeam.updateById();

        QueryWrapper<TeamUserRef> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("team_id", travelTeam.getTeamId());
        List<TeamUserRef> teamUserRefList = teamUserRefMapper.selectList(queryWrapper);
        teamUserRefList.stream().forEach(item -> {
            item.setStatus(item.getType() != 3 ? TeamStatusEnum.OFFICIAL_SUCCESS.getCode() : TeamStatusEnum.INDEPENDENT_TRAVEL.getCode());
        });
        teamUserRefService.updateBatchById(teamUserRefList);
        return SingleResponse.buildSuccess();
    }

    @ApiOperation(value = "审核拒绝")
    @RequestMapping(value = "/auditRefused", method = RequestMethod.POST)
    @ResponseBody
    public SingleResponse<?> auditRefused(@RequestBody @Validated TeamAuditParam param, UserAware userAware) {
        TravelTeam travelTeam = travelTeamService.getById(param.getTeamId());
        Assert.notNull(travelTeam, "该团队不存在");
        travelTeam.setTeamStatus(TeamStatusEnum.OFFICIAL_REJECT.getCode());
        travelTeam.setOfficialReviewReason(param.getReason());
        travelTeam.setOfficialReviewTime(new Date());
        travelTeam.setLastUpdateTime(new Date());
        travelTeam.setLastUpdateById(userAware.getUserId());
        travelTeam.setLastUpdateByName(userAware.getMchName());
        travelTeam.updateById();

        QueryWrapper<TeamUserRef> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("team_id", travelTeam.getTeamId());
        List<TeamUserRef> teamUserRefList = teamUserRefMapper.selectList(queryWrapper);
        teamUserRefList.stream().forEach(item -> {
            item.setStatus(item.getType() != 3 ? TeamStatusEnum.OFFICIAL_REJECT.getCode() : TeamStatusEnum.INDEPENDENT_TRAVEL.getCode());
        });

        return SingleResponse.buildSuccess();
    }
}
