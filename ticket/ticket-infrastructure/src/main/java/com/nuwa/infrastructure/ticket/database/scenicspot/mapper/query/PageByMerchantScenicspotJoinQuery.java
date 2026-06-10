package com.nuwa.infrastructure.ticket.database.scenicspot.mapper.query;

import cn.hutool.core.util.StrUtil;
import com.nuwa.framework.database.tk.join.annotation.JoinColumn;
import com.nuwa.framework.database.tk.join.query.BaseJoinPagingQuery;
import com.nuwa.framework.database.tk.join.wrappper.JoinQueryBuilder;
import com.nuwa.infrastructure.ticket.database.scenicspot.entity.MerchantScenicspotPoi;
import com.nuwa.infrastructure.ticket.database.scenicspot.entity.Scenicspot;
import com.nuwa.infrastructure.ticket.database.scenicspot.entity.ScenicspotType;
import com.nuwa.infrastructure.ticket.enums.AuditStatusEnum;
import com.nuwa.infrastructure.ticket.enums.DeleteFlagEnum;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.util.Objects;


/**
 * @author hy
 */
@ApiModel(value = "获取商户景点分页参数")
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = true)
public class PageByMerchantScenicspotJoinQuery extends BaseJoinPagingQuery<PageByMerchantScenicspotJoinQuery> {
    private static final long serialVersionUID = 1L;

    @ApiModelProperty("状态[0:待审核;1:审核通过;2审核不通过]")
    @JoinColumn(tableClass = Scenicspot.class)
    private Integer status;

    @ApiModelProperty("景区名称")
    @JoinColumn(tableClass = Scenicspot.class)
    private String name;

    @ApiModelProperty("省id")
    @JoinColumn(tableClass = Scenicspot.class)
    private String provinceId;

    @ApiModelProperty("市id")
    @JoinColumn(tableClass = Scenicspot.class)
    private String cityId;

    @ApiModelProperty("区id")
    @JoinColumn(tableClass = Scenicspot.class)
    private String areaId;

    @ApiModelProperty("景区等级")
    @JoinColumn(tableClass = Scenicspot.class)
    private Integer grade;

    @ApiModelProperty(value = "所属商户id", hidden = true)
    @JoinColumn(tableClass = MerchantScenicspotPoi.class)
    private String merchantId;

    @ApiModelProperty("景区类型")
    @JoinColumn(tableClass = ScenicspotType.class, column = ScenicspotType.TYPE_ID)
    private Long typeId;

    @ApiModelProperty("版本标志[1正式 0副本]")
    @JoinColumn(tableClass = Scenicspot.class, column = Scenicspot.VERSION_FLAG)
    private Integer versionFlag;

    @ApiModelProperty("删除标志")
    @JoinColumn(tableClass = Scenicspot.class, column = Scenicspot.DELETE_FLAG)
    private Integer deleteFlag;

    @JoinColumn(tableClass = MerchantScenicspotPoi.class, column = MerchantScenicspotPoi.SCENIC_SPOT_ID)
    private Boolean merchantScenicspotPoiIdIsNull;

    @ApiModelProperty(value = "POI权重", hidden = true)
    @JoinColumn(tableClass = MerchantScenicspotPoi.class, column = MerchantScenicspotPoi.WEIGHT)
    private Integer weight;

    @ApiModelProperty(value = "poiType[scenic(景区) venue(文博)]")
    @JoinColumn(tableClass = Scenicspot.class, column = Scenicspot.POI_TYPE)
    private String poiType;

    @Override
    public void where(JoinQueryBuilder<PageByMerchantScenicspotJoinQuery> wrapper) {
        wrapper.isNull(this.merchantScenicspotPoiIdIsNull, PageByMerchantScenicspotJoinQuery::getMerchantScenicspotPoiIdIsNull);
        wrapper.eq(PageByMerchantScenicspotJoinQuery::getStatus, AuditStatusEnum.AUDIT_PASS.getCode());
        wrapper.eq(PageByMerchantScenicspotJoinQuery::getDeleteFlag, DeleteFlagEnum.NORMAL.getCode());
        wrapper.eq(StrUtil.isNotEmpty(this.getPoiType()), PageByMerchantScenicspotJoinQuery::getPoiType, this.getPoiType());
        wrapper.eq(StrUtil.isNotEmpty(this.getProvinceId()), PageByMerchantScenicspotJoinQuery::getProvinceId, this.getProvinceId());
        wrapper.eq(StrUtil.isNotEmpty(this.getCityId()), PageByMerchantScenicspotJoinQuery::getCityId, this.getCityId());
        wrapper.eq(StrUtil.isNotEmpty(this.getAreaId()), PageByMerchantScenicspotJoinQuery::getAreaId, this.getAreaId());
        wrapper.eq(Objects.nonNull(this.getGrade()), PageByMerchantScenicspotJoinQuery::getGrade, this.getGrade());
        wrapper.eq(Objects.nonNull(this.getVersionFlag()), PageByMerchantScenicspotJoinQuery::getVersionFlag, this.getVersionFlag());
        wrapper.eq(Objects.nonNull(this.getTypeId()), PageByMerchantScenicspotJoinQuery::getTypeId, this.getTypeId());
        wrapper.like(StrUtil.isNotEmpty(this.getName()), PageByMerchantScenicspotJoinQuery::getName, this.getName());
        wrapper.eq(StrUtil.isNotEmpty(this.getMerchantId()), PageByMerchantScenicspotJoinQuery::getMerchantId, this.getMerchantId());
        wrapper.orderByAsc(PageByMerchantScenicspotJoinQuery::getWeight);
        //wrapper.orderByDesc(PageByMerchantScenicspotJoinQuery::getMerchantScenicspotPoiIdIsNull);
    }
}
