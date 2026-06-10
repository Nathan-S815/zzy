package com.zzy.api.service.hotmap.impl;


import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zzy.api.service.hotmap.IHotCountMapService;
import com.zzy.db.dao.hotmap.HotCountMapMapper;
import com.zzy.db.entity.hotmap.HotCountMap;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 热力图 服务实现类
 * </p>
 *
 * @author zzy
 * @since 2020-05-22
 */
@Service
public class HotCountMapServiceImpl extends ServiceImpl<HotCountMapMapper, HotCountMap> implements IHotCountMapService {

}
