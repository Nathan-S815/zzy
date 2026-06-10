package com.nuwa.infrastructure.zeus.database.app.query;

import cn.hutool.core.util.StrUtil;
import com.nuwa.framework.database.tk.join.annotation.JoinColumn;
import com.nuwa.framework.database.tk.join.query.BaseJoinPagingQuery;
import com.nuwa.framework.database.tk.join.wrappper.JoinQueryBuilder;
import com.nuwa.infrastructure.zeus.database.mch.entity.MerchantApp;
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
@ApiModel(value = "商户应用分页参数")
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = true)
public class PageAppJoinQuery extends BaseJoinPagingQuery<PageAppJoinQuery> {
    private static final long serialVersionUID = 1L;

    @JoinColumn(tableClass = MerchantApp.class)
    private String appName;

    @JoinColumn(tableClass = MerchantApp.class)
    private Integer appType;

    @JoinColumn(tableClass = MerchantApp.class)
    private Integer status;

    @Override
    public void where(JoinQueryBuilder<PageAppJoinQuery> wrapper) {
        //wrapper.eq(StrUtil.isNotEmpty(this.getAppName()), this.getAliasColumn(MerchantApp.APP_NAME), this.getAppName());
        wrapper.eq(StrUtil.isNotEmpty(this.getAppName()), PageAppJoinQuery::getAppName, this.getAppName());
        wrapper.orderByAsc(PageAppJoinQuery::getAppName);
    }
}
