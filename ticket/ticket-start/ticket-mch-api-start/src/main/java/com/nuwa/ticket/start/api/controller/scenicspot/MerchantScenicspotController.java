package com.nuwa.ticket.start.api.controller.scenicspot;

import cn.hutool.core.util.StrUtil;
import com.alibaba.cola.dto.SingleResponse;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.mzt.logapi.starter.annotation.LogRecordAnnotation;
import com.nuwa.app.ticket.command.query.MchScenicspotPageJoinQry;
import com.nuwa.framework.base.UserAware;
import com.nuwa.infrastructure.ticket.database.product.entity.ScenicspotProduct;
import com.nuwa.infrastructure.ticket.database.scenicspot.entity.*;
import com.nuwa.infrastructure.ticket.database.scenicspot.mapper.MerchantCanSelectScenicspotJoinMapper;
import com.nuwa.infrastructure.ticket.database.scenicspot.mapper.MerchantScenicspotJoinMapper;
import com.nuwa.infrastructure.ticket.database.scenicspot.mapper.ScenicspotJoinMapper;
import com.nuwa.infrastructure.ticket.database.scenicspot.mapper.query.PageByMerchantScenicspotJoinQuery;
import com.nuwa.infrastructure.ticket.database.scenicspot.mapper.query.PageScenicspotJoinQuery;
import com.nuwa.infrastructure.ticket.database.scenicspot.service.*;
import com.nuwa.infrastructure.ticket.database.scenicspot.vo.MerchantScenicspotPageVO;
import com.nuwa.infrastructure.ticket.enums.AuditStatusEnum;
import com.nuwa.infrastructure.ticket.enums.DeleteFlagEnum;
import com.nuwa.infrastructure.ticket.enums.SuitedPeopleEnum;
import com.nuwa.infrastructure.ticket.service.geo.IGeoService;
import com.nuwa.infrastructure.ticket.service.geo.ScenicGpsInfo;
import com.nuwa.ticket.start.api.constants.LogRecordCategoryConstant;
import com.nuwa.ticket.start.api.constants.LogRecordPrefixConstant;
import com.nuwa.ticket.start.api.controller.dto.ScenicspotMaterialDTO;
import com.nuwa.ticket.start.api.controller.param.*;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.*;
import java.util.stream.Collectors;

@Slf4j
@Controller
@RequestMapping("merchant/scenicspot")
@Api(tags = {"商户景点POI相关"})
public class MerchantScenicspotController {

    @Autowired
    private ScenicspotBaseTypeService scenicspotBaseTypeService;

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

    @Autowired
    private MerchantCanSelectScenicspotJoinMapper merchantCanSelectScenicspotJoinMapper;

    @Autowired
    private MerchantScenicspotJoinMapper merchantScenicspotJoinMapper;

    @Autowired
    private MerchantScenicspotPoiService merchantScenicspotPoiService;

    @Autowired
    private IGeoService iGeoService;

    @ApiOperation(value = "景点POI信息-新增")
    @RequestMapping(value = "/poi/save", method = RequestMethod.POST)
    @ResponseBody
    public SingleResponse<?> poiSave(@RequestBody @Valid SaveScenicspotPoiParam form, UserAware userAware) {
        Scenicspot scenicspot = new Scenicspot();
        scenicspot.setCreateById(userAware.getMchId() + "");
        scenicspot.setCreateByName(userAware.getUserName());
        scenicspot.setCreateTime(new Date());
        scenicspot.setLastUpdateTime(new Date());
        String typeNames = scenicspotBaseTypeService.lambdaQuery()
                .in(ScenicspotBaseType::getId, form.getBaseInfo().getTypeIds())
                .list()
                .stream()
                .map(ScenicspotBaseType::getLabelName)
                .collect(Collectors.joining(","));
        scenicspot.setTypeName(typeNames);
        String poiType = form.getBaseInfo().getPoiType();
        if (StrUtil.isBlank(poiType)) {
            form.getBaseInfo().setPoiType(poiType);
        }
        BeanUtils.copyProperties(form.getBaseInfo(), scenicspot);
        scenicspot.setVersionFlag(1);
        boolean insert = scenicspot.insert();
        if (insert) {
            Long scenicspotId = scenicspot.getId();
            log.info("save scenicspot id:{} success", scenicspotId);

            Scenicspot scenicspotCopy = new Scenicspot();
            BeanUtils.copyProperties(scenicspot, scenicspotCopy);
            scenicspotCopy.setVersionFlag(0);
            scenicspotCopy.setSrcId(scenicspotId);
            if (scenicspotCopy.insert()) {
                log.info("save copy scenicspot id:{} success", scenicspotId);
            } else {
                return SingleResponse.buildFailure("202", "保存景点POI信息失败");
            }

            //保存景点分类信息
            saveScenicType(scenicspotId, form.getBaseInfo().getTypeIds());
            log.info("save scenicspot type id:{}  success", scenicspotId);

            saveScenicType(scenicspotCopy.getId(), form.getBaseInfo().getTypeIds());
            log.info("save copy scenicspot type id:{}  success", scenicspotCopy.getId());

            //保存景点标签信息
            saveScenicLabel(scenicspotId, form.getBaseInfo().getLabelIds());
            log.info("save scenicspot label id:{}  success", scenicspotId);

            saveScenicLabel(scenicspotCopy.getId(), form.getBaseInfo().getLabelIds());
            log.info("save copy scenicspot label id:{}  success", scenicspotCopy.getId());

            saveScenicMaterial(form.getMaterialList(), scenicspotId);
            log.info("save scenicspot material id:{}  success", scenicspotId);

            saveScenicMaterial(form.getMaterialList(), scenicspotCopy.getId());
            log.info("save copy scenicspot material id:{}  success", scenicspotCopy.getId());

            return SingleResponse.of(scenicspotCopy.getId());
        }
        return SingleResponse.buildFailure("201", "保存景点POI信息失败");
    }

