package com.zzy.api.service.hotmap.impl;

import com.zzy.db.entity.hotmap.News;
import com.zzy.db.dao.hotmap.NewsMapper;
import com.zzy.api.service.hotmap.INewsService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author zzy
 * @since 2020-07-07
 */
@Service
public class NewsServiceImpl extends ServiceImpl<NewsMapper, News> implements INewsService {

}
