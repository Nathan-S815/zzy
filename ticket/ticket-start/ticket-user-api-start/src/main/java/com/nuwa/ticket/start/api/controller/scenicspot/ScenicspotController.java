package com.nuwa.ticket.start.api.controller.scenicspot;

import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONUtil;
import com.alibaba.cola.dto.SingleResponse;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.nuwa.app.ticket.command.query.UserScenicspotPageJoinQry;
import com.nuwa.infrastructure.ticket.database.scenicspot.entity.Scenicspot;
import com.nuwa.infrastructure.ticket.database.scenicspot.entity.ScenicspotBaseLabel;
import com.nuwa.infrastructure.ticket.database.scenicspot.entity.ScenicspotLabel;
import com.nuwa.infrastructure.ticket.database.scenicspot.entity.ScenicspotMaterial;
import com.nuwa.infrastructure.ticket.database.scenicspot.mapper.MerchantCanSelectScenicspotJoinMapper;
import com.nuwa.infrastructure.ticket.database.scenicspot.mapper.ScenicspotJoinMapper;
import com.nuwa.infrastructure.ticket.database.scenicspot.mapper.UserScenicspotJoinMapper;
import com.nuwa.infrastructure.ticket.database.scenicspot.mapper.query.PageByUserScenicspotJoinQuery;
import com.nuwa.infrastructure.ticket.database.scenicspot.service.*;
import com.nuwa.infrastructure.ticket.database.scenicspot.vo.ScenicspotLabelVO;
import com.nuwa.infrastructure.ticket.database.scenicspot.vo.UserScenicspotPageVO;
import com.nuwa.infrastructure.ticket.enums.SuitedPeopleEnum;
import com.nuwa.infrastructure.ticket.service.geo.IGeoService;
import com.nuwa.ticket.start.api.controller.scenicspot.vo.ScenicspotDetailVO;
import com.nuwa.ticket.start.api.controller.scenicspot.vo.ScenicspotPicturesVO;
import com.nuwa.ticket.start.api.util.DistanceUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.map.HashedMap;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.geo.*;
import org.springframework.data.redis.connection.RedisGeoCommands;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.validation.Valid;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @author hy
 */
@Slf4j
@Controller
@RequestMapping("scenicspot")
@Api(tags = {"景区相关"})
public class ScenicspotController {

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
    private UserScenicspotJoinMapper userScenicspotJoinMapper;

    @Autowired
    private MerchantScenicspotPoiService merchantScenicspotPoiService;

    @Autowired
    private ScenicspotBaseLabelService scenicspotBaseLabelService;

    @Autowired
    private IGeoService geoService;