    @ApiOperation(value = "景点POI信息-修改")
    @RequestMapping(value = "/poi/modify/{id}", method = RequestMethod.POST)
    @ResponseBody
    public SingleResponse<?> poiModify(@PathVariable(value = "id") Long id, @RequestBody @Valid SaveScenicspotPoiParam form, UserAware userAware) {
        Scenicspot scenicspotUpdate = scenicspotService.getById(id);
        scenicspotUpdate.setLastUpdateTime(new Date());
        scenicspotUpdate.setLastUpdateById(userAware.getMchId() + "");
        scenicspotUpdate.setLastUpdateByName(userAware.getUserName());
        scenicspotUpdate.setStatus(AuditStatusEnum.DRAFT.getCode());
        BeanUtils.copyProperties(form.getBaseInfo(), scenicspotUpdate);
        String typeNames = scenicspotBaseTypeService.lambdaQuery()
                .in(ScenicspotBaseType::getId, form.getBaseInfo().getTypeIds())
                .list()
                .stream()
                .map(ScenicspotBaseType::getLabelName)
                .collect(Collectors.joining(","));
        scenicspotUpdate.setTypeName(typeNames);
        scenicspotUpdate.setId(id);
        boolean updateFlag = scenicspotUpdate.updateById();
        if (updateFlag) {
            log.info("update scenicspot id:{} success", id);
            //保存景点分类信息
            saveScenicType(id, form.getBaseInfo().getTypeIds());
            log.info("update scenicspot type id:{}  success", id);

            //保存景点标签信息
            saveScenicLabel(id, form.getBaseInfo().getLabelIds());
            log.info("update scenicspot label id:{}  success", id);

            saveScenicMaterial(form.getMaterialList(), id);
            log.info("update scenicspot material id:{}  success", id);

            return SingleResponse.of(id);
        }
        return SingleResponse.buildFailure("201", "修改景点POI信息失败");
    }

    @ApiOperation(value = "商户从POI库添加景区")
    @RequestMapping(value = "/poi/append", method = RequestMethod.POST)
    @ResponseBody
    public SingleResponse<?> poiAppend(@RequestBody @Valid MerchantAppendScenicspotPoiParam form, UserAware userAware) {
        Long mchId = userAware.getMchId();
        form.getIds().forEach(id -> {
            List<MerchantScenicspotPoi> list = merchantScenicspotPoiService.lambdaQuery()
                    .eq(MerchantScenicspotPoi::getMerchantId, mchId)
                    .eq(MerchantScenicspotPoi::getScenicSpotId, id)
                    .list();
            if (list.isEmpty()) {
                MerchantScenicspotPoi poi = new MerchantScenicspotPoi();
                poi.setScenicSpotId(id);
                poi.setStatus(1);
                poi.setMerchantId(mchId);
                poi.setWeight(100);
                boolean insert = poi.insert();
                if (insert) {
                    Scenicspot scenicspot = scenicspotService.getById(id);
                    List<ScenicGpsInfo> scenicGpsInfos = new ArrayList<>();
                    ScenicGpsInfo gpsInfo = new ScenicGpsInfo();
                    gpsInfo.setId(id + "");
                    gpsInfo.setLatitude(scenicspot.getLatitude());
                    gpsInfo.setLongitude(scenicspot.getLongitude());
                    scenicGpsInfos.add(gpsInfo);
                    iGeoService.saveScenicToRedis(mchId + "", scenicGpsInfos);
                    log.info("save Scenicspot[id:{}] geo success", scenicspot.getId());
                }
            }
        });
        return SingleResponse.buildSuccess();
    }

