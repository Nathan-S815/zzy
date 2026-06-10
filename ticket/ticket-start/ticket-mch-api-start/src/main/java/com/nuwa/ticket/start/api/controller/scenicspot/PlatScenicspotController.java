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
import com.nuwa.infrastructure.ticket.enums.ErrorEnum;
import com.nuwa.ticket.start.api.controller.dto.ScenicspotMaterialDTO;
import com.nuwa.ticket.start.api.controller.param.AuditPassScenicspotPoiParam;
import com.nuwa.ticket.start.api.controller.param.AuditRejectScenicspotPoiParam;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * @author hy
 */
@Slf4j
@Controller
@RequestMapping("plat/scenicspot")
@Api(tags = {"平台景点POI相关"})
public class PlatScenicspotController {

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

    @ApiOperation(value = "景点POI信息-审核通过")
    @RequestMapping(value = "/poi/audit_pass", method = RequestMethod.POST)
    @ResponseBody
    public SingleResponse<?> poiAuditPass(@RequestBody @Valid AuditPassScenicspotPoiParam form, UserAware userAware) {
        Long id = form.getId();
        Scenicspot scenicspotCopy = scenicspotService.getById(id);
        boolean update = scenicspotService.lambdaUpdate()
                .set(Scenicspot::getStatus, AuditStatusEnum.AUDIT_PASS.getCode())
                .set(Scenicspot::getLastUpdateById, userAware.getMchUserId())
                .set(Scenicspot::getLastUpdateTime, new Date())
                .eq(Scenicspot::getId, form.getId())
                .update();

        Scenicspot scenicspot = new Scenicspot();
        BeanUtils.copyProperties(scenicspotCopy, scenicspot);

        scenicspot.setId(scenicspotCopy.getSrcId());
        scenicspot.setVersionFlag(1);
        scenicspot.setStatus(AuditStatusEnum.AUDIT_PASS.getCode());
        scenicspot.setLastUpdateTime(new Date());
        scenicspot.setLastUpdateById(userAware.getMchUserId() + "");
        scenicspot.setLastUpdateByName(userAware.getUserName());
        boolean b = scenicspot.updateById();
        if (b) {
            log.info("update scenicspot  id:{}  success", scenicspot.getId());

            //保存景点分类信息
            String typeIds = scenicspotTypeService.lambdaQuery().eq(ScenicspotType::getScenicSpotId, id).list()
                    .stream().map(x -> x.getTypeId() + "")
                    .collect(Collectors.joining(","));
            saveScenicType(scenicspot.getId(), typeIds);
            log.info("update scenicspot type id:{}  success", scenicspot.getId());

            //保存景点标签信息
            String labelIds = scenicspotLabelService.lambdaQuery().eq(ScenicspotLabel::getScenicSpotId, id).list()
                    .stream().map(x -> x.getLabelId() + "")
                    .collect(Collectors.joining(","));
            saveScenicLabel(scenicspot.getId(), labelIds);
            log.info("update scenicspot label id:{}  success", scenicspot.getId());

            List<ScenicspotMaterial> scenicspotMaterialList = scenicspotMaterialService.lambdaQuery().eq(ScenicspotMaterial::getScenicSpotId, id).list();
            List<ScenicspotMaterialDTO> materialDTOList = scenicspotMaterialList.stream().map(x -> {
                return new ScenicspotMaterialDTO(x.getMaterialId(), x.getLabel(), x.getTitle(), x.getType());
            }).collect(Collectors.toList());
            saveScenicMaterial(materialDTOList, scenicspot.getId());
            log.info("update scenicspot material id:{}  success", scenicspot.getId());
        }
        return SingleResponse.buildSuccess();
    }

    @ApiOperation(value = "景点POI信息-审核拒绝")
    @RequestMapping(value = "/poi/audit_reject", method = RequestMethod.POST)
    @ResponseBody
    public SingleResponse<?> poiAuditReject(@RequestBody @Valid AuditRejectScenicspotPoiParam form, UserAware userAware) {
        boolean update = scenicspotService.lambdaUpdate()
                .set(Scenicspot::getStatus, AuditStatusEnum.AUDIT_REJECT.getCode())
                .set(Scenicspot::getRejectReason, form.getReason())
                .set(Scenicspot::getLastUpdateById, userAware.getMchUserId())
                .set(Scenicspot::getLastUpdateTime, new Date())
                .eq(Scenicspot::getId, form.getId())
                .update();
        if (update) {
            return SingleResponse.buildSuccess();
        }
        return ErrorEnum.FAILED.build();
    }

