package com.nuwa.infrastructure.ticket.database.scenicspot.mapper.query;

import cn.hutool.core.util.StrUtil;
import com.nuwa.framework.database.tk.join.annotation.JoinColumn;
import com.nuwa.framework.database.tk.join.query.BaseJoinPagingQuery;
import com.nuwa.framework.database.tk.join.wrappper.JoinQueryBuilder;
import com.nuwa.infrastructure.ticket.database.scenicspot.entity.Scenicspot;
import com.nuwa.infrastructure.ticket.database.scenicspot.entity.ScenicspotType;
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
@ApiModel(value = "商户景点分页参数")
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = true)
public class PageScenicspotJoinQuery extends BaseJoinPagingQuery<PageScenicspotJoinQuery> {
    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "id", hidden = true)
    @JoinColumn(tableClass = Scenicspot.class, column = Scenicspot.ID)
    private Integer id;

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
    @JoinColumn(tableClass = Scenicspot.class)
    private String createById;

    @ApiModelProperty("景区类型")
    @JoinColumn(tableClass = ScenicspotType.class, column = ScenicspotType.TYPE_ID)
    private Long typeId;

    @ApiModelProperty("版本标志[1正式 0副本]")
    @JoinColumn(tableClass = Scenicspot.class, column = Scenicspot.VERSION_FLAG)
    private Integer versionFlag;

    @ApiModelProperty(value = "删除标志", hidden = true)
    @JoinColumn(tableClass = Scenicspot.class, column = Scenicspot.DELETE_FLAG)
    private Integer deleteFlag;

    @ApiModelProperty(value = "poiType[scenic(景区) venue(文博)]")
    @JoinColumn(tableClass = Scenicspot.class, column = Scenicspot.POI_TYPE)
    private String poiType;

    @ApiModelProperty("包含草稿状态")
    @JoinColumn(tableClass = Scenicspot.class)
    private boolean includeDraft = true;

    @Override
    public void where(JoinQueryBuilder<PageScenicspotJoinQuery> wrapper) {
        wrapper.orderByDesc(PageScenicspotJoinQuery::getId);
        wrapper.eq(Objects.nonNull(this.getStatus()), PageScenicspotJoinQuery::getStatus, this.getStatus());
        wrapper.ne(!includeDraft, PageScenicspotJoinQuery::getStatus, -1);
        wrapper.eq(PageScenicspotJoinQuery::getDeleteFlag, DeleteFlagEnum.NORMAL.getCode());
        wrapper.eq(StrUtil.isNotEmpty(this.getPoiType()), PageScenicspotJoinQuery::getPoiType, this.getPoiType());
        wrapper.eq(StrUtil.isNotEmpty(this.getProvinceId()), PageScenicspotJoinQuery::getProvinceId, this.getProvinceId());
        wrapper.eq(StrUtil.isNotEmpty(this.getCityId()), PageScenicspotJoinQuery::getCityId, this.getCityId());
        wrapper.eq(StrUtil.isNotEmpty(this.getAreaId()), PageScenicspotJoinQuery::getAreaId, this.getAreaId());
        wrapper.eq(Objects.nonNull(this.getGrade()), PageScenicspotJoinQuery::getGrade, this.getGrade());
        wrapper.eq(Objects.nonNull(this.getVersionFlag()), PageScenicspotJoinQuery::getVersionFlag, this.getVersionFlag());
        wrapper.eq(Objects.nonNull(this.getTypeId()), PageScenicspotJoinQuery::getTypeId, this.getTypeId());
        wrapper.like(StrUtil.isNotEmpty(this.getName()), PageScenicspotJoinQuery::getName, this.getName());
        wrapper.eq(StrUtil.isNotEmpty(this.getCreateById()), PageScenicspotJoinQuery::getCreateById, this.getCreateById());
    }
}