    @ApiOperation(value = "商户从POI库移除景区")
    @RequestMapping(value = "/poi/remove", method = RequestMethod.POST)
    @ResponseBody
    public SingleResponse<?> poiRemoved(@RequestBody @Valid MerchantRemovedScenicspotPoiParam form, UserAware userAware) {
        Long mchId = userAware.getMchId();
        boolean remove = merchantScenicspotPoiService.remove(Wrappers.<MerchantScenicspotPoi>lambdaQuery().eq(MerchantScenicspotPoi::getMerchantId, mchId).in(MerchantScenicspotPoi::getScenicSpotId, form.getIds()));
        if (remove) {
            log.info("mchId:{} remove merchantScenicspotPoi success.", mchId);

            form.getIds().forEach(x -> {
                iGeoService.removeScenicToRedis(mchId + "", x + "");
                log.info("save Scenicspot[id:{}] geo success", x);
            });
        }
        return SingleResponse.buildSuccess();
    }

    @ApiOperation(value = "商户修改景区权重")
    @RequestMapping(value = "/poi/modify_weight", method = RequestMethod.POST)
    @ResponseBody
    public SingleResponse<?> updatePoiWeight(@RequestBody @Valid UpdateWeightScenicspotPoiParam form, UserAware userAware) {
        boolean update = merchantScenicspotPoiService.lambdaUpdate()
                .set(MerchantScenicspotPoi::getWeight, form.getWeight())
                .eq(MerchantScenicspotPoi::getMerchantId, userAware.getMchId())
                .eq(MerchantScenicspotPoi::getScenicSpotId, form.getId())
                .update();
        if (update) {
            log.info("update merchantScenicspotPoi mchId:{},weight:{} success.", userAware.getMchId(), form.getWeight());
        }
        return SingleResponse.buildSuccess();
    }

    @ApiOperation(value = "景点POI信息-提交并审核")
    @RequestMapping(value = "/poi/submit", method = RequestMethod.POST)
    @ResponseBody
    public SingleResponse<?> poiSubmit(@RequestBody @Valid SaveAndAuditScenicspotPoiParam form, UserAware userAware) {
        boolean update = scenicspotService.lambdaUpdate()
                .set(Scenicspot::getStatus, AuditStatusEnum.WAIT_AUDIT.getCode())
                .set(Scenicspot::getLastUpdateById, userAware.getMchUserId())
                .set(Scenicspot::getLastUpdateTime, new Date())
                .eq(Scenicspot::getId, form.getId())
                .update();
        if (update) {
            return SingleResponse.buildSuccess();
        }
        return SingleResponse.buildFailure("201", "操作失败");
    }

    @ApiOperation(value = "商户原创POI分页查询")
    @RequestMapping(value = "/original/page", method = RequestMethod.GET)
    @ResponseBody
    public SingleResponse<Page<MerchantScenicspotPageVO>> pageByMy(MchScenicspotPageJoinQry query, UserAware userAware) {
        PageScenicspotJoinQuery queryPage = new PageScenicspotJoinQuery();
        BeanUtils.copyProperties(query, queryPage);
        queryPage.setCreateById(userAware.getMchId() + "");
        queryPage.setVersionFlag(0);
        Page<MerchantScenicspotPageVO> pageData = scenicspotJoinMapper.paginateByQuery(queryPage);
        return SingleResponse.of(pageData);
    }

    @ApiOperation(value = "商户POI分页查询")
    @RequestMapping(value = "/page", method = RequestMethod.GET)
    @ResponseBody
    public SingleResponse<Page<MerchantScenicspotPageVO>> page(MchScenicspotPageJoinQry query, UserAware userAware) {
        PageByMerchantScenicspotJoinQuery queryPage = new PageByMerchantScenicspotJoinQuery();
        BeanUtils.copyProperties(query, queryPage);
        queryPage.setVersionFlag(1);
        queryPage.setMerchantScenicspotPoiIdIsNull(false);
        queryPage.setMerchantId(userAware.getMchId() + "");
        Page<MerchantScenicspotPageVO> pageData = merchantScenicspotJoinMapper.paginateByQuery(queryPage);
        return SingleResponse.of(pageData);
    }

    @ApiOperation(value = "商户选择可选景区POI库分页查询")
    @RequestMapping(value = "/select/page", method = RequestMethod.GET)
    @ResponseBody
    public SingleResponse<Page<MerchantScenicspotPageVO>> selectByPage(MchScenicspotPageJoinQry query, UserAware userAware) {
        PageByMerchantScenicspotJoinQuery queryPage = new PageByMerchantScenicspotJoinQuery();
        BeanUtils.copyProperties(query, queryPage);
        queryPage.setVersionFlag(1);
        queryPage.setMerchantScenicspotPoiIdIsNull(true);
        queryPage.getExtMap().put("merchantId", userAware.getMchId() + "");
        Page<MerchantScenicspotPageVO> pageData = merchantCanSelectScenicspotJoinMapper.paginateByQuery(queryPage);
        return SingleResponse.of(pageData);
    }

