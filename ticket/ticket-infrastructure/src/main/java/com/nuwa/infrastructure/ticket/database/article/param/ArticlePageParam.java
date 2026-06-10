package com.nuwa.infrastructure.ticket.database.article.param;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.nuwa.client.ticket.dto.clientobject.article.qry.ArticlePageQry;
import com.nuwa.framework.base.UserAware;
import com.nuwa.framework.database.PageQry;
import com.nuwa.infrastructure.ticket.database.article.entity.ArticleInfo;
import io.swagger.annotations.ApiModel;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.util.Objects;

/**
 * <pre>
 * 商户资讯分页参数
 * </pre>
 *
 * @author huyonghack@163.com
 * @date 2021-12-17
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "资讯分页参数")
public class ArticlePageParam extends PageQry<ArticleInfo> {
    private static final long serialVersionUID = 1L;

    private ArticlePageQry qry;

    public ArticlePageParam(ArticlePageQry qry) {
        super(qry);
        this.qry = qry;
    }

    @Override
    public LambdaQueryWrapper<ArticleInfo> toQueryWrapper() {
        UserAware userAware = qry.getUserAware();
        LambdaQueryWrapper<ArticleInfo> queryWrapper = Wrappers.lambdaQuery();
        String orderBy = qry.getOrderBy();
        if (StrUtil.isNotBlank(orderBy)) {
            if ("new".equalsIgnoreCase(orderBy)) {
                queryWrapper.orderByDesc(ArticleInfo::getCreateTime);
            } else if ("fire".equalsIgnoreCase(orderBy)) {
                queryWrapper.orderByDesc(ArticleInfo::getViews);
            }
        } else {
            queryWrapper.orderByDesc(ArticleInfo::getId);
        }
        queryWrapper.select(ArticleInfo.class,
                t -> !t.getColumn().endsWith("_editor")
        );
        queryWrapper.like(StrUtil.isNotBlank(qry.getTitle()), ArticleInfo::getTitle, qry.getTitle());
        queryWrapper.eq(Objects.nonNull(qry.getCategoryOne()), ArticleInfo::getCategoryOne, qry.getCategoryOne());
        queryWrapper.eq(Objects.nonNull(qry.getCategorySecond()), ArticleInfo::getCategorySecond, qry.getCategorySecond());
        queryWrapper.eq(Objects.nonNull(qry.getStatus()), ArticleInfo::getStatus, qry.getStatus());
        if (Objects.nonNull(qry.getMchId())) {
            queryWrapper.eq(ArticleInfo::getMchId, qry.getMchId());
        } else {
            queryWrapper.eq(Objects.nonNull(userAware.getMchId()) && !userAware.getMchId().equals(-1L), ArticleInfo::getMchId, userAware.getMchId());
        }
        return queryWrapper;
    }
}