    @ApiOperation(value = "POI[待审核分页]查询")
    @RequestMapping(value = "/wait_audit/page", method = RequestMethod.GET)
    @ResponseBody
    public SingleResponse<Page<MerchantScenicspotPageVO>> waitAuditPage(MchScenicspotPageJoinQry query, UserAware userAware) {
        PageScenicspotJoinQuery queryPage = new PageScenicspotJoinQuery();
        BeanUtils.copyProperties(query, queryPage);
        if (query.getStatus() == null) {
            //queryPage.setStatus(AuditStatusEnum.WAIT_AUDIT.getCode());
        } else {
            //-1草稿;0:待审核;1:审核通过;2审核不通过
            if (query.getStatus().equals(0)) {
                queryPage.setStatus(AuditStatusEnum.WAIT_AUDIT.getCode());
            } else if (query.getStatus().equals(1)) {
                queryPage.setStatus(AuditStatusEnum.AUDIT_PASS.getCode());
            }else if (query.getStatus().equals(2)) {
                queryPage.setStatus(AuditStatusEnum.AUDIT_REJECT.getCode());
            }
        }
        queryPage.setVersionFlag(0);
        queryPage.setIncludeDraft(false);
        Page<MerchantScenicspotPageVO> pageData = scenicspotJoinMapper.paginateByQuery(queryPage);
        return SingleResponse.of(pageData);
    }

    private void saveScenicType(Long scenicspotId, String typeIds) {
        scenicspotTypeService.remove(Wrappers.<ScenicspotType>lambdaQuery().eq(ScenicspotType::getScenicSpotId, scenicspotId));
        List<ScenicspotType> scenicspotTypeList = Arrays.stream(typeIds.split(",")).map(x -> {
            ScenicspotType scenicspotType = new ScenicspotType();
            scenicspotType.setScenicSpotId(scenicspotId);
            scenicspotType.setTypeId(Long.parseLong(x));
            return scenicspotType;
        }).collect(Collectors.toList());
        scenicspotTypeService.saveBatch(scenicspotTypeList);
    }

    private void saveScenicLabel(Long scenicspotId, String typeIds) {
        scenicspotLabelService.remove(Wrappers.<ScenicspotLabel>lambdaQuery().eq(ScenicspotLabel::getScenicSpotId, scenicspotId));
        List<ScenicspotLabel> scenicspotLabelList = Arrays.stream(typeIds.split(",")).map(x -> {
            ScenicspotLabel scenicspotType = new ScenicspotLabel();
            scenicspotType.setScenicSpotId(scenicspotId);
            scenicspotType.setLabelId(Long.parseLong(x));
            return scenicspotType;
        }).collect(Collectors.toList());
        scenicspotLabelService.saveBatch(scenicspotLabelList);
    }

    private void saveScenicMaterial(List<ScenicspotMaterialDTO> materialList, Long scenicspotId) {
        scenicspotMaterialService.remove(Wrappers.<ScenicspotMaterial>lambdaQuery().eq(ScenicspotMaterial::getScenicSpotId, scenicspotId));
        if (Objects.nonNull(materialList) && materialList.size() > 0) {
            List<ScenicspotMaterial> scenicspotMaterialList = materialList.stream().map(x -> {
                ScenicspotMaterial scenicspotMaterial = new ScenicspotMaterial();
                BeanUtils.copyProperties(x, scenicspotMaterial);
                scenicspotMaterial.setScenicSpotId(scenicspotId);
                scenicspotMaterial.setWeight(100);
                return scenicspotMaterial;
            }).collect(Collectors.toList());
            scenicspotMaterialService.saveBatch(scenicspotMaterialList);
        }
    }

    private List<ScenicspotMaterialDTO> getScenicMaterials(Long id) {
        return scenicspotMaterialService.lambdaQuery().eq(ScenicspotMaterial::getScenicSpotId, id).list().stream().map(x -> new ScenicspotMaterialDTO(x.getMaterialId(), x.getLabel(), x.getTitle(), x.getType())).collect(Collectors.toList());
    }

}