    @ApiOperation(value = "景区分页查询")
    @RequestMapping(value = "/page", method = RequestMethod.GET)
    @ResponseBody
    public SingleResponse<Page<UserScenicspotPageVO>> page(@Valid UserScenicspotPageJoinQry query) {
        Integer sortType = query.getSortType();
        if (StrUtil.isBlank(query.getPoiType())) {
            query.setPoiType("scenic");
        }
        Map<Long, String> labelMap = scenicspotBaseLabelService.lambdaQuery()
                .list()
                .stream()
                .collect(Collectors.toMap(ScenicspotBaseLabel::getId, ScenicspotBaseLabel::getLabelName));
        if (Objects.isNull(sortType) || sortType.equals(0) || sortType.equals(1) || sortType.equals(3) || sortType.equals(4)) {
            PageByUserScenicspotJoinQuery queryPage = new PageByUserScenicspotJoinQuery();
            BeanUtils.copyProperties(query, queryPage);
            queryPage.setMerchantScenicspotPoiIdIsNull(false);
            String mchId = query.getMchId();
            queryPage.setMerchantId(Long.parseLong(mchId));
            queryPage.setScenicspotIds(query.getIds());
            Page<UserScenicspotPageVO> pageData = userScenicspotJoinMapper.paginateByQuery(queryPage);
            pageData.getRecords().forEach(x -> {
                if (x.getPoiType().equalsIgnoreCase("scenic")) {
                    x.setGradeName(convertGradeName(x.getGrade()));
                }else{
                    x.setGradeName(convertJBGradeName(x.getGrade()));
                }
                List<ScenicspotLabelVO> labelVOList = scenicspotLabelService.lambdaQuery()
                        .eq(ScenicspotLabel::getScenicSpotId, x.getId())
                        .list()
                        .stream()
                        .map(s -> {
                            ScenicspotLabelVO labelVO = new ScenicspotLabelVO();
                            labelVO.setLabelId(s.getLabelId());
                            labelVO.setLabelName(labelMap.get(s.getLabelId()));
                            return labelVO;
                        }).collect(Collectors.toList());
                x.setLabelList(labelVOList);

                if (Objects.nonNull(query.getLongitude()) && Objects.nonNull(query.getLatitude())) {
                    BigDecimal distance = DistanceUtils.getDistance(query.getLongitude(), query.getLatitude(), x.getLongitude(), x.getLatitude());
                    x.setDistance(distance);
                }
            });
            return SingleResponse.of(pageData);
        } else if (sortType.equals(2)) {
            Double latitude = query.getLatitude();
            Double longitude = query.getLongitude();
            if (Objects.isNull(latitude) || Objects.isNull(longitude)) {
                return SingleResponse.buildFailure("9875", "经纬度参数不能为空");
            }
            RedisGeoCommands.GeoRadiusCommandArgs args = RedisGeoCommands.GeoRadiusCommandArgs.newGeoRadiusArgs()
                    .includeDistance()
                    .limit(query.getLimit())
                    .sortAscending();
            Point center = new Point(query.getLongitude(), query.getLatitude());
            if (Objects.isNull(query.getRadiusKm())) {
                query.setRadiusKm(6000);
            }
            Distance radius = new Distance(query.getRadiusKm(), Metrics.KILOMETERS);
            Circle within = new Circle(center, radius);
            String mchId = query.getMchId();
            if (StrUtil.isBlankOrUndefined(mchId)) {
                return SingleResponse.buildFailure("6031", "权限认证异常");
            }

            GeoResults<RedisGeoCommands.GeoLocation<String>> pointRadius = geoService.getPointRadius(mchId + "", within, args);
            log.info("geo:{}", JSONUtil.toJsonPrettyStr(pointRadius));
            List<Long> scenicspotIds = pointRadius.getContent().stream().map(x -> Long.parseLong(x.getContent().getName())).collect(Collectors.toList());
            if (scenicspotIds.size() == 0) {
                Page<UserScenicspotPageVO> pageData = new Page<>(0, 0);
                pageData.setOrders(new ArrayList<>());
                return SingleResponse.of(pageData);
            }
            Map<Long, Double> distanceMap = new HashedMap();
            pointRadius.getContent().forEach(x -> {
                distanceMap.put(Long.parseLong(x.getContent().getName()), x.getDistance().getValue());
            });
            List<UserScenicspotPageVO> scenicspotItems = new ArrayList<>();
            PageByUserScenicspotJoinQuery queryPage = new PageByUserScenicspotJoinQuery();
            BeanUtils.copyProperties(query, queryPage);
            queryPage.setMerchantScenicspotPoiIdIsNull(false);
            queryPage.setMerchantId(Long.parseLong(mchId));
            queryPage.setScenicspotIds(scenicspotIds);
            queryPage.setLimit(100);
            queryPage.setPoiType(query.getPoiType());
            Page<UserScenicspotPageVO> pageData = userScenicspotJoinMapper.paginateByQuery(queryPage);
            pageData.getRecords().forEach(x -> {
                x.setGradeName(convertGradeName(x.getGrade()));
                List<ScenicspotLabelVO> labelVOList = scenicspotLabelService.lambdaQuery()
                        .eq(ScenicspotLabel::getScenicSpotId, x.getId())
                        .list()
                        .stream()
                        .map(s -> {
                            ScenicspotLabelVO labelVO = new ScenicspotLabelVO();
                            labelVO.setLabelId(s.getLabelId());
                            labelVO.setLabelName(labelMap.get(s.getLabelId()));
                            return labelVO;
                        }).collect(Collectors.toList());
                x.setLabelList(labelVOList);
            });

            Map<Long, UserScenicspotPageVO> mapScenic = pageData.getRecords().stream().collect(Collectors.toMap(UserScenicspotPageVO::getId, x -> x));
            scenicspotIds.forEach(x -> {
                UserScenicspotPageVO vo = mapScenic.get(x);
                if (Objects.nonNull(vo)) {
                    Double distance = distanceMap.get(x);
                    BigDecimal decimalDis = new BigDecimal(distance);
                    BigDecimal decimalVal = decimalDis.setScale(1, RoundingMode.UP);
                    vo.setDistance(decimalVal);
                    scenicspotItems.add(vo);
                }
            });
            pageData.setRecords(scenicspotItems);
            return SingleResponse.of(pageData);
        }
        return SingleResponse.buildFailure("9875", "查询失败,参数有误");
    }

