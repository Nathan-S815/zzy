package com.nuwa.infrastructure.ticket.database.scenicspot.mapper.query;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.annotation.FieldStrategy;
import com.baomidou.mybatisplus.annotation.TableField;
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

import java.math.BigDecimal;
import java.util.Collection;
import java.util.Objects;


/**
 * @author hy
 */
@ApiModel(value = "用户获取商户景点分页参数")
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = true)
public class PageByUserScenicspotJoinQuery extends BaseJoinPagingQuery<PageByUserScenicspotJoinQuery> {
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

    @ApiModelProperty(value = "所属商户id", hidden = true)
    @JoinColumn(tableClass = MerchantScenicspotPoi.class)
    private Long merchantId;

    @ApiModelProperty(value = "权重", hidden = true)
    @JoinColumn(tableClass = MerchantScenicspotPoi.class, column = MerchantScenicspotPoi.WEIGHT)
    private Integer weight;

    @JoinColumn(tableClass = Scenicspot.class, column = Scenicspot.GRADE)
    @ApiModelProperty(value = "景区等级（1,2,3,4,5）", hidden = true)
    private Integer grade;

    @ApiModelProperty("景区类型")
    @JoinColumn(tableClass = ScenicspotType.class, column = ScenicspotType.TYPE_ID)
    private Long typeId;

    @ApiModelProperty(value = "景区ids", hidden = true)
    @JoinColumn(tableClass = Scenicspot.class, column = Scenicspot.ID)
    private Collection<Long> scenicspotIds;

    @ApiModelProperty("版本标志[1正式 0副本]")
    @JoinColumn(tableClass = Scenicspot.class, column = Scenicspot.VERSION_FLAG)
    private Integer versionFlag;

    @ApiModelProperty(value = "删除标志", hidden = true)
    @JoinColumn(tableClass = Scenicspot.class, column = Scenicspot.DELETE_FLAG)
    private Integer deleteFlag;

    @JoinColumn(tableClass = MerchantScenicspotPoi.class, column = MerchantScenicspotPoi.SCENIC_SPOT_ID)
    private Boolean merchantScenicspotPoiIdIsNull;


    @ApiModelProperty("起售价格")
    @JoinColumn(tableClass = Scenicspot.class, column = Scenicspot.PRICE_MIN)
    private BigDecimal priceMin;

    @ApiModelProperty("排序方式 1:按星级倒序")
    private Integer sortType;

    @ApiModelProperty(value = "poiType[scenic(景区) venue(文博)]")
    @JoinColumn(tableClass = Scenicspot.class, column = Scenicspot.POI_TYPE)
    private String poiType;

    @Override
    public void where(JoinQueryBuilder<PageByUserScenicspotJoinQuery> wrapper) {
        //0:热度排序 1:按星级倒序 2:按距离排序  3:价格倒序 4:价格正序
        if (Objects.isNull(sortType)) {
            wrapper.orderByAsc(PageByUserScenicspotJoinQuery::getWeight);
        } else if (sortType.equals(0)) {
            wrapper.orderByDesc(PageByUserScenicspotJoinQuery::getWeight);
        } else if (sortType.equals(1)) {
            wrapper.orderByDesc(PageByUserScenicspotJoinQuery::getWeight);
        } else if (sortType.equals(3)) {
            wrapper.orderByDesc(PageByUserScenicspotJoinQuery::getPriceMin);
        } else if (sortType.equals(4)) {
            wrapper.orderByAsc(PageByUserScenicspotJoinQuery::getPriceMin);
        }
        wrapper.eq(PageByUserScenicspotJoinQuery::getDeleteFlag, DeleteFlagEnum.NORMAL.getCode());
        wrapper.isNull(this.merchantScenicspotPoiIdIsNull, PageByUserScenicspotJoinQuery::getMerchantScenicspotPoiIdIsNull);
        wrapper.eq(PageByUserScenicspotJoinQuery::getStatus, AuditStatusEnum.AUDIT_PASS.getCode());
        wrapper.eq(StrUtil.isNotEmpty(this.getPoiType()), PageByUserScenicspotJoinQuery::getPoiType, this.getPoiType());
        wrapper.eq(StrUtil.isNotEmpty(this.getProvinceId()), PageByUserScenicspotJoinQuery::getProvinceId, this.getProvinceId());
        wrapper.eq(PageByUserScenicspotJoinQuery::getVersionFlag, 1);
        wrapper.eq(Objects.nonNull(this.getTypeId()), PageByUserScenicspotJoinQuery::getTypeId, this.getTypeId());
        wrapper.like(StrUtil.isNotEmpty(this.getName()), PageByUserScenicspotJoinQuery::getName, this.getName());
        wrapper.eq(Objects.nonNull(this.getMerchantId()), PageByUserScenicspotJoinQuery::getMerchantId, this.getMerchantId());
        if (Objects.nonNull(scenicspotIds) && scenicspotIds.size() > 0) {
            wrapper.in(PageByUserScenicspotJoinQuery::getScenicspotIds, this.getScenicspotIds());
        }
    }
}
