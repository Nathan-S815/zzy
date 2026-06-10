package com.nuwa.infrastructure.ticket.database.product.mapper.query;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.StrUtil;
import com.nuwa.framework.database.tk.join.annotation.JoinColumn;
import com.nuwa.framework.database.tk.join.query.BaseJoinPagingQuery;
import com.nuwa.framework.database.tk.join.wrappper.JoinQueryBuilder;
import com.nuwa.infrastructure.ticket.database.product.entity.MerchantProductDistribute;
import com.nuwa.infrastructure.ticket.database.product.entity.ScenicspotProduct;
import com.nuwa.infrastructure.ticket.database.scenicspot.entity.Scenicspot;
import com.nuwa.infrastructure.ticket.enums.DeleteFlagEnum;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.util.Date;
import java.util.Objects;


/**
 * @author hy
 */
@ApiModel(value = "商户景区产品分页查询")
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = true)
public class PageByMerchantProductDistributeQuery extends BaseJoinPagingQuery<PageByMerchantProductDistributeQuery> {
    private static final long serialVersionUID = 1L;

    @ApiModelProperty("产品Id")
    @JoinColumn(tableClass = ScenicspotProduct.class, column = ScenicspotProduct.ID)
    private Long id;

    @ApiModelProperty(value = "分销表id", hidden = true)
    @JoinColumn(tableClass = MerchantProductDistribute.class, column = MerchantProductDistribute.ID)
    private Long distributeProductId;

    @ApiModelProperty("产品名称")
    @JoinColumn(tableClass = ScenicspotProduct.class, column = ScenicspotProduct.NAME)
    private String name;

    @ApiModelProperty("供应商-商户id")
    @JoinColumn(tableClass = ScenicspotProduct.class, column = ScenicspotProduct.MERCHANT_ID)
    private Long supplierMchId;

    @ApiModelProperty("供应商-商户名称")
    @JoinColumn(tableClass = MerchantProductDistribute.class, column = MerchantProductDistribute.SUPPLIER_MERCHANT_NAME)
    private String supplierMchName;

    @ApiModelProperty("分销商-商户id")
    @JoinColumn(tableClass = MerchantProductDistribute.class, column = MerchantProductDistribute.DISTRIBUTE_MERCHANT_ID)
    private Long distributeMchId;

    @ApiModelProperty("分销商-商户id")
    @JoinColumn(tableClass = MerchantProductDistribute.class, column = MerchantProductDistribute.WEIGHT)
    private Integer weight;

    @ApiModelProperty("审核状态 待审核:0  审核通过:1 审核拒绝:2")
    @JoinColumn(tableClass = MerchantProductDistribute.class, column = MerchantProductDistribute.AUDIT_STATUS)
    private Integer auditStatus;

    @ApiModelProperty(value = "状态 0:未上架 1:已上架", hidden = true)
    @JoinColumn(tableClass = ScenicspotProduct.class, column = ScenicspotProduct.STATUS)
    private Integer supplierPublishStatus;

    @ApiModelProperty("状态 0:未上架 1:已上架")
    @JoinColumn(tableClass = MerchantProductDistribute.class, column = MerchantProductDistribute.PUBLISH_STATUS)
    private Integer distributePublishStatus;

    @ApiModelProperty("景点POI名称")
    @JoinColumn(tableClass = Scenicspot.class, column = Scenicspot.NAME)
    private String scenicspotName;

    @ApiModelProperty("上架时间-开始")
    @JoinColumn(tableClass = MerchantProductDistribute.class, column = MerchantProductDistribute.PUBLISH_TIME)
    private Date publishTimeStart;

    @ApiModelProperty("上架时间-结束")
    @JoinColumn(tableClass = MerchantProductDistribute.class, column = MerchantProductDistribute.PUBLISH_TIME)
    private Date publishTimeEnd;

    @ApiModelProperty("申请时间-开始")
    @JoinColumn(tableClass = MerchantProductDistribute.class, column = MerchantProductDistribute.CREATE_TIME)
    private Date createTimeStart;

    @ApiModelProperty("申请时间-结束")
    @JoinColumn(tableClass = MerchantProductDistribute.class, column = MerchantProductDistribute.CREATE_TIME)
    private Date createTimeEnd;

    @ApiModelProperty(value = "删除状态", hidden = true)
    @JoinColumn(tableClass = ScenicspotProduct.class, column = ScenicspotProduct.DELETE_FLAG)
    private Integer deleteFlag;

    @ApiModelProperty("POI-省编号")
    @JoinColumn(tableClass = Scenicspot.class, column = Scenicspot.PROVINCE_ID)
    private Long provinceId;

    @ApiModelProperty("POI-市编号")
    @JoinColumn(tableClass = Scenicspot.class, column = Scenicspot.CITY_ID)
    private Long cityId;

    @ApiModelProperty("POI-区编号")
    @JoinColumn(tableClass = Scenicspot.class, column = Scenicspot.AREA_ID)
    private Long areaId;

    @Override
    public void where(JoinQueryBuilder<PageByMerchantProductDistributeQuery> wrapper) {
        wrapper.orderByAsc(PageByMerchantProductDistributeQuery::getWeight);
        wrapper.orderByAsc(PageByMerchantProductDistributeQuery::getCreateTimeStart);
        wrapper.eq(Objects.nonNull(distributePublishStatus), PageByMerchantProductDistributeQuery::getDistributePublishStatus, distributePublishStatus);
        wrapper.eq(Objects.nonNull(supplierPublishStatus), PageByMerchantProductDistributeQuery::getSupplierPublishStatus, supplierPublishStatus);
        wrapper.eq(Objects.nonNull(id), PageByMerchantProductDistributeQuery::getId, id);
        wrapper.eq(Objects.nonNull(auditStatus), PageByMerchantProductDistributeQuery::getAuditStatus, auditStatus);
        wrapper.like(StrUtil.isNotBlank(name), PageByMerchantProductDistributeQuery::getName, name);
        wrapper.eq(Objects.nonNull(supplierMchId), PageByMerchantProductDistributeQuery::getSupplierMchId, supplierMchId);
        wrapper.like(Objects.nonNull(supplierMchName), PageByMerchantProductDistributeQuery::getSupplierMchName, supplierMchName);
        wrapper.eq(Objects.nonNull(distributeMchId), PageByMerchantProductDistributeQuery::getDistributeMchId, distributeMchId);
        wrapper.eq(PageByMerchantProductDistributeQuery::getDeleteFlag, DeleteFlagEnum.NORMAL.getCode());
        wrapper.like(StrUtil.isNotBlank(scenicspotName), PageByMerchantProductDistributeQuery::getScenicspotName, scenicspotName);
        if (Objects.nonNull(getPublishTimeStart())) {
            wrapper.ge(PageByMerchantProductDistributeQuery::getPublishTimeStart, DateUtil.beginOfDay(getPublishTimeStart()));
        }
        if (Objects.nonNull(getPublishTimeEnd())) {
            wrapper.le(PageByMerchantProductDistributeQuery::getPublishTimeEnd, DateUtil.endOfDay(getPublishTimeEnd()));
        }

        if (Objects.nonNull(getCreateTimeStart())) {
            wrapper.ge(PageByMerchantProductDistributeQuery::getCreateTimeStart, DateUtil.beginOfDay(getCreateTimeStart()));
        }
        if (Objects.nonNull(getCreateTimeEnd())) {
            wrapper.le(PageByMerchantProductDistributeQuery::getCreateTimeEnd, DateUtil.endOfDay(getCreateTimeEnd()));
        }
    }
}
