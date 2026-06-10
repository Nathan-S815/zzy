package com.nuwa.infrastructure.ticket.database.article.service.impl;

import com.nuwa.infrastructure.ticket.database.article.entity.ArticleCategory;
import com.nuwa.infrastructure.ticket.database.article.mapper.ArticleCategoryMapper;
import com.nuwa.infrastructure.ticket.database.article.service.ArticleCategoryService;
import com.nuwa.framework.database.supper.SuperServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;

/**
 *  服务实现类
 *
 * @author huyonghack@163.com
 * @since 2022-03-15
 */
@Slf4j
@Service
public class ArticleCategoryServiceImpl extends SuperServiceImpl<ArticleCategoryMapper, ArticleCategory> implements ArticleCategoryService {

    @Autowired
    private ArticleCategoryMapper articelCategoryMapper;

}
