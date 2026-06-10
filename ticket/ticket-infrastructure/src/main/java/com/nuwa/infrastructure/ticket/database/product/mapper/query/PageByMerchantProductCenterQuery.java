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
public class PageByMerchantProductCenterQuery extends BaseJoinPagingQuery<PageByMerchantProductCenterQuery> {
    private static final long serialVersionUID = 1L;

    @ApiModelProperty("产品Id")
    @JoinColumn(tableClass = ScenicspotProduct.class, column = ScenicspotProduct.ID)
    private Long id;

    @ApiModelProperty("产品名称")
    @JoinColumn(tableClass = ScenicspotProduct.class, column = ScenicspotProduct.NAME)
    private String name;

    @ApiModelProperty("供应商-商户id")
    @JoinColumn(tableClass = ScenicspotProduct.class, column = ScenicspotProduct.MERCHANT_ID)
    private Long supplierMchId;

    @ApiModelProperty("分销商-商户id")
    @JoinColumn(tableClass = MerchantProductDistribute.class, column = MerchantProductDistribute.DISTRIBUTE_MERCHANT_ID)
    private Long distributeMchId;

    @ApiModelProperty(value = "分销表id", hidden = true)
    @JoinColumn(tableClass = MerchantProductDistribute.class, column = MerchantProductDistribute.ID)
    private Long distributeProductId;

    @ApiModelProperty(value = "状态 0:未上架 1:已上架", hidden = true)
    @JoinColumn(tableClass = ScenicspotProduct.class, column = ScenicspotProduct.STATUS)
    private Integer status;

    @ApiModelProperty("状态 0:未上架 1:已上架")
    @JoinColumn(tableClass = MerchantProductDistribute.class, column = MerchantProductDistribute.PUBLISH_STATUS)
    private Integer distributeStatus;

    @ApiModelProperty("景点POI名称")
    @JoinColumn(tableClass = Scenicspot.class, column = Scenicspot.NAME)
    private String scenicspotName;

    @ApiModelProperty("景点POI id")
    @JoinColumn(tableClass = Scenicspot.class, column = Scenicspot.ID)
    private String scenicspotId;

    @ApiModelProperty("上架时间-开始")
    @JoinColumn(tableClass = MerchantProductDistribute.class, column = MerchantProductDistribute.PUBLISH_TIME)
    private Date publishTimeStart;

    @ApiModelProperty("上架时间-结束")
    @JoinColumn(tableClass = MerchantProductDistribute.class, column = MerchantProductDistribute.PUBLISH_TIME)
    private Date publishTimeEnd;

    @ApiModelProperty(value = "删除状态", hidden = true)
    @JoinColumn(tableClass = ScenicspotProduct.class, column = ScenicspotProduct.DELETE_FLAG)
    private Integer deleteFlag;

    @ApiModelProperty(value = "隐藏已选择产品", hidden = true)
    private boolean hideSelected;

    @Override
    public void where(JoinQueryBuilder<PageByMerchantProductCenterQuery> wrapper) {
        wrapper.eq(PageByMerchantProductCenterQuery::getStatus, 1);
        wrapper.isNull(hideSelected, PageByMerchantProductCenterQuery::getDistributeProductId);
        wrapper.eq(Objects.nonNull(id), PageByMerchantProductCenterQuery::getId, id);
        wrapper.eq(Objects.nonNull(scenicspotId), PageByMerchantProductCenterQuery::getScenicspotId, scenicspotId);
        wrapper.like(StrUtil.isNotBlank(name), PageByMerchantProductCenterQuery::getName, name);
        wrapper.eq(Objects.nonNull(supplierMchId), PageByMerchantProductCenterQuery::getSupplierMchId, supplierMchId);
        wrapper.eq(Objects.nonNull(distributeMchId), PageByMerchantProductCenterQuery::getDistributeMchId, distributeMchId);
        wrapper.eq(Objects.nonNull(status), PageByMerchantProductCenterQuery::getStatus, status);
        wrapper.eq(PageByMerchantProductCenterQuery::getDeleteFlag, DeleteFlagEnum.NORMAL.getCode());
        wrapper.like(StrUtil.isNotBlank(scenicspotName), PageByMerchantProductCenterQuery::getScenicspotName, scenicspotName);
        if (Objects.nonNull(getPublishTimeStart())) {
            wrapper.ge(PageByMerchantProductCenterQuery::getPublishTimeStart, DateUtil.beginOfDay(getPublishTimeStart()));
        }
        if (Objects.nonNull(getPublishTimeEnd())) {
            wrapper.ge(PageByMerchantProductCenterQuery::getPublishTimeEnd, DateUtil.endOfDay(getPublishTimeEnd()));
        }
    }
}
