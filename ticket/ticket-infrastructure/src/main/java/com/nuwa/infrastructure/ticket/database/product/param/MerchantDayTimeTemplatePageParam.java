package com.nuwa.infrastructure.ticket.database.product.param;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.nuwa.client.ticket.dto.clientobject.article.qry.ArticlePageQry;
import com.nuwa.client.ticket.dto.clientobject.product.qry.MerchantDayTimeTemplatePageQry;
import com.nuwa.framework.base.UserAware;
import com.nuwa.framework.database.PageQry;
import com.nuwa.infrastructure.ticket.database.article.entity.ArticleInfo;
import com.nuwa.infrastructure.ticket.database.product.entity.MerchantDayTimeTemplate;
import io.swagger.annotations.ApiModel;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.util.Objects;

/**
 * <pre>
 * 商户场次模板分页参数
 * </pre>
 *
 * @author huyonghack@163.com
 * @date 2021-12-17
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "商户场次模板分页参数")
public class MerchantDayTimeTemplatePageParam extends PageQry<MerchantDayTimeTemplate> {
    private static final long serialVersionUID = 1L;

    private MerchantDayTimeTemplatePageQry qry;

    public MerchantDayTimeTemplatePageParam(MerchantDayTimeTemplatePageQry qry) {
        super(qry);
        this.qry = qry;
    }

    @Override
    public LambdaQueryWrapper<MerchantDayTimeTemplate> toQueryWrapper() {
        UserAware userAware = qry.getUserAware();
        LambdaQueryWrapper<MerchantDayTimeTemplate> queryWrapper = Wrappers.lambdaQuery();
        queryWrapper.orderByDesc(MerchantDayTimeTemplate::getId);
        queryWrapper.eq(Objects.nonNull(userAware.getMchId()) && !userAware.getMchId().equals(-1L), MerchantDayTimeTemplate::getMchId, userAware.getMchId());
        return queryWrapper;
    }
}
