package com.nuwa.infrastructure.ticket.database.product.mapper.query;

import com.nuwa.framework.database.tk.join.annotation.JoinColumn;
import com.nuwa.framework.database.tk.join.query.BaseJoinPagingQuery;
import com.nuwa.framework.database.tk.join.wrappper.JoinQueryBuilder;
import com.nuwa.infrastructure.ticket.database.product.entity.MerchantProductDistribute;
import com.nuwa.infrastructure.ticket.database.product.entity.ScenicspotProduct;
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
@ApiModel(value = "C端景区产品分页查询")
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = true)
public class PageByUserProductQuery extends BaseJoinPagingQuery<PageByUserProductQuery> {
    private static final long serialVersionUID = 1L;

    @ApiModelProperty("产品Id")
    @JoinColumn(tableClass = ScenicspotProduct.class, column = ScenicspotProduct.ID)
    private Long id;

    @ApiModelProperty("所属景点id")
    @JoinColumn(tableClass = ScenicspotProduct.class, column = ScenicspotProduct.SCENICSPOT_ID)
    private Long scenicspotId;

    @ApiModelProperty("所属分销-商户id")
    @JoinColumn(tableClass = MerchantProductDistribute.class, column = MerchantProductDistribute.DISTRIBUTE_MERCHANT_ID)
    private Long distributeMerchantId;

    @ApiModelProperty("供应商上架状态 0:未上架 1:已上架")
    @JoinColumn(tableClass = ScenicspotProduct.class, column = ScenicspotProduct.STATUS)
    private Integer status;

    @ApiModelProperty(value = "分销商上架状态 0:未上架 1:已上架", hidden = true)
    @JoinColumn(tableClass = MerchantProductDistribute.class, column = MerchantProductDistribute.PUBLISH_STATUS)
    private Integer distributePublishStatus;

    @ApiModelProperty(value = "删除状态", hidden = true)
    @JoinColumn(tableClass = ScenicspotProduct.class, column = ScenicspotProduct.DELETE_FLAG)
    private Integer deleteFlag;

    @ApiModelProperty("权重")
    @JoinColumn(tableClass = MerchantProductDistribute.class, column = MerchantProductDistribute.WEIGHT)
    private Integer weight;

    @JoinColumn(tableClass = MerchantProductDistribute.class, column = MerchantProductDistribute.PUBLISH_TIME)
    @ApiModelProperty("上架时间")
    private Date publishTime;

    @Override
    public void where(JoinQueryBuilder<PageByUserProductQuery> wrapper) {
        wrapper.orderByAsc(PageByUserProductQuery::getWeight);
        wrapper.orderByDesc(PageByUserProductQuery::getPublishTime);
        wrapper.eq(Objects.nonNull(distributeMerchantId), PageByUserProductQuery::getDistributeMerchantId, distributeMerchantId);
        wrapper.eq(Objects.nonNull(id), PageByUserProductQuery::getId, id);
        wrapper.eq(Objects.nonNull(scenicspotId), PageByUserProductQuery::getScenicspotId, scenicspotId);
        wrapper.eq(Objects.nonNull(status), PageByUserProductQuery::getStatus, status);
        wrapper.eq(PageByUserProductQuery::getDistributePublishStatus, 1);
        wrapper.eq(PageByUserProductQuery::getDeleteFlag, DeleteFlagEnum.NORMAL.getCode());
    }
}
