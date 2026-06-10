package com.nuwa.ticket.start.api.controller.scenicspot;

import com.alibaba.cola.dto.SingleResponse;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.nuwa.app.ticket.command.query.MchScenicspotPageJoinQry;
import com.nuwa.framework.base.UserAware;
import com.nuwa.infrastructure.ticket.database.scenicspot.entity.*;
import com.nuwa.infrastructure.ticket.database.scenicspot.mapper.ScenicspotJoinMapper;
import com.nuwa.infrastructure.ticket.database.scenicspot.mapper.query.PageScenicspotJoinQuery;
import com.nuwa.infrastructure.ticket.database.scenicspot.service.*;
import com.nuwa.infrastructure.ticket.database.scenicspot.vo.MerchantScenicspotPageVO;
import com.nuwa.infrastructure.ticket.enums.AuditStatusEnum;
import com.nuwa.ticket.start.api.controller.dto.ScenicspotBaseInfoDTO;
import com.nuwa.ticket.start.api.controller.dto.ScenicspotMaterialDTO;
import com.nuwa.ticket.start.api.controller.param.AuditPassScenicspotPoiParam;
import com.nuwa.ticket.start.api.controller.param.AuditRejectScenicspotPoiParam;
import com.nuwa.ticket.start.api.controller.param.SaveAndAuditScenicspotPoiParam;
import com.nuwa.ticket.start.api.controller.param.SaveScenicspotPoiParam;
import com.nuwa.ticket.start.api.controller.vo.ScenicspotDetailVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @author hy
 */
@Slf4j
@Controller
@RequestMapping("scenicspot")
@Api(tags = {"景点公共接口"})
public class CommonScenicspotController {

    @Autowired
    private ScenicspotBaseTypeService scenicspotBaseTypeService;

    @Autowired
    private ScenicspotBaseLabelService scenicspotBaseLabelService;

    @Autowired
    private ScenicspotTypeService scenicspotTypeService;

    @Autowired
    private ScenicspotLabelService scenicspotLabelService;

    @Autowired
    private ScenicspotMaterialService scenicspotMaterialService;

    @Autowired
    private ScenicspotService scenicspotService;

    @Autowired
    private ScenicspotJoinMapper scenicspotJoinMapper;

    @ApiOperation(value = "POI分页查询")
    @RequestMapping(value = "/page", method = RequestMethod.GET)
    @ResponseBody
    public SingleResponse<Page<MerchantScenicspotPageVO>> centerPage(MchScenicspotPageJoinQry query, UserAware userAware) {
        PageScenicspotJoinQuery queryPage = new PageScenicspotJoinQuery();
        BeanUtils.copyProperties(query, queryPage);
        queryPage.setVersionFlag(1);
        queryPage.setStatus(AuditStatusEnum.AUDIT_PASS.getCode());
        Page<MerchantScenicspotPageVO> pageData = scenicspotJoinMapper.paginateByQuery(queryPage);
        return SingleResponse.of(pageData);
    }

    @ApiOperation(value = "获取景点类型列表")
    @RequestMapping(value = "/type/list", method = RequestMethod.GET)
    @ResponseBody
    public SingleResponse<List<ScenicspotBaseType>> scenicspotBaseType() {
        List<ScenicspotBaseType> scenicspotBaseTypes = scenicspotBaseTypeService.lambdaQuery().orderByAsc(ScenicspotBaseType::getWeight).list();
        return SingleResponse.of(scenicspotBaseTypes);
    }

    @ApiOperation(value = "获取景点类型列表V2")
    @RequestMapping(value = "/type/list/{poiType}", method = RequestMethod.GET)
    @ResponseBody
    public SingleResponse<List<ScenicspotBaseType>> scenicspotBaseTypeV2(@PathVariable("poiType") String poiType) {
        List<ScenicspotBaseType> scenicspotBaseTypes = scenicspotBaseTypeService.lambdaQuery()
                .eq(ScenicspotBaseType::getPoiType, poiType)
                .orderByAsc(ScenicspotBaseType::getWeight).list();
        return SingleResponse.of(scenicspotBaseTypes);
    }

