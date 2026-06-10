package com.nuwa.infrastructure.ticket.database.product.mapper.query;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.StrUtil;
import com.nuwa.framework.database.tk.join.annotation.JoinColumn;
import com.nuwa.framework.database.tk.join.query.BaseJoinPagingQuery;
import com.nuwa.framework.database.tk.join.wrappper.JoinQueryBuilder;
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
public class PageByMerchantProductQuery extends BaseJoinPagingQuery<PageByMerchantProductQuery> {
    private static final long serialVersionUID = 1L;

    @ApiModelProperty("产品Id")
    @JoinColumn(tableClass = ScenicspotProduct.class, column = ScenicspotProduct.ID)
    private Long id;

    @ApiModelProperty("商户id")
    @JoinColumn(tableClass = ScenicspotProduct.class, column = ScenicspotProduct.MERCHANT_ID)
    private Long merchantId;

    @ApiModelProperty("产品名称")
    @JoinColumn(tableClass = ScenicspotProduct.class, column = ScenicspotProduct.NAME)
    private String name;

    @ApiModelProperty("供应商id")
    @JoinColumn(tableClass = ScenicspotProduct.class, column = ScenicspotProduct.SUPPLIER_ID)
    private String supplierId;

    @ApiModelProperty("供应商产品编码")
    @JoinColumn(tableClass = ScenicspotProduct.class, column = ScenicspotProduct.SUPPLIER_PRODUCT_CODE)
    private String supplierProductCode;

    @ApiModelProperty("产品权重")
    @JoinColumn(tableClass = ScenicspotProduct.class, column = ScenicspotProduct.WEIGHT)
    private Integer weight;

    @ApiModelProperty("状态 0:未上架 1:已上架")
    @JoinColumn(tableClass = ScenicspotProduct.class, column = ScenicspotProduct.STATUS)
    private Integer status;

    @ApiModelProperty("景点POI名称")
    @JoinColumn(tableClass = Scenicspot.class, column = Scenicspot.NAME)
    private String scenicspotName;

    @ApiModelProperty("上架时间-开始")
    @JoinColumn(tableClass = ScenicspotProduct.class, column = ScenicspotProduct.PUBLISH_TIME)
    private Date publishTimeStart;

    @ApiModelProperty("上架时间-结束")
    @JoinColumn(tableClass = ScenicspotProduct.class, column = ScenicspotProduct.PUBLISH_TIME)
    private Date publishTimeEnd;

    @ApiModelProperty(value = "删除状态", hidden = true)
    @JoinColumn(tableClass = ScenicspotProduct.class, column = ScenicspotProduct.DELETE_FLAG)
    private Integer deleteFlag;

    @Override
    public void where(JoinQueryBuilder<PageByMerchantProductQuery> wrapper) {
        wrapper.orderByAsc(PageByMerchantProductQuery::getWeight);
        wrapper.eq(Objects.nonNull(id), PageByMerchantProductQuery::getId, id);
        wrapper.eq(Objects.nonNull(merchantId), PageByMerchantProductQuery::getMerchantId, merchantId);
        wrapper.like(StrUtil.isNotBlank(name), PageByMerchantProductQuery::getName, name);
        wrapper.eq(StrUtil.isNotBlank(supplierProductCode), PageByMerchantProductQuery::getSupplierProductCode, supplierProductCode);
        wrapper.eq(StrUtil.isNotBlank(supplierId), PageByMerchantProductQuery::getSupplierId, supplierId);
        wrapper.eq(Objects.nonNull(status), PageByMerchantProductQuery::getStatus, status);
        wrapper.eq(PageByMerchantProductQuery::getDeleteFlag, DeleteFlagEnum.NORMAL.getCode());
        wrapper.like(StrUtil.isNotBlank(scenicspotName), PageByMerchantProductQuery::getScenicspotName, scenicspotName);
        if (Objects.nonNull(getPublishTimeStart())) {
            wrapper.ge(PageByMerchantProductQuery::getPublishTimeStart, DateUtil.beginOfDay(getPublishTimeStart()));
        }
        if (Objects.nonNull(getPublishTimeEnd())) {
            wrapper.le(PageByMerchantProductQuery::getPublishTimeEnd, DateUtil.endOfDay(getPublishTimeEnd()));
        }
    }
}
