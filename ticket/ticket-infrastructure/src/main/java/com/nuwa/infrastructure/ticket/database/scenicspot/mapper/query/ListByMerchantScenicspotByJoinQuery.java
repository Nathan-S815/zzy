package com.nuwa.infrastructure.ticket.database.scenicspot.mapper.query;


import com.nuwa.framework.database.tk.join.query.BaseJoinListQuery;
import com.nuwa.framework.database.tk.join.wrappper.JoinQueryBuilder;
import io.swagger.annotations.ApiModel;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <pre>
 * 商户应用 分页参数对象
 * </pre>
 *
 * @author huyonghack@163.com
 * @date 2021-06-03
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "获取商户POI列表参数")
public class ListByMerchantScenicspotByJoinQuery extends BaseJoinListQuery<ListByMerchantScenicspotByJoinQuery> {
    private static final long serialVersionUID = 1L;

    @Override
    public void where(JoinQueryBuilder<ListByMerchantScenicspotByJoinQuery> wrapper) {
    }
}
