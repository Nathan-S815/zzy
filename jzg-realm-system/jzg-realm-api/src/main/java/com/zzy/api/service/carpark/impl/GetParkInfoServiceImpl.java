package com.zzy.api.service.carpark.impl;


import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zzy.api.service.carpark.IGetParkInfoService;
import com.zzy.db.dao.carpark.GetParkInfoMapper;
import com.zzy.db.entity.carpark.GetParkInfo;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author zzy
 * @since 2020-04-24
 */
@Service
public class GetParkInfoServiceImpl extends ServiceImpl<GetParkInfoMapper, GetParkInfo> implements IGetParkInfoService {

}