    @ApiOperation(value = "获取景点详情")
    @RequestMapping(value = "/getById", method = RequestMethod.GET)
    @ResponseBody
    public SingleResponse<ScenicspotDetailVO> getById(Long id) {
        Scenicspot scenicspot = scenicspotService.getById(id);
        ScenicspotBaseInfoDTO baseInfo = new ScenicspotBaseInfoDTO();
        BeanUtils.copyProperties(scenicspot, baseInfo);
        ScenicspotDetailVO detailVO = new ScenicspotDetailVO();
        detailVO.setBaseInfo(baseInfo);
        baseInfo.setTypeIds(getScenicspotBaseTypes(id));
        baseInfo.setLabelIds(getScenicspotBaseLabels(id));
        detailVO.setMaterials(getScenicMaterials(id));
        return SingleResponse.of(detailVO);
    }

    @ApiOperation(value = "获取景点详情(多个id)")
    @RequestMapping(value = "/getByIdList", method = RequestMethod.GET)
    @ResponseBody
    public SingleResponse<List<Scenicspot>> getByIdList(String ids) {
        List<Scenicspot> list = scenicspotService.lambdaQuery().in(Scenicspot::getId, ids.split(",")).list();
        return SingleResponse.of(list);
    }

    @ApiOperation(value = "获取景点标签列表")
    @RequestMapping(value = "/label/list", method = RequestMethod.GET)
    @ResponseBody
    public SingleResponse<List<ScenicspotBaseLabel>> scenicspotBaseLabel(UserAware userAware) {
        List<ScenicspotBaseLabel> scenicspotBaseTypes = scenicspotBaseLabelService.lambdaQuery()
                .in(ScenicspotBaseLabel::getCreateById, userAware.getMchId(), "-1")
                .orderByDesc(ScenicspotBaseLabel::getId).list();
        return SingleResponse.of(scenicspotBaseTypes);
    }

    @ApiOperation(value = "获取景点商户标签列表")
    @RequestMapping(value = "/label/{id}/list", method = RequestMethod.GET)
    @ResponseBody
    public SingleResponse<List<ScenicspotBaseLabel>> scenicspotMchBaseLabel(UserAware userAware, @PathVariable("id") Long id) {
        Scenicspot scenicspot = scenicspotService.getById(id);
        List<ScenicspotBaseLabel> scenicspotBaseTypes = scenicspotBaseLabelService.lambdaQuery()
                .in(ScenicspotBaseLabel::getCreateById, scenicspot.getCreateById(), userAware.getMchId())
                .orderByDesc(ScenicspotBaseLabel::getId).list();
        return SingleResponse.of(scenicspotBaseTypes);
    }

    private String getScenicspotBaseLabels(Long id) {
        List<ScenicspotLabel> scenicspotLabelList = scenicspotLabelService.lambdaQuery().eq(ScenicspotLabel::getScenicSpotId, id).list();
        return scenicspotLabelList.stream().map(x -> x.getLabelId() + "").collect(Collectors.joining(","));
    }

    private String getScenicspotBaseTypes(Long id) {
        List<ScenicspotType> scenicspotTypeList = scenicspotTypeService.lambdaQuery().eq(ScenicspotType::getScenicSpotId, id).list();
        return scenicspotTypeList.stream().map(x -> x.getTypeId() + "").collect(Collectors.joining(","));
    }

    private List<ScenicspotMaterialDTO> getScenicMaterials(Long id) {
        return scenicspotMaterialService.lambdaQuery().eq(ScenicspotMaterial::getScenicSpotId, id).list().stream().map(x -> new ScenicspotMaterialDTO(x.getMaterialId(), x.getLabel(), x.getTitle(), x.getType())).collect(Collectors.toList());
    }

}