    @ApiOperation(value = "景区详情")
    @RequestMapping(value = "/detail/{id}", method = RequestMethod.GET)
    @ResponseBody
    public SingleResponse<ScenicspotDetailVO> detail(@PathVariable("id") Long id) {
        ScenicspotDetailVO detailVO = new ScenicspotDetailVO();
        Scenicspot scenicspot = scenicspotService.getById(id);
        BeanUtils.copyProperties(scenicspot, detailVO);
        if (scenicspot.getPoiType().equalsIgnoreCase("scenic")) {
            detailVO.setGradeName(convertGradeName(scenicspot.getGrade()));
        }else{
            detailVO.setGradeName(convertJBGradeName(scenicspot.getGrade()));
        }
        Map<Long, String> labelMap = scenicspotBaseLabelService.lambdaQuery()
                .list()
                .stream()
                .collect(Collectors.toMap(ScenicspotBaseLabel::getId, ScenicspotBaseLabel::getLabelName));

        List<ScenicspotLabelVO> labelVOList = scenicspotLabelService.lambdaQuery()
                .eq(ScenicspotLabel::getScenicSpotId, id)
                .list()
                .stream()
                .map(x -> {
                    ScenicspotLabelVO labelVO = new ScenicspotLabelVO();
                    labelVO.setLabelId(x.getLabelId());
                    labelVO.setLabelName(labelMap.get(x.getLabelId()));
                    return labelVO;
                }).collect(Collectors.toList());
        detailVO.setLabelList(labelVOList);

        String pictureList = scenicspotMaterialService.lambdaQuery().eq(ScenicspotMaterial::getScenicSpotId, id)
                .list().stream()
                .map(x -> x.getMaterialId() + "")
                .collect(Collectors.joining(","));
        detailVO.setPictureList(pictureList);

        if (StrUtil.isNotEmpty(scenicspot.getSuitedPeople())) {
            Map<Integer, String> suitedPeopleMap = SuitedPeopleEnum.toMap();
            List<String> suitedPeopleNames = Arrays.stream(scenicspot.getSuitedPeople().split(",")).map(x -> {
                return suitedPeopleMap.get(Integer.parseInt(x));
            }).collect(Collectors.toList());
            detailVO.setSuitedPeopleNames(suitedPeopleNames);
        }
        return SingleResponse.of(detailVO);
    }

    @ApiOperation(value = "景区图片列表")
    @RequestMapping(value = "/{id}/pictureList", method = RequestMethod.GET)
    @ResponseBody
    public SingleResponse<List<ScenicspotPicturesVO>> getPictureList(@PathVariable("id") Long id) {
        List<ScenicspotMaterial> materials = scenicspotMaterialService.lambdaQuery()
                .eq(ScenicspotMaterial::getScenicSpotId, id).list();
        List<ScenicspotPicturesVO> voList = materials.stream().map(x -> {
            ScenicspotPicturesVO vo = new ScenicspotPicturesVO();
            BeanUtils.copyProperties(x, vo);
            return vo;
        }).collect(Collectors.toList());
        return SingleResponse.of(voList);
    }

    private String convertGradeName(Integer grade) {
        if (grade.equals(0)) {
            return "无等级";
        } else if (grade.equals(5)) {
            return "5A景区";
        } else if (grade.equals(4)) {
            return "4A景区";
        } else if (grade.equals(3)) {
            return "3A景区及以下";
        } else {
            return "--";
        }
    }
    private String convertJBGradeName(Integer grade) {
        if (grade.equals(1)) {
            return "一级";
        } else if (grade.equals(2)) {
            return "二级";
        } else if (grade.equals(3)) {
            return "三级";
        } else {
            return "--";
        }
    }

}
