package com.nuwa.infrastructure.ticket.database.article.service.impl;

import com.nuwa.framework.database.supper.SuperServiceImpl;
import com.nuwa.infrastructure.ticket.database.article.entity.ArticleInfo;
import com.nuwa.infrastructure.ticket.database.article.mapper.ArticleInfoMapper;
import com.nuwa.infrastructure.ticket.database.article.service.ArticleInfoService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * 资讯表 服务实现类
 *
 * @author huyonghack@163.com
 * @since 2022-03-15
 */
@Slf4j
@Service
public class ArticleInfoServiceImpl extends SuperServiceImpl<ArticleInfoMapper, ArticleInfo> implements ArticleInfoService {

    @Autowired
    private ArticleInfoMapper articleInfoMapper;

}