    @LogRecordAnnotation(
            fail = "POI删除操作失败，原因：「{{#_errorMsg}}」",
            success = "POI删除操作成功",
            operator = "{{#userAware.userName}}({{#userAware.mchUserId}})",
            prefix = LogRecordPrefixConstant.POI,
            bizNo = "{{#scenicspotId}}",
            category = LogRecordCategoryConstant.REMOVE_POI,
            detail = "{{#scenicspotId}}")
    @ApiOperation(value = "POI删除")
    @RequestMapping(value = "{scenicspotId}/remove", method = RequestMethod.GET)
    @ResponseBody
    public SingleResponse<?> removeProduct(@PathVariable("scenicspotId") Long scenicspotId, UserAware userAware) {
        scenicspotService.lambdaUpdate()
                .set(Scenicspot::getDeleteFlag, DeleteFlagEnum.DELETE.getCode())
                .set(Scenicspot::getLastUpdateTime, new Date())
                .eq(Scenicspot::getId, scenicspotId).update();

        scenicspotService.lambdaUpdate()
                .set(Scenicspot::getDeleteFlag, DeleteFlagEnum.DELETE.getCode())
                .set(Scenicspot::getLastUpdateTime, new Date())
                .eq(Scenicspot::getSrcId, scenicspotId).update();
        return SingleResponse.buildSuccess();
    }

    @ApiOperation(value = "获取适合人群列表")
    @RequestMapping(value = "/suited/people/list", method = RequestMethod.GET)
    @ResponseBody
    public SingleResponse<List<SuitedPeopleDO>> listSuitedPeople() {
        Map<Integer, String> mapData = SuitedPeopleEnum.toMap();
        List<SuitedPeopleDO> suitedPeopleDOList = new ArrayList<>();
        mapData.forEach((k, v) -> {
            SuitedPeopleDO suitedPeopleDO = new SuitedPeopleDO();
            suitedPeopleDO.setCode(k);
            suitedPeopleDO.setMessage(v);
            suitedPeopleDOList.add(suitedPeopleDO);
        });
        return SingleResponse.of(suitedPeopleDOList);
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

    private void saveScenicMaterial(List<ScenicspotMaterialDTO> scenicspotMaterialDtoItems, Long scenicspotId) {
        scenicspotMaterialService.remove(Wrappers.<ScenicspotMaterial>lambdaQuery().eq(ScenicspotMaterial::getScenicSpotId, scenicspotId));
        if (Objects.nonNull(scenicspotMaterialDtoItems) && scenicspotMaterialDtoItems.size() > 0) {
            List<ScenicspotMaterial> scenicspotMaterialList = scenicspotMaterialDtoItems.stream().map(x -> {
                ScenicspotMaterial scenicspotMaterial = new ScenicspotMaterial();
                BeanUtils.copyProperties(x, scenicspotMaterial);
                scenicspotMaterial.setScenicSpotId(scenicspotId);
                scenicspotMaterial.setWeight(100);
                return scenicspotMaterial;
            }).collect(Collectors.toList());
            scenicspotMaterialService.saveBatch(scenicspotMaterialList);
        }
    }

    @ApiOperation(value = "更新geo数据")
    @RequestMapping(value = "/geoUpdate", method = RequestMethod.POST)
    @ResponseBody
    public SingleResponse<?> geoUpdate(UserAware userAware) {
        Long mchId = userAware.getMchId();
        List<MerchantScenicspotPoi> list = merchantScenicspotPoiService.lambdaQuery()
                .eq(MerchantScenicspotPoi::getMerchantId, mchId)
                .list();
        list.forEach(x -> {
            Scenicspot scenicspot = scenicspotService.getById(x.getScenicSpotId());
            if (Objects.nonNull(scenicspot)) {
                List<ScenicGpsInfo> scenicGpsInfos = new ArrayList<>();
                ScenicGpsInfo gpsInfo = new ScenicGpsInfo();
                gpsInfo.setId(x.getScenicSpotId() + "");
                gpsInfo.setLatitude(scenicspot.getLatitude());
                gpsInfo.setLongitude(scenicspot.getLongitude());
                scenicGpsInfos.add(gpsInfo);
                iGeoService.saveScenicToRedis(mchId + "", scenicGpsInfos);
                log.info("save Scenicspot[id:{}] geo success", scenicspot.getId());
            }
        });
        return SingleResponse.buildSuccess();
    }

    @Data
    public static class SuitedPeopleDO {
        private Integer code;
        private String message;
    }
}
